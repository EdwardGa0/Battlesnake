package com.heroku.battlesnake.controller;

import com.heroku.battlesnake.strategy.Simulation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.*;
import com.heroku.battlesnake.gameElements.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@RestController
public class GameController {
    private static final Logger LOGGER = Logger.getLogger( GameController.class.getName() );

    @Autowired
    private Gson GSON;

    @GetMapping("/")
    public Map<String, String> snakeInfo() {
        Map<String, String> response = new HashMap<>();
        response.put("apiversion", "1");
        response.put("author", "EdwardGa0");
        response.put("color", "#7BE495");
        response.put("headType", "fang");
        response.put("tailType", "sharp");
        return response;
    }

    @PostMapping("/start")
    public void start() {
        LOGGER.info("Game has started!");
    }

    @PostMapping("/move")
    public Map<String, String> move(@RequestBody GameInfo gameInfo) throws IOException {
        Simulation sim = new Simulation(gameInfo.turnNum, gameInfo.board, gameInfo.you);
        String move = "";

        move = sim.bfsStrat();
        if (move.isEmpty()) {
            move = sim.moveToAvailable();
        }

        Map<String, String> response = new HashMap<>();
        response.put("move", move);
        LOGGER.info("Move: " + move);

        return response;
    }

}
