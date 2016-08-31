package cn.com.tcsl.myapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * 对应activty_main3.xml
 * Created by wjx on 2016/8/30.
 */
public class CustomBehavior extends CoordinatorLayout.Behavior<View> {

    private final Context mContext;
    private float mCustomFinalHeight;
    private float mStartY;
    private float mStartAvaterX;
    private int mMaxAvaterHeight;

    public CustomBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomBehavior);
            mCustomFinalHeight = array.getDimension(R.styleable.CustomBehavior_finalHeight, 0);
            Log.d("CustomBehavior", "mCustomFinalHeight:" + mCustomFinalHeight);
            array.recycle();
        }
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof Toolbar;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        if (mStartY == 0) {
            mStartY = dependency.getY();
            Log.d("CustomBehavior", "mStartY:" + mStartY);
        }
        if (mStartAvaterX == 0) {
            mStartAvaterX = child.getLeft();
            Log.d("CustomBehavior", "mStartAvaterX:" + mStartAvaterX);
        }
        if (mMaxAvaterHeight == 0) {
            mMaxAvaterHeight = child.getHeight();
        }
        float percent = (float) dependency.getTop() / mStartY;
        //根据percent设置toolbar的透明度，当percent=1时，完全透明，当avater居中的时候，显示标题栏背景色
        dependency.getBackground().setAlpha((int)(255 * (1 - percent)));
        //child的Y变化，toolbar的位置dependency.getY() + dependency.getHeight() / 2 - mCustomFinalHeight / 2
        child.setY(dependency.getY() + dependency.getHeight() / 2 - mCustomFinalHeight / 2);
        //child的位置变化,随percent的由1→0，最开始 2*mStartAvaterX-->mStartAvaterX+mMaxAvaterHeight/2-mCustomFinalHeight/2
        float x = mStartAvaterX + mMaxAvaterHeight / 2 - mCustomFinalHeight / 2 + percent * (mStartAvaterX - mMaxAvaterHeight / 2 + mCustomFinalHeight / 2);
        child.setX(x);
        //child的宽和高，随着percent的1->0由mMaxAvaterHeight-->mCustomFinalHeight
        ViewGroup.LayoutParams layoutParams = child.getLayoutParams();
        layoutParams.height = (int) (mCustomFinalHeight + (mMaxAvaterHeight - mCustomFinalHeight) * percent);
        layoutParams.width = (int) (mCustomFinalHeight + (mMaxAvaterHeight - mCustomFinalHeight) * percent);
        child.setLayoutParams(layoutParams);
        return true;
    }
}
