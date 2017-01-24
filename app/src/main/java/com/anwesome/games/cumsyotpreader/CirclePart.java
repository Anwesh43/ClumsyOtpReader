package com.anwesome.games.cumsyotpreader;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by anweshmishra on 24/01/17.
 */
public class CirclePart {
    private float x,y,r;
    private boolean openState = false;
    private int opened = 0;
    public CirclePart(float x,float y,float r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }
    public void messageOpened() {
        openState = true;
        opened++;
    }
    public void messageClosed() {
        openState = false;
    }
    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.parseColor("#00BCD4"));
        canvas.drawCircle(x,y,r,paint);
        if(opened == 0) {
            paint.setColor(Color.parseColor("#d32f2f"));
            canvas.drawCircle(x, r / 5, r / 5, paint);
            paint.setColor(Color.WHITE);
            paint.setTextSize(r / 5);
            canvas.drawText("1", x - r / 10, r/5, paint);
        }
    }
    public void setOpenState(boolean openState) {
        this.openState = openState;
    }
    public boolean isOpen() {
        return openState;
    }
    public boolean containsTouch(float x,float y) {
        return x>=this.x-r && x<=this.x+r && y>=this.y-r && y<=this.y+r;
    }
    public int hashCode() {
        return (int)x+(int)y+(int)r+opened;
    }
}
