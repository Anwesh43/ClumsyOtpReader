package com.anwesome.games.cumsyotpreader;

import android.graphics.*;

/**
 * Created by anweshmishra on 24/01/17.
 */
public class MessagePart {
    private float x,y,w,h;
    private String text;
    public MessagePart(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public float getH() {

        return h;
    }

    public void setH(float h) {
        this.h = h;
    }

    public float getW() {
        return w;
    }

    public void setW(float w) {
        this.w = w;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.parseColor("#3F51B5"));
        float r = w/2;
        if(h<w) {
            r = h/2;
        }
        canvas.drawRoundRect(new RectF(x,y,x+w,y+h),r,r,paint);
        paint.setColor(Color.WHITE);
        paint.setTextSize(canvas.getWidth()/10);
        canvas.drawText(text,x+w/2-paint.measureText(text)/2,y+h/2-paint.getTextSize()/2,paint);
    }
    public boolean containsTouch(float x,float y) {
        return x>=this.x && x<=this.x+w && y>=this.y && y<=this.y+h;
    }
}
