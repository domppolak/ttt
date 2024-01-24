package com.tictactie.tictactoe.exceptions;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GameNotFoundException extends Exception{
    private String message;

}
