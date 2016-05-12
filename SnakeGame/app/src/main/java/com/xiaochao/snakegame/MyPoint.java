package com.xiaochao.snakegame;

/**
 * Created by xc on 2016/5/11.
 */
public class MyPoint {
    private int x;
    private int y;
    MyPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }
    void setPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }
    void setPoint(MyPoint pt) {
        x = pt.getX();
        y = pt.getY();
    }
    void setX(int x) {
        this.x = x;
    }
    void setY(int y) {
        this.y = y;
    }
    int getX() {
        return x;
    }
    int getY() {
        return y;
    }
    void incX() {
        incX(1);
    }
    void incX(int dis) {
        x += dis;
    }
    void incY() {
        incY(1);
    }
    void incY(int dis) {
        y += dis;
    }
    void decX() {
        decX(1);
    }
    void decX(int dis) {
        x -= dis;
    }
    void decY() {
        decY(1);
    }
    void decY(int dis) {
        y -= dis;
    }

    @Override
    public boolean equals(Object o) {
        MyPoint pt = (MyPoint) o;
        return this.x == pt.x && this.y == pt.y;
    }

    MyPoint copy() {
        return new MyPoint(getX(),getY());
    }
}
