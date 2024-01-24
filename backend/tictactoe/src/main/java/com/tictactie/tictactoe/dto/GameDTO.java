package com.tictactie.tictactoe.dto;

import com.tictactie.tictactoe.enums.GameStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GameDTO {
    Long gameId;
    Long Player1Id;
    Long Player2Id;
    Long winnerId;
    String board[][];
    GameStatus gameStatus;
}
