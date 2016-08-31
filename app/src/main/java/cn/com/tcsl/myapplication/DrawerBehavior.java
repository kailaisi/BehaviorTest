package cn.com.tcsl.myapplication;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 对应activty_main2.xml
 * Created by wjx on 2016/8/30.
 */
public class DrawerBehavior  extends CoordinatorLayout.Behavior<View>{
    private int mStartY;
    private int mEndY;

    public DrawerBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof Toolbar;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        if(mStartY==0){
            mStartY= (int) dependency.getTop();
            mEndY=dependency.getHeight();
        }
        float percent = (float) dependency.getTop() / mStartY;
        child.setY(child.getHeight()*(-percent));
        return true;
    }
}
