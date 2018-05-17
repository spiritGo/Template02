package com.example.spirit.template02.fragment;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.spirit.template02.R;
import com.example.spirit.template02.activity.MainActivity;
import com.example.spirit.template02.view.SlideMenuLayout;

public class ContentBaseFragment extends Fragment {

    public Context mContent = null;
    private ImageButton ib_menuOpen;
    private static TextView tv_topTitle;
    private SlideMenuLayout slide_layout;
    private static ContentBaseFragment fragment;
    private FragmentFactory fragmentFactory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (mContent == null) {
            mContent = getActivity();
        }

        fragment = ContentBaseFragment.this;

        View view = View.inflate(mContent, R.layout.fragment_base, null);
        ib_menuOpen = view.findViewById(R.id.ib_menuOpen);
        tv_topTitle = view.findViewById(R.id.tv_topTitle);

        event();

        FragmentTabHost tabHost = view.findViewById(android.R.id.tabhost);
        tabHost.setCurrentTab(0);
        tabHost.setup(getContext(), getChildFragmentManager(), R.id.fl_container);

        fragmentFactory = FragmentFactory.getFragmentFactory();
        fragmentFactory.addFragment(new Fragment01());
        fragmentFactory.addFragment(new Fragment02());
        System.out.println("count:"+fragmentFactory.getCount());

        for (int i = 0; i < fragmentFactory.getCount(); i++) {
            createSpec(tabHost, i);
        }

        tabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);

        return view;
    }

    private void createSpec(FragmentTabHost tabHost, int position) {
        TabHost.TabSpec tab = tabHost.newTabSpec("tab" + position).setIndicator(buildTab());
        Bundle bundle = new Bundle();
        bundle.putString("text", "tab" + position);
        tabHost.addTab(tab, fragmentFactory.getClazz(position), bundle);
    }

    public View buildTab() {
        View tabView = View.inflate(getContext(), R.layout.tab_layout, null);
        ImageView iv_tab = tabView.findViewById(R.id.iv_tab);
        TextView tv_tab = tabView.findViewById(R.id.tv_tab);
        iv_tab.setImageResource(R.mipmap.cate_0);
        tv_tab.setText("tabN");
        tabView.setBackgroundResource(R.drawable.tab_bg);

        return tabView;
    }

    private void event() {

        slide_layout = MainActivity.getMainActivity().getSlide_layout();
        ib_menuOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.getMainActivity().getState() == SlideMenuLayout.State.OPEN) {
                    MainActivity.getMainActivity().menuClose();
                } else {
                    MainActivity.getMainActivity().menuOpen();
                }
            }
        });

        slide_layout.setListener(new SlideMenuLayout.OnFractionChangedListener() {
            @Override
            public void onFractionChanged(float fraction) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(ib_menuOpen, "rotation", 0, 360);
                animator.setDuration(350);
                animator.start();
            }
        });
    }

    public static TextView getTv_topTitle() {
        return tv_topTitle;
    }

    public static ContentBaseFragment getFragment() {
        return fragment;
    }
}
