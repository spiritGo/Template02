package com.example.spirit.template02.utils;

import android.content.Context;

public class DencityUtil {

    public static int dp2px(Context context, float dip) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5);
    }
}
