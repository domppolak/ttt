package com.tictactie.tictactoe.dto;

import lombok.Data;

@Data
public class GamePlayDto {
    private int row;
    private int column;
    private Long playerTurnId;
}
