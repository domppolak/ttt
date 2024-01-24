package com.tictactie.tictactoe.services;


import com.tictactie.tictactoe.dto.UpdateBoardDTO;
import com.tictactie.tictactoe.exceptions.BoardNotInitializeException;
import com.tictactie.tictactoe.exceptions.InvalidParamException;
import com.tictactie.tictactoe.model.Board;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class BoardService {

    public Board initBoard(){
        Board board = new Board();
        String initBoard[][] = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                initBoard[i][j] = "";
            }
        }
        board.setBoard(initBoard);
        return board;
    }

    public boolean updateBoard(Board board, UpdateBoardDTO updateBoardDTO) throws InvalidParamException, BoardNotInitializeException {
        if(board == null){
            throw new BoardNotInitializeException("Board not initialized.");
        }
        int row = updateBoardDTO.getRow();
        int column = updateBoardDTO.getColumn();

        if (!isValidMove(board, row, column)) {
            throw new InvalidParamException("Invalid move.");
        }

        if(isBoardFull(board)){
            return false;
        }

        board.getBoard()[row][column] = updateBoardDTO.getSymbol();
        return true;
    }

    public boolean isBoardFull(Board board){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(board.getBoard()[i][j].equals("")){
                    return false;
                }
            }
        }
        return true;
    }
    public boolean checkWinner(Board board, String currentPlayerSymbol){
        if (board == null) {
            throw new IllegalStateException("Board not initialized.");
        }

        for (int i = 0; i < 3; i++) {
            if (checkRow(board, i, currentPlayerSymbol) || checkColumn(board, i, currentPlayerSymbol)) {
                return true;
            }
        }

        if (checkDiagonal(board, currentPlayerSymbol) || checkAntiDiagonal(board, currentPlayerSymbol)) {
            return true;
        }

        return false;
    }

    private boolean checkRow(Board board, int row, String currentPlayerSymbol) {
        return board.getBoard()[row][0].equals(currentPlayerSymbol) &&
                board.getBoard()[row][1].equals(currentPlayerSymbol) &&
                board.getBoard()[row][2].equals(currentPlayerSymbol);
    }

    private boolean checkColumn(Board board, int column, String currentPlayerSymbol) {
        return board.getBoard()[0][column].equals(currentPlayerSymbol) &&
                board.getBoard()[1][column].equals(currentPlayerSymbol) &&
                board.getBoard()[2][column].equals(currentPlayerSymbol);
    }

    private boolean checkDiagonal(Board board, String currentPlayerSymbol) {
        return board.getBoard()[0][0].equals(currentPlayerSymbol) &&
                board.getBoard()[1][1].equals(currentPlayerSymbol) &&
                board.getBoard()[2][2].equals(currentPlayerSymbol);
    }

    private boolean checkAntiDiagonal(Board board, String currentPlayerSymbol) {
        return board.getBoard()[0][2].equals(currentPlayerSymbol) &&
                board.getBoard()[1][1].equals(currentPlayerSymbol) &&
                board.getBoard()[2][0].equals(currentPlayerSymbol);
    }

    private boolean isValidMove(Board board, int row, int column) {
        return row >= 0 && row < board.getBoard().length && column >= 0 && column < board.getBoard()[0].length;
    }
}
