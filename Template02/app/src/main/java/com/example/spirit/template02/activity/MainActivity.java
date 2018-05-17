package com.example.spirit.template02.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Window;

import com.example.spirit.template02.R;
import com.example.spirit.template02.fragment.ContentBaseFragment;
import com.example.spirit.template02.fragment.MenuFragment;
import com.example.spirit.template02.view.SlideMenuLayout;

public class MainActivity extends FragmentActivity {

    private SlideMenuLayout slide_layout;
    private static MainActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);

        ContentBaseFragment baseFragment = new ContentBaseFragment();
        MenuFragment menuFragment = new MenuFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.contentView, baseFragment).commit();
        fragmentManager.beginTransaction().add(R.id.menuView, menuFragment).commit();

        slide_layout = findViewById(R.id.slide_layout);
        mainActivity = MainActivity.this;
    }

    public static MainActivity getMainActivity() {
        return mainActivity;
    }

    public void menuOpen() {
        slide_layout.open();
    }

    public void menuClose() {
        slide_layout.close();
    }

    public float getFracton() {
        return slide_layout.getFraction();
    }

    public SlideMenuLayout.State getState() {
        return slide_layout.getCurrentState();
    }

    public SlideMenuLayout getSlide_layout() {
        return slide_layout;
    }
}
