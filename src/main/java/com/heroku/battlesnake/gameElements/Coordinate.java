package com.heroku.battlesnake.gameElements;

public class Coordinate {
    public int y;
    public int x;
    private Coordinate parent;
    public int moveNum;

    public Coordinate(int y, int x) {
        this.y = y;
        this.x = x;
    }

    public Coordinate() { }

    public Coordinate[] around() {
        Coordinate[] around = new Coordinate[4];
        around[0] = new Coordinate(y-1, x);
        around[1] = new Coordinate(y, x+1);
        around[2] = new Coordinate(y+1,x);
        around[3] = new Coordinate(y, x-1);
        return around;
    }

    public Coordinate[] around(int[] order) {
        Coordinate[] around = around();
        Coordinate[] rtn = new Coordinate[4];
        for (int i = 0; i < order.length; i++) {
            rtn[i] = around[order[i]];
        }
        return rtn;
    }

    public Coordinate coordFromRel(int y, int x) {
        return new Coordinate(y+y, x+x);
    }

    public boolean inGrid(int size) {
        return y >= 0 && y < size && x >= 0 && x < size;
    }

    public boolean equals(Coordinate other) {
        return y == other.y && x == other.x;
    }

    public Coordinate copy() {
        return new Coordinate(y, x);
    }

    public String coordStr() {
        return y + ", " + x;
    }

    public int distTo(Coordinate other) {
        return Math.abs(y - other.y) + Math.abs(x - other.x);
    }

    public Coordinate getParent() {
        return parent;
    }

    public void setParent(Coordinate parent) {
        moveNum = parent.moveNum + 1;
        this.parent = parent;
    }

}