package com.tictactie.tictactoe.services;

import com.tictactie.tictactoe.dto.GamePlayDto;
import com.tictactie.tictactoe.dto.UpdateBoardDTO;
import com.tictactie.tictactoe.enums.GameStatus;
import com.tictactie.tictactoe.exceptions.BoardNotInitializeException;
import com.tictactie.tictactoe.exceptions.GameNotFoundException;
import com.tictactie.tictactoe.exceptions.InvalidGameException;
import com.tictactie.tictactoe.exceptions.InvalidParamException;
import com.tictactie.tictactoe.model.Board;
import com.tictactie.tictactoe.model.Game;
import com.tictactie.tictactoe.model.Player;
import com.tictactie.tictactoe.repositories.GameRepository;
import com.tictactie.tictactoe.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class GameService {

    @Autowired
    GameRepository gameRepository;

    @Autowired
    BoardService boardService;

    @Autowired
    PlayerRepository playerRepository;

    public Game createNewGame(Player player){
        Game game = new Game();
        game.setPlayer1(player);
        game.setGameStatus(GameStatus.NEW);
        game.setBoard(boardService.initBoard());
        gameRepository.save(game);

        return game;
    }

    public Game connectToRandomGame(Player player2) throws GameNotFoundException{
        List<Game> newGames = gameRepository.findByGameStatus(GameStatus.NEW);

        newGames.removeIf(game -> game.getPlayer1().equals(player2));

        if(newGames.isEmpty()){
            throw new GameNotFoundException("Game not found");
        }

        Random random = new Random();
        Game game = newGames.get(random.nextInt(newGames.size()));

        Player player1 = game.getPlayer1();

        player1.setSymbol(random.nextBoolean() ? "X" : "O");
        player2.setSymbol(player1.getSymbol().equals("X") ? "O" : "X");

        game.setPlayer2(player2);
        game.setGameStatus(GameStatus.IN_PROGRESS);
        gameRepository.save(game);
        return game;
    }

    public Game gamePlay(Long gameId, GamePlayDto gamePlayDto) throws GameNotFoundException, InvalidGameException, InvalidParamException, BoardNotInitializeException {
        Game game = gameRepository.findById(gameId).orElse(null);

        if(game == null){ throw new GameNotFoundException("Game not found"); }

        if(game.getGameStatus() == GameStatus.FINISHED) { throw new InvalidGameException("Game already finished."); }

        Board board = game.getBoard();

        String currentPlayerSymbol = game.getPlayer1().getId().equals(gamePlayDto.getPlayerTurnId()) ? game.getPlayer1().getSymbol() : game.getPlayer2().getSymbol();

        UpdateBoardDTO updateBoardDTO = new UpdateBoardDTO(gamePlayDto.getRow(), gamePlayDto.getColumn(), currentPlayerSymbol);

        Boolean moveResult = boardService.updateBoard(board, updateBoardDTO);

        if(moveResult && boardService.checkWinner(board, currentPlayerSymbol)){
            setWinner(game, gamePlayDto);
            game.setGameStatus(GameStatus.FINISHED);
        }

        if(boardService.isBoardFull(board) && game.getGameStatus() != GameStatus.FINISHED){
            setDraw(game);
            game.setGameStatus(GameStatus.FINISHED);
        }


        gameRepository.save(game);

        return game;
    }

    private void setWinner(Game game, GamePlayDto gamePlayDto){
        Player winner = game.getPlayer1().getId().equals(gamePlayDto.getPlayerTurnId()) ? game.getPlayer1() : game.getPlayer2();
        Player loser = game.getPlayer1().getId().equals(gamePlayDto.getPlayerTurnId()) ? game.getPlayer2() : game.getPlayer1();

        winner.setWins(winner.getWins() + 1);
        loser.setLost(loser.getLost() + 1);
        game.setWinner(winner);
    }

    private void setDraw(Game game){
        game.getPlayer1().setDraws(game.getPlayer1().getDraws() + 1);
        game.getPlayer2().setDraws(game.getPlayer2().getDraws() + 1);
    }
}
