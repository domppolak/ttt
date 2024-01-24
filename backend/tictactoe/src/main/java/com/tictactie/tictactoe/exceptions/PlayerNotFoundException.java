package com.tictactie.tictactoe.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlayerNotFoundException extends Exception{
    private String message;
}
