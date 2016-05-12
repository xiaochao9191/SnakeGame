package com.xiaochao.snakegame;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.util.Iterator;
import java.util.LinkedList;


public class MainActivity extends AppCompatActivity implements View.OnTouchListener{
    private static Recorder recorder;
    private SnakeView snakeView;
    int normalMills = 300;
    int accelerMills = 100;
    Button up;
    Button down;
    Button left;
    Button right;
    int windowMaxX;
    int windowMaxY;
    int blkSz = 30;
    static {
        recorder = Recorder.getRecorder();
        Snake.createSnake();
        Food.createNewFood();

    }
    Handler handler;
    Runnable rNormal;
    Runnable rAcceler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    void init() {
        up = (Button) findViewById(R.id.up);
        down = (Button) findViewById(R.id.down);
        left = (Button) findViewById(R.id.left);
        right = (Button) findViewById(R.id.right);
        up.setOnTouchListener(this);
        down.setOnTouchListener(this);
        left.setOnTouchListener(this);
        right.setOnTouchListener(this);
        snakeView = (SnakeView) findViewById(R.id.snakeView);
        handler = new Handler();
        rNormal = new Runnable() {
            @Override
            public void run() {
                if (!recorder.snake.getAlive()) {
                    return;
                }
                if (crashDetect()) {
                    return;
                }
                if (!eatDetect()) {
                    recorder.snake.move(recorder.snake.direction);
                }
                snakeView.invalidate();
                handler.postDelayed(this, normalMills);
            }
        };
        handler.postDelayed(rNormal,normalMills);
        rAcceler = new Runnable() {
            @Override
            public void run() {
                if (!recorder.snake.getAlive()) {
                    return;
                }
                if (crashDetect()) {
                    return;
                }
                if (!eatDetect()) {
                    recorder.snake.move(recorder.snake.direction);
                }
                snakeView.invalidate();
                handler.postDelayed(this, accelerMills);
            }
        };
    }
    boolean crashDetect() {
        LinkedList<MyPoint> body = recorder.snake.getBody();
        Iterator<MyPoint> iter = body.iterator();
        MyPoint head = iter.next().copy();
        switch (recorder.snake.getDirection()) {
            case up:
                head.decY();
                break;
            case down:
                head.incY();
                break;
            case left:
                head.decX();
                break;
            case right:
                head.incX();
                break;
        }
        //撞身体
        while (iter.hasNext()) {
            MyPoint pt = iter.next();
            if (head.equals(pt) && iter.hasNext()) {
                recorder.snake.setAlive(false);
                snakeView.invalidate();
                return true;
            }
        }
        //撞墙
        if (windowMaxX == 0 || windowMaxY == 0) {
            windowMaxX = snakeView.getMeasuredWidth()/blkSz;
            windowMaxY = snakeView.getMeasuredHeight()/blkSz;
            return false;
        }
        if (head.getX() >= windowMaxX || head.getY() >= windowMaxY || head.getX() < 0 || head.getY() < 0) {
            recorder.snake.setAlive(false);
            snakeView.invalidate();
            return true;
        }
        return false;
    }
    boolean eatDetect() {
        LinkedList<MyPoint> body = recorder.snake.getBody();
        MyPoint head = body.getFirst().copy();
        switch (recorder.snake.getDirection()) {
            case up:
                head.decY();
                break;
            case down:
                head.incY();
                break;
            case left:
                head.decX();
                break;
            case right:
                head.incX();
                break;
        }
        if (head.equals(recorder.food.getMyPoint())) {
            recorder.snake.incHead(recorder.food.getMyPoint().copy());
            recorder.food.changeFoodPos(windowMaxX,windowMaxY,recorder.snake);
            return true;
        }
        return false;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_restart) {
            Snake.createSnake();
            Food.createNewFood();
            handler.removeCallbacks(rNormal);
            handler.removeCallbacks(rAcceler);
            handler.postDelayed(rNormal,normalMills);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (!recorder.snake.getAlive()) {
            return true;
        }
        boolean revDirection = false;
        Snake.Direction direct = recorder.snake.getDirection();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                switch (v.getId()){
                        case R.id.up:
                        if (direct == Snake.Direction.down) {
                            revDirection = true;
                            break;
                        }
                        recorder.snake.setDirection(Snake.Direction.up);
                        break;
                    case R.id.down:
                        if (direct == Snake.Direction.up) {
                            revDirection = true;
                            break;
                        }
                        recorder.snake.setDirection(Snake.Direction.down);
                        break;
                    case R.id.left:
                        if (direct == Snake.Direction.right) {
                            revDirection = true;
                            break;
                        }
                        recorder.snake.setDirection(Snake.Direction.left);
                        break;
                    case R.id.right:
                        if (direct == Snake.Direction.left) {
                            revDirection = true;
                            break;
                        }
                        recorder.snake.setDirection(Snake.Direction.right);
                        break;
                }
                if (!revDirection) {
                    if (crashDetect()) {
                        return true;
                    }
                    if (!eatDetect()) {
                        recorder.snake.move(recorder.snake.direction);
                    }
                    snakeView.invalidate();
                    handler.removeCallbacks(rNormal);
                    handler.postDelayed(rAcceler, normalMills);
                }
                break;
            case MotionEvent.ACTION_UP:
                switch (v.getId()){
                    case R.id.up:
                        if (direct == Snake.Direction.down) {
                            revDirection = true;
                            break;
                        }
                        break;
                    case R.id.down:
                        if (direct == Snake.Direction.up) {
                            revDirection = true;
                            break;
                        }
                        break;
                    case R.id.left:
                        if (direct == Snake.Direction.right) {
                            revDirection = true;
                            break;
                        }
                        break;
                    case R.id.right:
                        if (direct == Snake.Direction.left) {
                            revDirection = true;
                            break;
                        }
                        break;
                }
                if (!revDirection) {
                    handler.removeCallbacks(rAcceler);
//                    handler.removeCallbacks(rNormal);
                    handler.postDelayed(rNormal, normalMills);
                }
                break;
        }
        return false;
    }
}
