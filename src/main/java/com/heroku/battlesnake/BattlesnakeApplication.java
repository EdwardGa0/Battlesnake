package com.heroku.battlesnake;

import com.heroku.battlesnake.controller.GameController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BattlesnakeApplication {

    public static void main(String[] args) {
        SpringApplication.run(GameController.class, args);
    }

}
