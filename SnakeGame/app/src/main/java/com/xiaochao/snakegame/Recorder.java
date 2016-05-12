package com.xiaochao.snakegame;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by xc on 2016/5/11.
 */
public class Recorder implements Observer {
    Snake snake;
    Food food;
    private static Recorder instance;
    @Override
    public void update(Observable observable, Object data) {
        if (observable instanceof Snake) {
            this.snake = (Snake) observable;
        }
        else {
            this.food = (Food) observable;
        }
    }
    private Recorder() {

    }
    public static Recorder getRecorder() {
        if (instance == null) {
            instance = new Recorder();
        }
        return instance;
    }
}
