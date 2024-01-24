package com.tictactie.tictactoe.controller;

import com.tictactie.tictactoe.dto.GameDTO;
import com.tictactie.tictactoe.dto.GamePlayDto;
import com.tictactie.tictactoe.exceptions.*;
import com.tictactie.tictactoe.model.Game;
import com.tictactie.tictactoe.model.Player;
import com.tictactie.tictactoe.repositories.PlayerRepository;
import com.tictactie.tictactoe.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class GameController {

    private static final Logger logger = LoggerFactory.getLogger(GameController.class);
    @Autowired
    GameService gameService;

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping("/game/start")
    public ResponseEntity<GameDTO> startGame(@RequestBody Long player1Id)  {

        Player player = playerRepository.findById(player1Id).orElse(null);
        if(player == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Game game = gameService.createNewGame(player);
        return new ResponseEntity<>(createGameDto(game), HttpStatus.OK);
    }

    @PostMapping("/game/connect/random")
    public ResponseEntity<GameDTO> connectRandomGame(@RequestBody Long player2Id) throws PlayerNotFoundException, GameNotFoundException{
        Player player = playerRepository.findById(player2Id).orElse(null);
        if(player == null) throw new PlayerNotFoundException("Player not found.");
        Game game = gameService.connectToRandomGame(player);

        simpMessagingTemplate.convertAndSend("/topic/game/gameplay" + game.getPlayer1().getId(), createGameDto(game));
        return new ResponseEntity<>(createGameDto(game), HttpStatus.OK);
    }

    @MessageMapping("/game/gameplay/{gameId}")
    public void gamePlay(@DestinationVariable Long gameId, @RequestBody GamePlayDto gamePlayDto) throws GameNotFoundException, InvalidGameException, InvalidParamException, BoardNotInitializeException {
        Game game = gameService.gamePlay(gameId, gamePlayDto);
        simpMessagingTemplate.convertAndSend("/topic/game/gameplay" + game.getId(), createGameDto(game));
    }

    private GameDTO createGameDto(Game game){

        return new GameDTO(game.getId(),
                game.getPlayer1() != null ? game.getPlayer1().getId() : -1,
                game.getPlayer2() != null ? game.getPlayer2().getId() : -1,
                game.getWinner() != null ? game.getWinner().getId() : -1,
                game.getBoard().getBoard(),
                game.getGameStatus());
    }
}
//        return new ResponseEntity<>(game, HttpStatus.OK);