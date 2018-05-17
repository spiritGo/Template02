package com.example.spirit.template02.fragment;

import android.support.v4.app.Fragment;

import com.example.spirit.template02.activity.MainActivity;

import java.util.ArrayList;

public class FragmentFactory {
    private static FragmentFactory fragmentFactory;
    private ArrayList<Fragment> fragments = new ArrayList<>();

    public static FragmentFactory getFragmentFactory() {
        if (fragmentFactory == null) {
            synchronized (FragmentFactory.class) {
                if (fragmentFactory == null) {
                    fragmentFactory = new FragmentFactory();
                }
            }
        }
        return fragmentFactory;
    }

    public void addFragment(int key, Fragment fragment) {
        fragments.add(key, fragment);
    }

    public void addFragment(Fragment fragment) {
        fragments.add(fragment);
        MenuFragment.putStr(fragment.getClass().getSimpleName());
    }

    public Fragment getFragment(int key) {
        if (fragments.size() > 0) {
            return fragments.get(key);
        }

        return null;
    }

    public Class<? extends Fragment> getClazz(int key) {
        return fragments.get(key).getClass();
    }

    public int getCount() {
        return fragments.size();
    }
}
