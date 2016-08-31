package cn.com.tcsl.myapplication;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.BounceInterpolator;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewPropertyAnimator;

/**
 * 对应activty_main.xml
 * Created by wjx on 2016/8/29.
 */
public class FooterBehavior extends CoordinatorLayout.Behavior<View> {
    private int sinceDirectionChange;

    public FooterBehavior() {
    }

    public FooterBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
       //只取垂直滑动的方向进行处理
       return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        //根据滑动距离显示或者隐藏footview
        if(dy>0 && sinceDirectionChange<0 || dy<0 && sinceDirectionChange>0){
            //重置当前的已经移动距离为0
            ViewPropertyAnimator.animate(child).cancel();
            sinceDirectionChange=0;
        }
        sinceDirectionChange+=dy;
        //向上滑动距离大于高度，并且现在是可见的  dy>0,表示视图内容是从下往上移动，当滑动的高度大于视图高度的时候，关闭
        if(sinceDirectionChange>child.getHeight() && child.getVisibility()==View.VISIBLE){
            hide(child);
        }
        //当视图内容向下滑动的时候，直接显示
        else  if(sinceDirectionChange<0 && child.getVisibility()==View.GONE){
            show(child);
        }


    }


    /**
     * 显示某个视图
     * @param child
     */
    private void show(final View child) {
        ObjectAnimator animator=ObjectAnimator.ofFloat(child,"translationY",0.0f);
        animator.setDuration(100);
        animator.setInterpolator(new FastOutLinearInInterpolator());
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                child.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                hide(child);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }

    /**
     * 隐藏某个View
     * @param child
     */
    private void hide(final View child) {
        ObjectAnimator animator=ObjectAnimator.ofFloat(child,"translationY",child.getHeight());
        animator.setDuration(1000);
        animator.setInterpolator(new FastOutLinearInInterpolator());
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                child.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                show(child);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }

}
