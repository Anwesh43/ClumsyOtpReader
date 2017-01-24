package com.anwesome.games.cumsyotpreader;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.RectF;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by anweshmishra on 09/01/17.
 */
public class OtpReaderService extends Service{
    private WindowManager windowManager;
    private DisplayManager displayManager;
    private Display display;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int width,height;
    public void onCreate() {
        super.onCreate();
        Intent intent = new Intent();
        intent.setAction(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
        registerReceiver(new SmsReceiver(),new IntentFilter(intent.getAction()));
        Context context = getApplicationContext();
        windowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        displayManager = (DisplayManager) context.getSystemService(DISPLAY_SERVICE);
        display = displayManager.getDisplay(0);
        Point size = new Point();
        display.getRealSize(size);
        width = size.x;
        height = size.y;
        addOtpView("hello");
    }
    public void addOtpView(String text) {
        final OtpView otpView = new OtpView(getApplicationContext(),text);
        final Handler handler = new Handler();
        final WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(200,200,WindowManager.LayoutParams.TYPE_PHONE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,PixelFormat.TRANSLUCENT);
        layoutParams.x = 400;
        layoutParams.y = 400;
        windowManager.addView(otpView,layoutParams);
        otpView.setElevation(10);
        otpView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                windowManager.removeView(view);
                return true;
            }
        });
    }
    public IBinder onBind(Intent intent) {
        return null;
    }
    private class OtpView extends View {
        private String text;
        public OtpView(Context context,String text) {
            super(context);
            this.text = text;
        }
        public void onDraw(Canvas canvas) {
            paint.setColor(Color.parseColor("#009688"));
            Path path = new Path();
            path.addCircle(canvas.getWidth()/2,canvas.getHeight()/2,canvas.getWidth()/2, Path.Direction.CCW);
            canvas.clipPath(path);
            canvas.drawRect(new RectF(0,0,canvas.getWidth(),canvas.getHeight()),paint);
            paint.setTextSize(40);
            paint.setColor(Color.WHITE);
            canvas.drawText(text,canvas.getWidth()/2-paint.measureText(text)/2,canvas.getHeight()/2,paint);
            String closeHelper = "(click on me to dismiss)";
            paint.setTextSize(20);
            canvas.drawText(closeHelper,canvas.getWidth()/2-paint.measureText(closeHelper)/2,3*canvas.getHeight()/5,paint);
        }
    }
    private class SmsReceiver extends BroadcastReceiver {
        public void onReceive(Context context,Intent intent) {
            String format = intent.getExtras().getString("format");
            Object pdus[] = (Object[])intent.getExtras().get("pdus");
            String message = "";
            for(int i=0;i<pdus.length;i++) {
                SmsMessage smsBody = SmsMessage.createFromPdu((byte[])pdus[i]);
                message = message+smsBody.getDisplayMessageBody();
            }
            message = message.replaceAll("\\n"," ");
            message = message.toLowerCase();
            if(message.indexOf("otp")!=-1 || message.indexOf("one time password")!=-1) {
                String[] tokens = message.split(" ");
                int otpNumber = 0;
                for(String token:tokens) {
                    if(token.length() == 4 || token.length() ==6) {
                        try {
                            otpNumber = Integer.parseInt(token);
                            break;
                        }
                        catch (Exception ex) {

                        }
                    }
                }
                addOtpView(""+otpNumber);
            }
        }
    }
}
