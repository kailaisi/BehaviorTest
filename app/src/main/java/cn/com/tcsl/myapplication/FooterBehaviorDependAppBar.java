package cn.com.tcsl.myapplication;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toolbar;

/**
 * 对应activty_main1.xml
 * Created by wjx on 2016/8/30.
 */
public class FooterBehaviorDependAppBar extends CoordinatorLayout.Behavior<View> {
    public FooterBehaviorDependAppBar() {
    }

    public FooterBehaviorDependAppBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        float translationY =dependency.getTop();
        float percent=Math.abs(translationY/dependency.getHeight());
        Log.d("FooterBehaviorDependApp", "translationY:" + translationY);
        child.setTranslationY(child.getHeight()*percent);
        return true;
    }
}
