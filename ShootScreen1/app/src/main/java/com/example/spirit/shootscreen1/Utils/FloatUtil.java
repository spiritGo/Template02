package com.example.spirit.shootscreen1.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.spirit.shootscreen1.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class FloatUtil {
    final public static String OPEN_ACTION = "open_action";
    final public static String CLOSE_ACTION = "close_action";
    @SuppressLint("StaticFieldLeak")
    private static FloatUtil floatUtil;
    private final WindowManager windowManager;
    private ImageView floatView;
    private final WindowManager.LayoutParams params;
    private int statusBarHeight;

    private FloatUtil(Context context) {
        floatView = new ImageView(context);
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        params = new WindowManager.LayoutParams();
        params.height = 50;
        params.width = 50;
        params.type = WindowManager.LayoutParams.TYPE_PHONE;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager
                .LayoutParams.FLAG_NOT_FOCUSABLE;
        params.format = PixelFormat.RGBA_8888;
        floatView.setImageResource(R.mipmap.screen_shot);
        floatView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
    }

    public static FloatUtil getFloatUtil(Context context) {
        if (floatUtil == null) {
            synchronized (FloatUtil.class) {
                if (floatUtil == null) {
                    floatUtil = new FloatUtil(context);
                }
            }
        }
        return floatUtil;
    }

    public void addFloatView(final Activity activity) {
        windowManager.addView(floatView, params);
        floatView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View decorView = activity.getWindow().getDecorView();
                decorView.buildDrawingCache(true);
                decorView.buildDrawingCache();
                Bitmap bitmap = decorView.getDrawingCache();
                try {
                    File file = new File(activity.getCacheDir(), "text.png");
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(file));

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void removeFloatView() {
        windowManager.removeView(floatView);
    }

    @SuppressLint("ClickableViewAccessibility")
    public void moveView() {
        floatView.setOnTouchListener(new View.OnTouchListener() {

            private float x;
            private float y;
            private float y1;
            private float x1;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x = event.getRawX();
                        y = event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        x1 = event.getRawX() - x;
                        y1 = event.getRawY() - y;
                        refreshView(((int) x1), ((int) y1));
                        break;
                }
                return true;
            }
        });
    }

    private void refreshView(int x, int y) {
        // 状态栏高度不能立即取，不然得到的值是0
        if (statusBarHeight == 0) {
            View rootView = floatView.getRootView();
            Rect r = new Rect();
            rootView.getWindowVisibleDisplayFrame(r);
            statusBarHeight = r.top;
        }

        params.x = x - 25;
        // y轴减去状态栏的高度，因为状态栏不是用户可以绘制的区域，不然拖动的时候会有跳动
        params.y = y - 25;// STATUS_HEIGHT;
        windowManager.updateViewLayout(floatView, params);
    }
}
