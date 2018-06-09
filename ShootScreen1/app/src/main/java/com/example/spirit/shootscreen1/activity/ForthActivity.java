package com.example.spirit.shootscreen1.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.example.spirit.shootscreen1.FloatService2;
import com.example.spirit.shootscreen1.R;

public class ForthActivity extends Activity implements View.OnClickListener {
    private Button close;
    private Button open;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        initView();
    }

    private void initView() {
        close = findViewById(R.id.close);
        open = findViewById(R.id.open);

        close.setOnClickListener(this);
        open.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.open:
                startService(new Intent(ForthActivity.this, FloatService2.class));
                finish();
                break;
            case R.id.close:
                break;
        }
    }
}
