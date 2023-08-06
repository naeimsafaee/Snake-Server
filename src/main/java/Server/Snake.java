package Server;


import Tools.Rand;

import java.awt.*;
import java.util.Random;

public class Snake {

    public int id;
    public int x;
    public int y;

    public int size;

    public int direction;

    public String color;

    public transient int socketId;

    public Snake() {
    }

    public Snake(boolean callYourSelf) {
        this.x = new Rand().rand(0, Global.cols);
        this.y = new Rand().rand(0, Global.rows);

        this.id = new Rand().rand();
        this.color = new Rand().randColor();
        this.direction = new Rand().rand(1, 5);

        this.size = Global.defaultSnakeSize;
    }

    public void move(int x, int y , int direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public void increase_size() {
        size += 1;
    }

}