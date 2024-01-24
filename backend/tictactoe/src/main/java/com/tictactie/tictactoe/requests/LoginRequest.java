package com.tictactie.tictactoe.requests;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
