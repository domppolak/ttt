package com.tictactie.tictactoe.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class InvalidGameException extends Exception{
    private String message;
}
