package com.example.spirit.shootscreen1.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.spirit.shootscreen1.R;
import com.example.spirit.shootscreen1.Utils.ScreenShotUtil;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import java.io.File;
import java.io.FileOutputStream;

public class SecondActivity extends Activity implements View.OnClickListener {
    private Button btnShot;
    private File sourcePath;
    private Button open;
    private Button close;
    private WindowManager windowManager;
    private Button btnFloatView;
    private WindowManager.LayoutParams params;
    private boolean isFloatVisible = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initUI();
    }

    private void initUI() {
        btnShot.setOnClickListener(this);
        open.setOnClickListener(this);
        close.setOnClickListener(this);
    }

    private void startCrop() {
        Uri sourceUri = Uri.fromFile(sourcePath);
        Uri destUri = Uri.fromFile(new File(getCacheDir(), "text.png"));
        UCrop uCrop = UCrop.of(sourceUri, destUri);
        UCrop.Options options = new UCrop.Options();
        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
        options.setFreeStyleCropEnabled(true);
        uCrop.withOptions(options);
        uCrop.start(this);
    }

    private void initView() {
        btnShot = findViewById(R.id.btn_shot);
        open = findViewById(R.id.open);
        close = findViewById(R.id.close);
        btnFloatView = new Button(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_shot:
                Bitmap screenBitmap = ScreenShotUtil.getScreenBitmap(SecondActivity.this);
                try {
                    sourcePath = new File(getCacheDir(), "screenBitmap,png");
                    FileOutputStream fileOutputStream = new FileOutputStream(sourcePath);
                    screenBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                startCrop();
                break;
            case R.id.close:
                if (isFloatVisible) {
                    windowManager.removeView(btnFloatView);
                    isFloatVisible = false;
                }
                break;
            case R.id.open:
                if (!isFloatVisible) {
                    btnFloatView.setText(R.string.floatWindow);
                    initFloatView();
                    windowManager.addView(btnFloatView, params);
                    isFloatVisible = true;
                }
                break;
        }
    }

    private void initFloatView() {
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        params = new WindowManager.LayoutParams();
        params.type = WindowManager.LayoutParams.TYPE_PHONE;//下拉通知栏不可见
        // 设置Window flag,锁定悬浮窗 ,若不设置，悬浮窗会占用整个屏幕的点击事件，FLAG_NOT_FOCUSABLE不设置会导致菜单键和返回键失效
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager
                .LayoutParams.FLAG_NOT_FOCUSABLE;
        params.format = PixelFormat.RGBA_8888;//设置图片格式，效果为背景透明
        params.width = 200;
        params.height = 200;
    }
}
