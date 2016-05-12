package com.xiaochao.snakegame;

import java.util.LinkedList;
import java.util.Observable;

/**
 * Created by xc on 2016/5/11.
 */
public class Snake extends Observable{
    enum Direction {
        up,down,left,right;
    }
    boolean alive;
    private LinkedList<MyPoint> body = null;
    Direction direction = Direction.right;
    private Snake() {
        body = new LinkedList<>();
        setAlive(true);
        incHead(9, 5);
        incTail(8, 5);
        incTail(7, 5);
        addObserver(Recorder.getRecorder());
        setChanged();
        notifyObservers();
    }

    static void createSnake(){
        new Snake();
    }

    public LinkedList<MyPoint> getBody() {
        return body;
    }

    void incHead(int x, int y) {
        body.addFirst(new MyPoint(x,y));
    }
    void incHead(MyPoint pt) {
        body.addFirst(pt);
    }
    void incTail(int x, int y) {
        body.addLast(new MyPoint(x, y));
    }
    void incTail(MyPoint pt) {
        body.addLast(pt);
    }

    void move(Direction d) {
        if (!alive) {
            return;
        }
        for (int i = body.size()-1; i >= 0; i--) {
            MyPoint ptSnake = body.get(i);
            if (i == 0){
                switch (d) {
                    case up:
                        ptSnake.decY();
                        break;
                    case down:
                        ptSnake.incY();
                        break;
                    case left:
                        ptSnake.decX();
                        break;
                    case right:
                        ptSnake.incX();
                        break;
                    default:
                        break;
                }
            }
            else {
                MyPoint temp = body.get(i - 1);
                ptSnake.setPoint(temp.getX(),temp.getY());
            }
        }
        notifyObservers();
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
    boolean getAlive() {
        return alive;
    }
    void setDirection(Direction d) {
        direction = d;
        notifyObservers();
    }
    Direction getDirection() {
        return direction;
    }

}
