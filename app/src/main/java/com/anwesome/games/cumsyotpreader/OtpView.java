package com.anwesome.games.cumsyotpreader;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by anweshmishra on 24/01/17.
 */
public class OtpView extends View {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int opened = 0,time = 0;
    private boolean openState = false;
    private MessagePart messagePart;
    private CirclePart circlePart;
    private WindowManager windowManager;
    public OtpView(Context context, String otp, WindowManager windowManager) {
        super(context);
        this.messagePart = new MessagePart(otp);
        this.windowManager = windowManager;
    }
    public void onDraw(Canvas canvas) {
        if(time == 0) {
            messagePart.setX(0);
            messagePart.setW(canvas.getWidth());
            messagePart.setY(canvas.getHeight()/2);
            messagePart.setH(canvas.getHeight()/2);
            int r = canvas.getHeight()/4;
            int x = canvas.getWidth()-r,y = r;
            circlePart = new CirclePart(x,y,r);
        }
        circlePart.draw(canvas,paint);
        if(circlePart.isOpen()) {
            messagePart.draw(canvas,paint);
        }
        time++;
    }
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            float x = event.getX(),y = event.getY();
            if(circlePart.isOpen()) {
                if(circlePart.containsTouch(x,y)) {
                    circlePart.messageClosed();
                }
                else if(messagePart.containsTouch(x,y)) {
                    windowManager.removeView(this);
                }
            }
            else {
                if(circlePart.containsTouch(x,y)) {
                    circlePart.messageOpened();
                }
            }
        }
        postInvalidate();
        return true;
    }
}
