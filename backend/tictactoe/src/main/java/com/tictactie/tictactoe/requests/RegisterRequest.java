package com.tictactie.tictactoe.requests;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
}
