package com.tictactie.tictactoe.controller;

import com.tictactie.tictactoe.model.Player;
import com.tictactie.tictactoe.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/player/")
public class PlayerController {

    @Autowired
    PlayerRepository playerRepository;

    @GetMapping("ranking")
    public ResponseEntity<List<Player>> getPlayerRanking(){
        try{
            List<Player> playerRanking = playerRepository.findAll();
            if(playerRanking.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(playerRanking, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
