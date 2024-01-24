package com.tictactie.tictactoe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateBoardDTO {
    private int row;
    private int column;
    private String symbol;
}
