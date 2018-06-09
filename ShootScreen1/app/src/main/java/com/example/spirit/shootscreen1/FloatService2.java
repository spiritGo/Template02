package com.example.spirit.shootscreen1;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.spirit.shootscreen1.Utils.FloatViewManager;

public class FloatService2 extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        FloatViewManager manager = FloatViewManager.getInstance(this);
        manager.showFloatView();
    }
}
