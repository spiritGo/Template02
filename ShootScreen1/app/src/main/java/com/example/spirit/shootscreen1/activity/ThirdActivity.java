package com.example.spirit.shootscreen1.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.example.spirit.shootscreen1.FloatService;
import com.example.spirit.shootscreen1.R;
import com.example.spirit.shootscreen1.Utils.FloatUtil;

public class ThirdActivity extends Activity implements View.OnClickListener {
    private Button close;
    private Button open;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        initView();
        initUI();
    }

    private void initUI() {
        close.setOnClickListener(this);
        open.setOnClickListener(this);
        startService(new Intent(this, FloatService.class));
    }

    private void initView() {
        close = findViewById(R.id.close);
        open = findViewById(R.id.open);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.open:
                sendBroadCast(FloatUtil.OPEN_ACTION);
                break;
            case R.id.close:
                sendBroadCast(FloatUtil.CLOSE_ACTION);
                break;
        }
    }

    private void sendBroadCast(String action) {
        Intent intent = new Intent();
        intent.setAction(action);
        sendBroadcast(intent);
    }
}
