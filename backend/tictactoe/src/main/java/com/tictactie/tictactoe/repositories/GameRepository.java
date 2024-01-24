package com.tictactie.tictactoe.repositories;

import com.tictactie.tictactoe.enums.GameStatus;
import com.tictactie.tictactoe.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findByGameStatus(GameStatus gameStatus);
}
