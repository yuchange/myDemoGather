package com.bingstar.bingmall.goods.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bingstar.bingmall.R;
import com.bingstar.bingmall.goods.GoodsVideoAdapter;
import com.bingstar.bingmall.video.lib.JCVideoPlayer;
import com.bingstar.bingmall.video.lib.JCVideoPlayerStandard;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by zhangyifei on 17/7/10.
 */

public class MutableGoodsViewPager extends LinearLayout {

    private int dotResId;

    private LinearLayout linearLayout;

    private ViewPager mutable_viewpager;

    private ViewPager.OnPageChangeListener onPageChangeListener;

    private boolean scrollFlag = false;

    private Timer timer;

    private Handler handler;

    private int autoCurrIndex;

    private final int UPTATE_VIEWPAGER = 555;

    private boolean shouldReturn;
    private boolean shouldResume = false;


    public boolean getShouldResume() {
        return shouldResume;
    }

    public void setShouldResume(boolean shouldResume) {
        this.shouldResume = shouldResume;
    }


    public boolean getShouldReturn() {
        return shouldReturn;
    }

    public void setShouldReturn(boolean shouldReturn) {
        this.shouldReturn = shouldReturn;
    }


    public MutableGoodsViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.viewpager);
        dotResId = ta.getResourceId(R.styleable.viewpager_dotView, 0);
        int type = ta.getInt(R.styleable.viewpager_viewType, 0);
        switch (type) {
            case 0:
                LayoutInflater.from(context).inflate(R.layout.viewpager_dot_bottom, this);
                break;
            case 1:
                LayoutInflater.from(context).inflate(R.layout.viewpager_dot_under, this);
                break;
            default:
                LayoutInflater.from(context).inflate(R.layout.viewpager_dot_bottom, this);
                break;
        }
        ta.recycle();
        mutable_viewpager = (ViewPager) findViewById(R.id.mutable_viewpager);
        mutable_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (onPageChangeListener != null) {
                    onPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }
            }

            @Override
            public void onPageSelected(int position) {
                if (onPageChangeListener != null) {
                    onPageChangeListener.onPageSelected(position);
                }
                PagerAdapter adapter = mutable_viewpager.getAdapter();
                if (adapter instanceof GoodsVideoAdapter) {
                    GoodsVideoAdapter videoAdapter = (GoodsVideoAdapter) adapter;
                    Object currentView = videoAdapter.getCurrentView();
                    Log.e("onPageSelected", "GoodsVideoAdapter == onPageSelected: " + currentView);
                    if (currentView instanceof ImageView) {
                        setShouldResume(true);
                    }
                }
                changeDot(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (onPageChangeListener != null) {
                    onPageChangeListener.onPageScrollStateChanged(state);
                }
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    scrollFlag = false;
                } else if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                    scrollFlag = true;
                }
            }
        });
    }

    public void setDot(int size) {
        if(size==1){
            setDot(0, false);
        }else {
            setDot(size, false);

        }
    }

    /**
     * 设置点标
     *
     * @param size     数量
     * @param autoFlag 是否滚自动滑动
     */
    public void setDot(int size, boolean autoFlag) {
        if (dotResId == 0) {
            return;
        }
        if (timer != null) {
            timer.cancel();
        }
        linearLayout = (LinearLayout) findViewById(R.id.viewpager_dot_list);
        linearLayout.removeAllViews();
        linearLayout.setVisibility(VISIBLE);
        for (int i = 0; i < size; i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setImageResource(dotResId);
            LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(5, 5, 5, 5);
            imageView.setLayoutParams(layoutParams);
            linearLayout.addView(imageView);
            if (i != 0) {
                imageView.setAlpha((float) 0.5);
            }
        }
        autoCurrIndex = 0;
        if (autoFlag) {
            if (size < 2) {
                return;
            }
            if (handler == null) {
                handler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        switch (msg.what) {
                            case UPTATE_VIEWPAGER:
                                if (mutable_viewpager.getAdapter().getCount() > autoCurrIndex) {
                                    mutable_viewpager.setCurrentItem(autoCurrIndex);

                                }
                                break;
                        }
                    }
                };
            }
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Message message = new Message();
                    if (!scrollFlag) {
                        message.what = UPTATE_VIEWPAGER;
                        shouldResume();
                        if (getShouldReturn() && !shouldResume) {
                            return;
                        }
                        autoCurrIndex++;
                        if (autoCurrIndex == mutable_viewpager.getAdapter().getCount()) {
                            autoCurrIndex = 0;
                        }
                        if (handler != null) {
                            handler.sendMessage(message);
                        }
                    }
                }
            }, 1000, 3000);
        }
    }

    private void shouldResume() {
        PagerAdapter adapter = mutable_viewpager.getAdapter();
        if (adapter instanceof GoodsVideoAdapter) {
            GoodsVideoAdapter videoAdapter = (GoodsVideoAdapter) adapter;
            Object currentView = videoAdapter.getCurrentView();
            if (currentView instanceof JCVideoPlayerStandard) {
                JCVideoPlayerStandard videoView = (JCVideoPlayerStandard) currentView;
                if ((videoView.currentState == JCVideoPlayer.CURRENT_STATE_PLAYING ||
                        videoView.currentState == JCVideoPlayer.CURRENT_STATE_PREPARING ||
                        videoView.currentState == JCVideoPlayer.CURRENT_STATE_PLAYING_BUFFERING_START)) {
                    setShouldResume(false);
                } else {
                    setShouldResume(true);
                }
            }
        }
    }

    public void changeDot(int position) {
        if (linearLayout == null || linearLayout.getChildCount() < 2) {
            return;
        }
        int length = linearLayout.getChildCount();
        if (position > length) {
            return;
        }
        for (int i = 0; i < length; i++) {
            View view = linearLayout.getChildAt(i);
            if (i != position) {
                view.setAlpha((float) 0.5);
            } else {
                autoCurrIndex = i;
                view.setAlpha(1);
            }
        }
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        this.onPageChangeListener = onPageChangeListener;
    }

    public void setAdapter(PagerAdapter pagerAdapter) {
        mutable_viewpager.setAdapter(pagerAdapter);
    }

    public void clearChildView() {
        mutable_viewpager.removeAllViews();
    }

    public int getCurrentIndex() {
        return mutable_viewpager.getCurrentItem();
    }

    public void onDestory() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }

}
