package com.tictactie.tictactoe.controller;

import com.tictactie.tictactoe.model.Player;
import com.tictactie.tictactoe.model.User;
import com.tictactie.tictactoe.repositories.PlayerRepository;
import com.tictactie.tictactoe.repositories.UserRepository;
import com.tictactie.tictactoe.requests.LoginRequest;
import com.tictactie.tictactoe.requests.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
public class UserController {

//    @Autowired
//    AuthenticationManager authenticationManager;
    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<Long> login(@RequestBody LoginRequest loginRequest){
        User user = userRepository.findByLoginAndCheckIfPasswordIsCorrect(loginRequest.getUsername(), loginRequest.getPassword());
        if(user == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Login failed");
        return new ResponseEntity<>(user.getPlayer().getId(), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Long> register(@RequestBody RegisterRequest registerRequest){
        try{

            Optional<User> userOptional = userRepository.findByUsername(registerRequest.getUsername());
            if(userOptional.isPresent()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exist");
            }

            User newUser = new User(registerRequest.getUsername(), registerRequest.getPassword());
            Player newPlayer = new Player(newUser);
            newUser.setPlayer(newPlayer);

            userRepository.save(newUser);
            playerRepository.save(newPlayer);
            return new ResponseEntity<>(newPlayer.getId(), HttpStatus.OK);
        }catch (Exception e){

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
