import { Injectable } from '@angular/core';
import {Client, Stomp, StompSubscription} from "@stomp/stompjs";
import {BehaviorSubject, Observable} from "rxjs";
import {environment} from "../../../environments/environment";
import {Game} from "../model/game";

@Injectable({
  providedIn: 'root'
})
export class WebsocketService {
  // private stompClient: Client
  // subscription: StompSubscription;
  // game: Game
  // constructor() {
  // }
  //
  // connect(gameId: number): void {
  //   this.stompClient = new Client({
  //     brokerURL: `${environment.backendURL}/gameplay`,
  //     onConnect: () =>{
  //       this.subscription = this.stompClient.subscribe('/topic/game/gameplay/' + gameId,
  //         (message: any) => {
  //             let gameParse = JSON.parse(message.body)
  //             callback()
  //         })
  //     }
  //   })
  // }

}
