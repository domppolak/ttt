package com.tictactie.tictactoe.repositories;

import com.tictactie.tictactoe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.username=:username and u.password=:password")
    User findByLoginAndCheckIfPasswordIsCorrect(@Param("username") String login, @Param("password") String password);

    Optional<User> findByUsername(String username);
}
