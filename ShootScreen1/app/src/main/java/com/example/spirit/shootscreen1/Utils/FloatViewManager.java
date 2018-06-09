package com.example.spirit.shootscreen1.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.spirit.shootscreen1.R;

public class FloatViewManager {

    private Context context;
    private static FloatViewManager manager;
    private final WindowManager wm;
    private final ImageView floatView;
    private WindowManager.LayoutParams params;

    @SuppressLint("ClickableViewAccessibility")
    private FloatViewManager(final Context context) {
        this.context = context;
        wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        floatView = new ImageView(context);
        floatView.setImageResource(R.mipmap.screen_shot);
        floatView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        floatView.setOnTouchListener(new View.OnTouchListener() {

            private float x0;
            private float startY;
            private float startX;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getRawX();
                        startY = event.getRawY();
                        x0 = this.startX;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float x = event.getRawX();
                        float y = event.getRawY();
                        float dx = x - this.startX;
                        float dy = y - startY;
                        params.x += dx;
                        params.y += dy;
                        if (Math.abs(dx) > 6 || Math.abs(dy) > 6) {
                            floatView.setImageResource(R.mipmap.bag);
                        }
                        if (wm != null) {
                            wm.updateViewLayout(floatView, params);
                        }
                        this.startX = x;
                        startY = y;
                        break;
                    case MotionEvent.ACTION_UP:
                        float rawX = event.getRawX();

                        if (rawX > getScreenWidth() / 2) {
                            params.x = getScreenWidth() - floatView.getWidth();
                        } else {
                            params.x = 0;
                        }
                        if (wm != null) {
                            floatView.setImageResource(R.mipmap.screen_shot);
                            wm.updateViewLayout(floatView, params);
                        }
                        return Math.abs(rawX - x0) > 6;
                }
                return false;
            }
        });

        floatView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "点击了", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public int getScreenWidth() {
        return wm.getDefaultDisplay().getWidth();
    }

    public static FloatViewManager getInstance(Context context) {
        if (manager == null) {
            synchronized (FloatViewManager.class) {
                if (manager == null) {
                    manager = new FloatViewManager(context);
                }
            }
        }
        return manager;
    }

    public void showFloatView() {
        params = new WindowManager.LayoutParams();
        params.width = 50;
        params.height = 50;
        params.type = WindowManager.LayoutParams.TYPE_PHONE;
        params.format = PixelFormat.RGBA_8888;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager
                .LayoutParams.FLAG_NOT_FOCUSABLE;
        params.gravity = Gravity.TOP | Gravity.START;
        params.x = 0;
        params.y = 0;
        wm.addView(floatView, params);
    }

}
