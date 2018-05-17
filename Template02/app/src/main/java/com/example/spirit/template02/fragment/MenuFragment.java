package com.example.spirit.template02.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.spirit.template02.activity.MainActivity;
import com.example.spirit.template02.utils.DencityUtil;

import java.util.ArrayList;
import java.util.Objects;

public class MenuFragment extends ListFragment {

    private static ArrayList<String> strs = new ArrayList<>();
    int padding = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (strs.size() > 0) {
            setListAdapter(new MyAdapter());
            padding = DencityUtil.dp2px(getContext(), 6);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getListView().setBackgroundColor(Color.GRAY);
        if (strs.size() > 0) {
            ContentBaseFragment.getTv_topTitle().setText(strs.get(0));
        }
    }

    public static void putStr(String str) {
        strs.add(str);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        MainActivity.getMainActivity().menuClose();
        ContentBaseFragment.getTv_topTitle().setText(strs.get(position));
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return strs.size();
        }

        @Override
        public String getItem(int position) {
            return strs.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = new TextView(getContext());
            }

            ((TextView) convertView).setTextColor(0xff000000);
            ((TextView) convertView).setTextSize(16);
            ((TextView) convertView).setText(getItem(position));
            convertView.setPadding(padding, padding, padding, padding);
            return convertView;
        }
    }
}
