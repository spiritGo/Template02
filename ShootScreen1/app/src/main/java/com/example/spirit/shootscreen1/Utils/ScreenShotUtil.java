package com.example.spirit.shootscreen1.Utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;

public class ScreenShotUtil {

    public static Bitmap getScreenBitmap(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        decorView.setDrawingCacheEnabled(true);
        decorView.buildDrawingCache();
        return decorView.getDrawingCache();
    }
}
