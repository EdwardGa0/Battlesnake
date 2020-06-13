package com.heroku.battlesnake.gameElements;

public class Snake {
    public String id;
    public String name;
    public int health;
    public int length;
    private Coordinate head;
    private Coordinate[] body;

    public Snake() { }

    public Coordinate getHead() {
        return head.copy();
    }

    public Coordinate[] getBody() {
        Coordinate[] rtn = new Coordinate[body.length];
        for (int i = 0; i < body.length; i++) {
            rtn[i] = body[i].copy();
        }
        return rtn;
    }

    public Coordinate getRevSeg(int idx) {
        int lastIdx = length-idx-1;
        if (lastIdx >= 0 && lastIdx < length) {
            return body[lastIdx].copy();
        }
        return null;
    }
}
