package com.xiaochao.snakegame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by xc on 2016/5/11.
 */
public class SnakeView extends View {
    Recorder recorder;
    Paint snakePaint;
    Paint foodPaint;
    Paint gameOverPaint;
    int blkSz = 30;
    public SnakeView(Context context) {
        super(context);
        init();
    }

    public SnakeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public SnakeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setBlkSz(int blkSz) {
        this.blkSz = blkSz;
    }

    void init() {
        recorder = Recorder.getRecorder();
        snakePaint = new Paint();
        foodPaint = new Paint();
        gameOverPaint = new Paint();
        snakePaint.setColor(Color.GREEN);
        foodPaint.setColor(Color.RED);
        gameOverPaint.setColor(Color.RED);
        gameOverPaint.setTextAlign(Paint.Align.CENTER);
        gameOverPaint.setStrokeWidth(3);
        gameOverPaint.setTextSize(80);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);
        if (recorder != null && recorder.snake != null) {
            if (recorder.snake.getAlive()) {
                drawSnake(canvas, recorder.snake, snakePaint);
                drawFood(canvas, recorder.food, foodPaint);
            } else {
                Rect rect = new Rect(0,0,getMeasuredWidth(),getMeasuredHeight());;
                Paint.FontMetricsInt fontMetrics = gameOverPaint.getFontMetricsInt();
                int baseline = (rect.bottom + rect.top - fontMetrics.bottom - fontMetrics.top) / 2;
                canvas.drawText("Game Over", rect.centerX(), baseline, gameOverPaint);
            }
        }
    }
    void drawSnake(Canvas canvas, Snake snake, Paint paint) {
        LinkedList<MyPoint> body = snake.getBody();
        if (body == null) {
            return;
        }
        Iterator<MyPoint> iter = body.iterator();
        while (iter.hasNext()) {
            MyPoint pt = iter.next();
            RectF rect = new RectF(blkSz*pt.getX(),blkSz*pt.getY(),blkSz*(pt.getX()+1),blkSz*(pt.getY()+1));
            canvas.drawRect(rect,paint);
        }
    }
    void drawFood(Canvas canvas, Food food, Paint paint) {
        MyPoint pt = food.getMyPoint();
        if (pt == null) {
            return;
        }
        RectF rect = new RectF(blkSz*pt.getX(),blkSz*pt.getY(),blkSz*(pt.getX()+1),blkSz*(pt.getY()+1));
        canvas.drawRect(rect, paint);
    }
}
