package com.xiaochao.snakegame;

import android.graphics.Color;

import java.util.Observable;
import java.util.Random;

/**
 * Created by xc on 2016/5/11.
 */
public class Food extends Observable {
    int color;
    private MyPoint myPoint = null;
    Food(int x, int y) {
        this(x, y, Color.RED);
    }
    Food(int x, int y, int color) {
        this(new MyPoint(x,y), color);
    }
    Food(MyPoint myPoint) {
        this(myPoint, Color.RED);
    }
    Food(MyPoint myPoint, int color) {
        this.myPoint = myPoint;
        this.color = color;
        addObserver(Recorder.getRecorder());
        setChanged();
        notifyObservers();
    }
    MyPoint getMyPoint() {
        return myPoint;
    }
    static void createNewFood() {
        new Food(10,10);
    }
    void changeFoodPos(int maxX, int maxY, Snake snake) {
        Random random = new Random();
        myPoint.setX(Math.abs(random.nextInt() % maxX));
        myPoint.setY(Math.abs(random.nextInt() % maxY));
        while (snake.getBody().contains(myPoint)) {
            myPoint.setX(Math.abs(random.nextInt() % maxX));
            myPoint.setY(Math.abs(random.nextInt() % maxY));
        }
        notifyObservers();
    }
}
