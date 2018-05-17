package com.example.spirit.template02.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.example.spirit.template02.utils.ColorUtil;
import com.nineoldandroids.view.ViewHelper;

public class SlideMenuLayout extends FrameLayout {

    private View contentView;
    private View menuView;
    private ViewDragHelper dragHelper = null;
    private int menuWidth;
    private int contentWidth;
    private float fraction;

    public enum State {
        OPEN, CLOSE
    }

    private State currentState = State.CLOSE;

    public SlideMenuLayout(@NonNull Context context) {
        this(context, null);
    }

    public SlideMenuLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideMenuLayout(@NonNull Context context, @Nullable AttributeSet attrs, int
            defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        DragCallBack callback = new DragCallBack();
        if (dragHelper == null) {
            dragHelper = ViewDragHelper.create(this, callback);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        menuView = getChildAt(0);
        contentView = getChildAt(1);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        menuView.layout(-240, 0, 0, menuView.getMeasuredHeight());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        menuWidth = menuView.getMeasuredWidth();
        contentWidth = contentView.getMeasuredWidth();
    }

    class DragCallBack extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            return child == menuView || child == contentView;
        }

        @Override
        public int getViewHorizontalDragRange(@NonNull View child) {
            return 240;
        }

        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            if (child == contentView) {
                if (left < 0) {
                    left = 0;
                } else if (left > 240) {
                    left = 240;
                }
            } else if (child == menuView) {
                if (left < -240) {
                    left = -240;
                } else if (left > 0) {
                    left = 0;
                }
            }
            return left;
        }

        @Override
        public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx,
                                          int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            if (changedView == contentView) {
                menuView.layout(left - menuWidth, menuView.getTop() + dy,
                        left, menuView.getBottom() + dy);
            } else if (changedView == menuView) {
                contentView.layout(menuView.getRight(), menuView.getTop(), menuView.getRight() +
                        contentWidth, menuView.getBottom());
            }

            fraction = (contentView.getLeft() / ((float) menuWidth)) * 0.16f;

            ViewHelper.setScaleX(contentView, 1 - fraction);
            ViewHelper.setScaleY(contentView, 1 - fraction);
            contentView.getBackground().setColorFilter((Integer) ColorUtil.evaluateColor(0.6f -
                    fraction, Color.WHITE, Color.BLACK), PorterDuff.Mode.SRC_OVER);

            if (listener != null) {
                listener.onFractionChanged(fraction);
            }
        }

        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            if (contentView.getLeft() < menuWidth / 2) {
                close();

            } else {
                open();

            }

            if (xvel > 160 && currentState != State.OPEN) {
                open();
            } else if (xvel < -160 && currentState != State.CLOSE) {
                close();
            }
        }
    }


    public float getFraction() {
        return fraction;
    }

    public State getCurrentState() {
        return currentState;
    }

    public void open() {
        dragHelper.smoothSlideViewTo(contentView, 240, 0);
        ViewCompat.postInvalidateOnAnimation(SlideMenuLayout.this);
        currentState = State.OPEN;

    }

    private OnFractionChangedListener listener;

    public void setListener(OnFractionChangedListener listener) {
        this.listener = listener;
    }

    public interface OnFractionChangedListener {
        void onFractionChanged(float fraction);
    }

    public void close() {
        dragHelper.smoothSlideViewTo(contentView, 0, 0);
        ViewCompat.postInvalidateOnAnimation(SlideMenuLayout.this);
        currentState = State.CLOSE;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return dragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public void computeScroll() {
        if (dragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(SlideMenuLayout.this);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dragHelper.processTouchEvent(event);
        return true;
    }
}
