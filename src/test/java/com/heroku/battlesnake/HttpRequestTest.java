package com.heroku.battlesnake;

import com.google.gson.Gson;
import com.heroku.battlesnake.controller.GameController;

import com.heroku.battlesnake.gameElements.GameInfo;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {
    private static final Gson GSON = new Gson();

    @LocalServerPort
    private int port;

    @Autowired
    private GameController gameController;

    @Test
    public void moveTest() throws Exception {
        try {
            String path = "src/test/resources/request.json";
            String content = new String(Files.readAllBytes(Paths.get(path)));
            Map<String, String> response = this.gameController.move(GSON.fromJson(content, GameInfo.class));

            List<String> options = new ArrayList<String>();
            options.add("up");
            options.add("down");
            options.add("left");
            options.add("right");

            assertTrue(options.contains(response.get("move")));
        } catch (Exception e) {
            e.printStackTrace();
        }



    }
}