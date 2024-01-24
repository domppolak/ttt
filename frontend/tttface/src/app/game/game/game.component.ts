import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthService} from "../../auth/services/auth.service";
import {Client, StompSubscription,} from "@stomp/stompjs";
import {ActivatedRoute} from "@angular/router";
import {Game} from "../model/game";
import {environment} from "../../../environments/environment";
import {GameService} from "../services/game.service";
import {GamePlayDto} from "../model/GamePlayDto";
import {GameStatus} from "../model/gamestatus";
import {Player} from "../model/player";

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrl: './game.component.scss'
})
export class GameComponent implements OnInit, OnDestroy{

  private stompClient: Client;
  GamePlaySubscription: StompSubscription;
  GameStartSubscription: StompSubscription
  private game: Game;
  private playerId: number;
  private opponent: number;
  private enemies: number
  gameStarted: boolean = false;
  gameBoard: string[][]
  playerTurnId: number = undefined
  message: string = "Starting"
  isDisabled: boolean = false;
  gameFinished: boolean = false;
  players: Player[]
  isGetPlayerRanking: boolean = false
  constructor(
    private authService: AuthService,
    private activeRoute: ActivatedRoute,
    // private websocketService: WebsocketService,
    private gameService: GameService)
  {
    this.playerId = authService.playerId
    this.playerTurnId = this.playerId
    this.startSubscribeGameStart()

  }

  finishGame(): void{
    this.gameStarted = false;
    this.gameFinished = false;
    this.isGetPlayerRanking = false;
  }
  startGame(): void {
    this.gameService.startGame(this.playerId).subscribe((game: any) =>{
      this.game = game
      console.log("Game on frontend", this.game)
      this.gameStarted = true
      this.gameBoard = this.game.board
      this.startSubscribeGamePLay()
      this.message = "Waiting for enemies!"
      console.log("Game started", game)
    })
  }

  connectRandomGame(): void {
    this.gameService.connectRandomGame(this.playerId).subscribe(
      (game: any) => {
        this.game = game;
        this.startSubscribeGamePLay()
        this.gameStarted = true;
        this.gameBoard = this.game.board
        console.log("You connect to game: ", game)
        this.isDisabled = false
        this.message = "It is not your turn!"
        this.playerTurnId = this.game.player1Id
        this.enemies = this.game.player1Id
      },
      error => {
        console.error('Failed to connect to random game', error);
      }
    );
  }

  startSubscribeGamePLay(): void {
    // this.stompClient = new Client({
    //   brokerURL: `ws://localhost:8080/gameplay`,
    //   onConnect: () =>{
    //     this.subscription = this.stompClient.subscribe('/topic/game/gameplay' + this.game.gameId,
    //       (message: any) =>{
    //         let gameParse = JSON.parse(message.body)
    //         this.procesMove(gameParse)
    //         console.log("Connection is active")
    //       })
    //   }
    // })
    //
    // this.stompClient.activate()
    this.GamePlaySubscription = this.stompClient.subscribe('/topic/game/gameplay' + this.game.gameId,
      (message: any) =>{
        let gameParse = JSON.parse(message.body)
        this.procesMove(gameParse)
        console.log("Connection is active")
      })
  }

  startSubscribeGameStart(){
    this.stompClient = new Client({
      brokerURL: environment.websocketURL + '/gameplay',
      onConnect: () =>{
        this.GameStartSubscription = this.stompClient.subscribe('/topic/game/gameplay' + this.playerId,
          (message: any) =>{
          let gameParse = JSON.parse(message.body)
              this.game = gameParse
              this.message = "It is your turn!"
              this.playerTurnId = this.game.player1Id
              this.enemies = this.game.player2Id
              this.isDisabled = true
          })
      }
    })

    this.stompClient.activate()
  }

  procesMove(game: Game){
    this.gameBoard = game.board
    this.game = game
    console.log("cala gra", game)
    console.log("game", game.player1Id, game.player2Id) // 1,2
    console.log("Console log", this.playerTurnId, this.playerId, this.enemies) //2,2,1
    console.log("move is processing")
    if(this.playerTurnId === this.playerId){
      this.playerTurnId = this.enemies
      this.isDisabled = false
      this.message = "It is not your turn"
    }else{
      this.playerTurnId = this.playerId
      this.isDisabled = true
      this.message = "It is your turn"
    }
    console.log(game)
    if(game.gameStatus !== GameStatus.Finished){
      console.log("Finished processing move")
      return
    }

    this.GamePlaySubscription.unsubscribe()
    this.stompClient.forceDisconnect()
    this.isDisabled = false

    if(game.winnerId === this.playerId){
      this.message = "You WON!"
    }else if (game.winnerId === -1){
      this.message = "It is DRAW!"
    }else{
      this.message = "You LOST!"
    }

    this.gameFinished = true
  }
  clickGameSlot(row: number, column: number){
    console.log("cell is clicked", row, column, this.playerTurnId)
    const gamePlayDto: GamePlayDto = {
      row : row,
      column: column,
      playerTurnId: this.playerTurnId
    }
    console.log("Game id", this.game.gameId)
    console.log("Send: ", JSON.stringify(gamePlayDto))
    this.stompClient.publish({
      destination: '/app/game/gameplay/' + this.game.gameId,
      body: JSON.stringify(gamePlayDto)
    })
  }
  ngOnInit() {
      // this.game = this.activeRoute.snapshot.data['game']
      // this.websocketService.subscribeToGame(this.game.id)
  }

  ngOnDestroy() {
    this.GameStartSubscription.unsubscribe()
    this.GameStartSubscription.unsubscribe()
    this.stompClient.forceDisconnect()
  }

  getRanking(){
    this.gameService.getPlayerRanking().subscribe((players: Player[]) =>{
        this.players = players
        this.isGetPlayerRanking = true
    }, (error) =>{
      console.log("Failed to ger player ranking", error)
    })
  }
}
