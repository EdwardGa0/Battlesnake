package com.heroku.battlesnake.strategy;

import com.heroku.battlesnake.gameElements.Board;
import com.heroku.battlesnake.gameElements.Coordinate;
import com.heroku.battlesnake.gameElements.Snake;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Simulation {
    public int turn;
    public Board board;
    public Snake me;
    public static final String[] reverseMoves = { "down", "left", "up", "right" };
    public static final String[] cwMoves = { "up", "right", "down", "left" };

    public Simulation(int turn, Board board, Snake me) throws IOException {
        this.turn = turn;
        this.board = board;
        this.me = me;
        board.fillGrid(me);
    }

    public ArrayList<String> retrace(Coordinate child) {
        if (child.getParent() != null) {
            Coordinate[] around = child.around();
            for (int i = 0; i < around.length; i++) {
                if (around[i].equals(child.getParent())) {
                    ArrayList<String> temp = retrace(child.getParent());
                    temp.add(reverseMoves[i]);
                    return temp;
                }
            }
        }
        return new ArrayList<>();
    }

    public String bfsStrat() {
        //System.out.println(Arrays.toString(board.snakeAreas(10)));
        // bfs setup
        Board bfs = board.fullCopy();
        Queue<Coordinate> queue = new LinkedList<>();
        Coordinate start = me.getHead();
        queue.add(start.copy());
        ArrayList<Coordinate> nearestFood = new ArrayList<Coordinate>();
        int moveNum = 0;
        while (queue.size() > 0 && moveNum < 10) {
            Coordinate parent = queue.remove();

            // remove tails
            if (parent.moveNum > moveNum) {
                moveNum = parent.moveNum;
                for (Snake s : board.snakes) {
                    Coordinate tempSeg = s.getRevSeg(moveNum - 2);
                    if (tempSeg != null) {
                        bfs.setCoord(tempSeg, 0);
                    }
                }
            }

            // add children to queue
            for (Coordinate child : parent.around()) {
                if (child.inGrid(board.size()) && bfs.coordAt(child) < 1) {
                    child.setParent(parent);
                    queue.add(child);
                    // found food
                    if (bfs.coordAt(child) == -1) {
                        nearestFood.add(child);
                    }
                    bfs.setCoord(child, 1);
                }
            }
        }

        ArrayList<String> moves = retrace(nearestFood.get(0));
        System.out.println("I'm hungry");
        System.out.println(nearestFood.get(0).coordStr());
        return moves.get(0);
    }


    public String moveToAvailable() {
        Coordinate[] around = me.getHead().around();
        for (int i = 0; i < around.length; i++) {
            if (around[i].inGrid(board.size()) && board.coordAt(around[i]) != 1) {
                return cwMoves[i];
            }
        }
        return "";
    }
}
