package com.bingstar.bingmall.main.bottom;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bingstar.bingmall.R;

/**
 * 功能：
 * Created by yaoyafeng on 17/2/16 下午3:16
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by user: {修改人} 17/2/16 下午3:16
 * @modify by reason:{方法名}:{原因}
 */

public class CircleMenuLayout extends RelativeLayout {

    public boolean isClose = true;
    private boolean isAnimation = false;

    private OnClickListener viewClickListener1;
    private OnClickListener viewClickListener2;
    private OnClickListener viewClickListener3;
    private OnClickListener viewClickListener4;

    private Button mBottomDiscount; //优惠卷

    public CircleMenuLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isClose) {
                    dissMissChildView();
                }
            }
        });
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mBottomDiscount = (Button) findViewById(R.id.coumpon_bottom_info2);
    }


    public void badgeView(boolean isShow) {
        if (isShow) {
            mBottomDiscount.setVisibility(VISIBLE);
        } else {
            mBottomDiscount.setVisibility(GONE);
        }
    }

    public void dissMissChildView() {
        if (isClose) {
            return;
        }
        final int count = getChildCount();
        isAnimation = true;
        for (int i = 0; i < count; i++) {
            final View view = getChildAt(i);
            final int position = i;
            AlphaAnimation alphaAnim = new AlphaAnimation(1.0f, 0.0f);
            alphaAnim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    view.setClickable(false);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    view.clearAnimation();
                    if (position == count - 1) {
                        setVisibility(GONE);
                        isClose = true;
                        isAnimation = false;
                    }
                    view.setVisibility(INVISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            alphaAnim.setDuration(200);
            view.startAnimation(alphaAnim);
        }


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //测量子view
        for (int i = 0, count = getChildCount(); i < count; i++) {
            //需要传入父view的spec
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
        }
    }

    public void toggleMenu() {
        if (isAnimation) {
            return;
        }
        final int count = getChildCount();
        isAnimation = true;
        for (int i = 0; i < count; i++) {
            final View view = getChildAt(i);
            AnimatorSet set = new AnimatorSet();
            final int position = i;
            int startTime = 100;
            ObjectAnimator rotateAnim;
            ObjectAnimator translateAnimX = null;
            ObjectAnimator translateAnimY = null;
            ObjectAnimator alphaAnim;
            if (isClose) {
                setVisibility(VISIBLE);
                switch (i) {
                    case 0:
                        translateAnimX = ObjectAnimator.ofFloat(view, "translationX", 0, -144);
                        translateAnimY = ObjectAnimator.ofFloat(view, "translationY", 0, -100);
                        break;
                    case 1:
                        translateAnimX = ObjectAnimator.ofFloat(view, "translationX", 0, -47);
                        translateAnimY = ObjectAnimator.ofFloat(view, "translationY", 0, -100);
                        break;
                    case 2:
                        translateAnimX = ObjectAnimator.ofFloat(view, "translationX", 0, 47);
                        translateAnimY = ObjectAnimator.ofFloat(view, "translationY", 0, -100);
                        break;
                    case 3:
                        translateAnimX = ObjectAnimator.ofFloat(view, "translationX", 0, 144);
                        translateAnimY = ObjectAnimator.ofFloat(view, "translationY", 0, -100);
                        break;
                }
                alphaAnim = ObjectAnimator.ofFloat(view, "alpha", 0, 1);
                rotateAnim = ObjectAnimator.ofFloat(view, "rotation", 0, 360);
                set.setStartDelay(startTime * i);
                set.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        view.setClickable(false);
                        view.setVisibility(VISIBLE);
                        view.setAlpha(0);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        animation.removeAllListeners();
                        view.clearAnimation();
                        view.setClickable(true);
                        if (position == count - 1) {
                            isClose = false;
                            isAnimation = false;
                        }
                    }


                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
            } else {
                switch (i) {
                    case 0:
                        translateAnimX = ObjectAnimator.ofFloat(view, "translationX", -144, 0);
                        translateAnimY = ObjectAnimator.ofFloat(view, "translationY", -100, 0);
                        break;
                    case 1:
                        translateAnimX = ObjectAnimator.ofFloat(view, "translationX", -47, 0);
                        translateAnimY = ObjectAnimator.ofFloat(view, "translationY", -100, 0);
                        break;
                    case 2:
                        translateAnimX = ObjectAnimator.ofFloat(view, "translationX", 47, 0);
                        translateAnimY = ObjectAnimator.ofFloat(view, "translationY", -100, 0);
                        break;
                    case 3:
                        translateAnimX = ObjectAnimator.ofFloat(view, "translationX", 144, 0);
                        translateAnimY = ObjectAnimator.ofFloat(view, "translationY", -100, 0);
                        break;
                }
                alphaAnim = ObjectAnimator.ofFloat(view, "alpha", 1, 0);
                rotateAnim = ObjectAnimator.ofFloat(view, "rotation", 0, -360);
                set.setStartDelay(startTime * (count - 1 - i));
                set.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        view.setClickable(false);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (position == 0) {
                            setVisibility(GONE);
                            isClose = true;
                            isAnimation = false;
                        }
                        animation.removeAllListeners();
                        view.clearAnimation();
                        view.setVisibility(INVISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
            }
            set.playTogether(
                    alphaAnim, rotateAnim, translateAnimX, translateAnimY
            );
            set.setDuration(startTime);
            set.start();
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    dissMissChildView();
                    if (position == 0 && viewClickListener1 != null) {
                        viewClickListener1.onClick(v);
                    } else if (position == 1 && viewClickListener2 != null) {
                        viewClickListener2.onClick(v);
                    } else if (position == 2 && viewClickListener3 != null) {
                        viewClickListener3.onClick(v);
                    } else if (position == 3 && viewClickListener4 != null) {
                        viewClickListener4.onClick(v);
                    }
                }
            });
        }
    }

    public void setViewOneClickListener(OnClickListener viewClickListener) {
        this.viewClickListener1 = viewClickListener;
    }

    public void setViewTwoClickListener(OnClickListener viewClickListener) {
        this.viewClickListener2 = viewClickListener;
    }

    public void setViewThreeClickListener(OnClickListener viewClickListener) {
        this.viewClickListener3 = viewClickListener;
    }

    public void setViewFourClickListener(OnClickListener viewClickListener) {
        this.viewClickListener4 = viewClickListener;
    }

}
