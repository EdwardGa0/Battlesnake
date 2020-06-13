package com.heroku.battlesnake.gameElements;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Board {
    public int height;
    public int width;
    public Coordinate[] food;
    public Snake[] snakes;
    private int[][] grid;

    public Board(int size) {
        height = size;
        width = size;
        grid = new int[height][width];
    }

    public Board() { }

    public void fillGrid(Snake me) {
        grid = new int[height][width];
        for (int i = 0; i < snakes.length; i++) {
            for (Coordinate seg : snakes[i].getBody()) {
                grid[seg.y][seg.x] = 1;
            }
            if (!snakes[i].id.equals(me.id) && snakes[i].length >= me.length) {
                if (snakes[i].getHead().coordFromRel(1, 0).inGrid(height)) {
                    grid[snakes[i].getHead().y+1][snakes[i].getHead().x] = i+1;
                }
                if (snakes[i].getHead().coordFromRel(-1, 0).inGrid(height)) {
                    grid[snakes[i].getHead().y-1][snakes[i].getHead().x] = i+1;
                }
                if (snakes[i].getHead().coordFromRel(0, 1).inGrid(height)) {
                    grid[snakes[i].getHead().y][snakes[i].getHead().x+1] = i+1;
                }
                if (snakes[i].getHead().coordFromRel(0, -1).inGrid(height)) {
                    grid[snakes[i].getHead().y][snakes[i].getHead().x-1] = i+1;
                }
            }
        }
        for (Coordinate f : food) {
            if (grid[f.y][f.x] == 0) {
                grid[f.y][f.x] = -1;
            }
        }
    }

    public int[] snakeAreas(int maxMoves) {
        int[] aroundAreas = new int[snakes.length];

        Queue<Coordinate>[] queues = new Queue[snakes.length];
        for (int i = 0; i < snakes.length; i++) {
            queues[i] = new LinkedList<>();
            queues[i].add(snakes[i].getHead());
        }
        int moveNum = 0;
        int idx = 0;
        while (moveNum <= maxMoves) {
            while (queues[idx].size() > 0) {
                Coordinate parent = queues[idx].remove();
                aroundAreas[idx]++;
                if (parent.moveNum > moveNum) {
                    break;
                }
                // add children to queue
                for (Coordinate child : parent.around()) {
                    if (child.inGrid(height) && coordAt(child) < 1) {
                        child.setParent(parent);
                        queues[idx].add(child);
                        setCoord(child, idx+1);
                    }
                }
            }
            idx++;
            if (idx >= snakes.length) {
                idx = 0;
                // remove tails
                moveNum++;
                for (Snake s : snakes) {
                    Coordinate tempSeg = s.getRevSeg(moveNum - 1);
                    if (tempSeg != null) {
                        setCoord(tempSeg, 0);
                    }
                }
            }
        }
        System.out.println(gridStr());
        return aroundAreas;
    }

    public int coordAt(Coordinate coord) {
        return grid[coord.y][coord.x];
    }

    public void setCoord(Coordinate coord, int value) {
        grid[coord.y][coord.x] = value;
    }

    public int[][] getGrid() {
        int[][] rtn = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                rtn[i][j] = grid[i][j];
            }
        }
        return rtn;
    }

    public void setGrid(Board other) {
        grid = other.getGrid();
    }

    public Board emptyCopy() {
        Board board = new Board(height);
        return board;
    }

    public Board fullCopy() {
        Board board = emptyCopy();
        board.setGrid(this);
        return board;
    }

    public int size() {
        return height;
    }

    public Coordinate nearestFood(Coordinate head) {
        int minDist = Integer.MAX_VALUE;
        Coordinate nearest = new Coordinate();
        for (Coordinate food : food) {
            if (food.distTo(head) < minDist) {
                minDist = food.distTo(head);
                nearest = food.copy();
            }
        }
        return nearest;
    }

    public String gridStr() {
        StringBuilder gridStr = new StringBuilder();
        for (int[] row : grid) {
            gridStr.append(Arrays.toString(row) + "\n");
        }
        return gridStr.toString();
    }
}
