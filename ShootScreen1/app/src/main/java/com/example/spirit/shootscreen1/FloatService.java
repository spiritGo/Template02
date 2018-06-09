package com.example.spirit.shootscreen1;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.spirit.shootscreen1.Utils.FloatUtil;

public class FloatService extends Service {

    private FloatUtil floatUtil;
    private boolean isAddView = false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        FloatBroadCast floatBroadCast = new FloatBroadCast();
        IntentFilter filter = new IntentFilter();
        filter.addAction(FloatUtil.CLOSE_ACTION);
        filter.addAction(FloatUtil.OPEN_ACTION);
        registerReceiver(floatBroadCast, filter);
        initUI();
    }

    private void initUI() {
        floatUtil = FloatUtil.getFloatUtil(this);
    }

    class FloatBroadCast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (FloatUtil.CLOSE_ACTION.equals(action)) {
                if (isAddView) {
                    isAddView = false;
                    floatUtil.removeFloatView();
                }
            } else if (FloatUtil.OPEN_ACTION.equals(action)) {
                Activity activity = null;
                if (!isAddView) {
                    floatUtil.addFloatView(activity);
                    isAddView = true;
                    floatUtil.moveView();
                }
            }
        }
    }
}
