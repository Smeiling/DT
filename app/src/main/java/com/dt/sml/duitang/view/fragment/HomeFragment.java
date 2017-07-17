package com.dt.sml.duitang.view.fragment;

import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.dt.sml.duitang.R;
import com.dt.sml.duitang.utils.DensityUtil;
import com.dt.sml.duitang.view.PostNewActivity;

import java.util.ArrayList;

/**
 * Created by Smeiling on 2017/7/13.
 */

public class HomeFragment extends Fragment implements View.OnTouchListener, RadioGroup.OnCheckedChangeListener {

    public static final String TAG = HomeFragment.class.getSimpleName();

    private LinearLayout searchResultPanel;
    private EditText etSearch;
    private TextView tvCancel;

    private int screenWidth;
    private int screenHeight;

    private InputMethodManager manager;

    //content view
    private RadioGroup mRadioGroup;
    private RadioButton mRadioButton1;
    private RadioButton mRadioButton2;
    private RadioButton mRadioButton3;
    private RadioButton mRadioButton4;
    private RadioButton mRadioButton5;
    private RadioButton mRadioButton6;
    private RadioButton mRadioButton7;
    private RadioButton mRadioButton8;
    private ImageView mImageView;
    private float mCurrentCheckedRadioLeft;//当前被选中的RadioButton距离左侧的距离
    private HorizontalScrollView mHorizontalScrollView;//上面的水平滚动控件
    private ViewPager mViewPager;   //下方的可横向拖动的控件
    private ArrayList<View> mViews;//用来存放下方滚动的layout(layout_1,layout_2,layout_3)

    public HomeFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FloatingActionButton fab = (FloatingActionButton) getView().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.w(TAG, "FloadingActionButton clicked");
                startActivity(new Intent(getActivity(), PostNewActivity.class));
            }
        });
        getScreenParams();
        initView();
    }


    private void initView() {
        manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        searchResultPanel = (LinearLayout) getView().findViewById(R.id.ll_search_result);
        tvCancel = (TextView) getView().findViewById(R.id.tv_cancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (searchResultPanel.getVisibility() == View.VISIBLE) {
                    hideSearchPannel();
                }
            }
        });
        etSearch = (EditText) getView().findViewById(R.id.btn_anim);
        etSearch.getLayoutParams().width = screenWidth * 2 - 40;
        etSearch.setPadding((screenWidth * 2 - 40) / 2 - 36 * 4, 10, 10, 10);
        etSearch.setFocusable(false);
        etSearch.setOnTouchListener(this);


        iniController();
        iniListener();
        iniVariable();

        mRadioButton1.setChecked(true);

        mViewPager.setCurrentItem(1);
        mCurrentCheckedRadioLeft = getCurrentCheckedRadioLeft();

    }

    /**
     * 显示搜索结果面板
     */
    private void showSearchPannel() {
        Log.w(TAG, "show");
        searchResultPanel.setVisibility(View.VISIBLE);
        searchResultPanel.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.show_from_bottom));
        tvCancel.setVisibility(View.VISIBLE);
        tvCancel.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.show_from_right));
        performAnimate(true);
        etSearch.setFocusable(true);
        etSearch.setFocusableInTouchMode(true);
        etSearch.requestFocus();
    }

    /**
     * 隐藏搜索结果面板
     */
    private void hideSearchPannel() {
        Log.w(TAG, "hide");
        searchResultPanel.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.hide_to_bottom));
        searchResultPanel.setVisibility(View.GONE);
        tvCancel.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.hide_to_right));
        tvCancel.setVisibility(View.GONE);
        hideKeyboard();
        etSearch.setFocusable(false);
        performAnimate(false);

    }

    /**
     * 隐藏软键盘
     */
    private void hideKeyboard() {
        Log.w(TAG, "hideKeyboard1");
        if (getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            Log.w(TAG, "hideKeyboard2");
            if (getActivity().getCurrentFocus() != null) {
                Log.w(TAG, "hideKeyboard3");
                manager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    private static class ViewWrapper {
        private View mTarget;

        public ViewWrapper(View target) {
            mTarget = target;
        }

        public int getWidth() {
            return mTarget.getLayoutParams().width;
        }

        public void setWidth(int width) {
            //Log.w(TAG, "setWidth = " + width);
            mTarget.getLayoutParams().width = width;
            mTarget.requestLayout();
        }

        public int getPadding() {
            return mTarget.getPaddingLeft();
        }

        public void setPadding(int padding) {
            //Log.w(TAG, "setPadding = " + padding);
            mTarget.setPadding(padding, 10, 10, 10);
        }

    }

    /**
     * 控件属性渐变动画
     *
     * @param expand
     */
    private void performAnimate(boolean expand) {
        ViewWrapper wrapper = new ViewWrapper(etSearch);
        //Log.w(TAG, "screenWidth = " + screenWidth);
        if (expand) {
            ObjectAnimator.ofInt(wrapper, "padding", (screenWidth * 2 - 40) / 2 - 36 * 4, 10).setDuration(500).start();
            ObjectAnimator.ofInt(wrapper, "width", screenWidth * 2 - 40 - 80).setDuration(500).start();
        } else {
            ObjectAnimator.ofInt(wrapper, "padding", (screenWidth * 2 - 40) / 2 - 36 * 4).setDuration(500).start();
            ObjectAnimator.ofInt(wrapper, "width", screenWidth * 2 - 40).setDuration(500).start();
        }
    }

    /**
     * 获取屏幕信息
     */
    private void getScreenParams() {
        WindowManager wm = getActivity().getWindowManager();

        screenWidth = DensityUtil.px2dip(getActivity(), wm.getDefaultDisplay().getWidth());
        screenHeight = DensityUtil.px2dip(getActivity(), wm.getDefaultDisplay().getHeight());
        Log.w(TAG, "screenWidth = " + screenWidth);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.getId() == R.id.btn_anim && searchResultPanel.getVisibility() == View.GONE) {
            showSearchPannel();
        }
        return false;
    }


    private void iniVariable() {
        // TODO Auto-generated method stub
        mViews = new ArrayList<View>();
        mViews.add(getActivity().getLayoutInflater().inflate(R.layout.layout_0, null));
        mViews.add(getActivity().getLayoutInflater().inflate(R.layout.layout_1, null));
        mViews.add(getActivity().getLayoutInflater().inflate(R.layout.layout_0, null));
        mViews.add(getActivity().getLayoutInflater().inflate(R.layout.layout_1, null));
        mViews.add(getActivity().getLayoutInflater().inflate(R.layout.layout_0, null));
        mViews.add(getActivity().getLayoutInflater().inflate(R.layout.layout_1, null));
        mViews.add(getActivity().getLayoutInflater().inflate(R.layout.layout_0, null));
        mViews.add(getActivity().getLayoutInflater().inflate(R.layout.layout_1, null));
        mViews.add(getActivity().getLayoutInflater().inflate(R.layout.layout_0, null));
        mViews.add(getActivity().getLayoutInflater().inflate(R.layout.layout_1, null));
        mViewPager.setAdapter(new MyPagerAdapter());//设置ViewPager的适配器
    }

    /**
     * RadioGroup点击CheckedChanged监听
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        AnimationSet animationSet = new AnimationSet(true);
        TranslateAnimation translateAnimation;

        Log.i(TAG, "checkedid=" + checkedId);
        if (checkedId == R.id.btn1) {
            translateAnimation = new TranslateAnimation(mCurrentCheckedRadioLeft, getResources().getDimension(R.dimen.rrdo1), 0f, 0f);
            animationSet.addAnimation(translateAnimation);
            animationSet.setFillBefore(false);
            animationSet.setFillAfter(true);
            animationSet.setDuration(100);
            /*LayoutParams _LayoutParams1 = new LayoutParams(100, 4);
            _LayoutParams1.setMargins(0, 0, 0, 0);
            _LayoutParams1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);*/
            //mImageView.bringToFront();
            mImageView.startAnimation(animationSet);//开始上面蓝色横条图片的动画切换
            //mImageView.setLayoutParams(_LayoutParams1);
            mViewPager.setCurrentItem(1);//让下方ViewPager跟随上面的HorizontalScrollView切换
        } else if (checkedId == R.id.btn2) {
            translateAnimation = new TranslateAnimation(mCurrentCheckedRadioLeft, getResources().getDimension(R.dimen.rrdo2), 0f, 0f);

            animationSet.addAnimation(translateAnimation);
            animationSet.setFillBefore(false);
            animationSet.setFillAfter(true);
            animationSet.setDuration(100);

            //mImageView.bringToFront();
            mImageView.startAnimation(animationSet);

            mViewPager.setCurrentItem(2);
        } else if (checkedId == R.id.btn3) {
            translateAnimation = new TranslateAnimation(mCurrentCheckedRadioLeft, getResources().getDimension(R.dimen.rrdo3), 0f, 0f);

            animationSet.addAnimation(translateAnimation);
            animationSet.setFillBefore(false);
            animationSet.setFillAfter(true);
            animationSet.setDuration(100);

            //mImageView.bringToFront();
            mImageView.startAnimation(animationSet);

            mViewPager.setCurrentItem(3);
        } else if (checkedId == R.id.btn4) {
            translateAnimation = new TranslateAnimation(mCurrentCheckedRadioLeft, getResources().getDimension(R.dimen.rrdo4), 0f, 0f);

            animationSet.addAnimation(translateAnimation);
            animationSet.setFillBefore(false);
            animationSet.setFillAfter(true);
            animationSet.setDuration(100);

            //mImageView.bringToFront();
            mImageView.startAnimation(animationSet);
            mViewPager.setCurrentItem(4);
        } else if (checkedId == R.id.btn5) {
            translateAnimation = new TranslateAnimation(mCurrentCheckedRadioLeft, getResources().getDimension(R.dimen.rrdo5), 0f, 0f);

            animationSet.addAnimation(translateAnimation);
            animationSet.setFillBefore(false);
            animationSet.setFillAfter(true);
            animationSet.setDuration(100);

            //mImageView.bringToFront();
            mImageView.startAnimation(animationSet);

            mViewPager.setCurrentItem(5);
        } else if (checkedId == R.id.btn6) {
            translateAnimation = new TranslateAnimation(mCurrentCheckedRadioLeft, getResources().getDimension(R.dimen.rrdo6), 0f, 0f);

            animationSet.addAnimation(translateAnimation);
            animationSet.setFillBefore(false);
            animationSet.setFillAfter(true);
            animationSet.setDuration(100);

            //mImageView.bringToFront();
            mImageView.startAnimation(animationSet);

            mViewPager.setCurrentItem(6);
        } else if (checkedId == R.id.btn7) {
            translateAnimation = new TranslateAnimation(mCurrentCheckedRadioLeft, getResources().getDimension(R.dimen.rrdo7), 0f, 0f);

            animationSet.addAnimation(translateAnimation);
            animationSet.setFillBefore(false);
            animationSet.setFillAfter(true);
            animationSet.setDuration(100);

            //mImageView.bringToFront();
            mImageView.startAnimation(animationSet);

            mViewPager.setCurrentItem(7);
        } else if (checkedId == R.id.btn8) {
            translateAnimation = new TranslateAnimation(mCurrentCheckedRadioLeft, getResources().getDimension(R.dimen.rrdo8), 0f, 0f);

            animationSet.addAnimation(translateAnimation);
            animationSet.setFillBefore(false);
            animationSet.setFillAfter(true);
            animationSet.setDuration(100);

            //mImageView.bringToFront();
            mImageView.startAnimation(animationSet);

            mViewPager.setCurrentItem(8);
        }

        mCurrentCheckedRadioLeft = getCurrentCheckedRadioLeft();

        Log.i(TAG, "getCurrentCheckedRadioLeft=" + getCurrentCheckedRadioLeft());
        Log.i(TAG, "getDimension=" + getResources().getDimension(R.dimen.rrdo2));



        mHorizontalScrollView.smoothScrollTo((int) mCurrentCheckedRadioLeft - (int) getResources().getDimension(R.dimen.rrdo2), 0);
    }

    /**
     * 获得当前被选中的RadioButton距离左侧的距离
     */
    private float getCurrentCheckedRadioLeft() {
        // TODO Auto-generated method stub
        if (mRadioButton1.isChecked()) {
            //Log.i("zj", "currentCheckedRadioLeft="+getResources().getDimension(R.dimen.rrdo1));
            return getResources().getDimension(R.dimen.rrdo1);
        } else if (mRadioButton2.isChecked()) {
            //Log.i("zj", "currentCheckedRadioLeft="+getResources().getDimension(R.dimen.rrdo2));
            return getResources().getDimension(R.dimen.rrdo2);
        } else if (mRadioButton3.isChecked()) {
            //Log.i("zj", "currentCheckedRadioLeft="+getResources().getDimension(R.dimen.rrdo3));
            return getResources().getDimension(R.dimen.rrdo3);
        } else if (mRadioButton4.isChecked()) {
            //Log.i("zj", "currentCheckedRadioLeft="+getResources().getDimension(R.dimen.rrdo4));
            return getResources().getDimension(R.dimen.rrdo4);
        } else if (mRadioButton5.isChecked()) {
            //Log.i("zj", "currentCheckedRadioLeft="+getResources().getDimension(R.dimen.rrdo5));
            return getResources().getDimension(R.dimen.rrdo5);
        } else if (mRadioButton6.isChecked()) {
            //Log.i("zj", "currentCheckedRadioLeft="+getResources().getDimension(R.dimen.rrdo5));
            return getResources().getDimension(R.dimen.rrdo6);
        } else if (mRadioButton7.isChecked()) {
            //Log.i("zj", "currentCheckedRadioLeft="+getResources().getDimension(R.dimen.rrdo5));
            return getResources().getDimension(R.dimen.rrdo7);
        } else if (mRadioButton8.isChecked()) {
            //Log.i("zj", "currentCheckedRadioLeft="+getResources().getDimension(R.dimen.rrdo5));
            return getResources().getDimension(R.dimen.rrdo8);
        }
        return 0f;
    }

    private void iniListener() {
        // TODO Auto-generated method stub

        mRadioGroup.setOnCheckedChangeListener(this);


        mViewPager.setOnPageChangeListener(new MyPagerOnPageChangeListener());
    }

    private void iniController() {
        // TODO Auto-generated method stub
        mRadioGroup = (RadioGroup) getView().findViewById(R.id.radioGroup);
        mRadioButton1 = (RadioButton) getView().findViewById(R.id.btn1);
        mRadioButton2 = (RadioButton) getView().findViewById(R.id.btn2);
        mRadioButton3 = (RadioButton) getView().findViewById(R.id.btn3);
        mRadioButton4 = (RadioButton) getView().findViewById(R.id.btn4);
        mRadioButton5 = (RadioButton) getView().findViewById(R.id.btn5);
        mRadioButton6 = (RadioButton) getView().findViewById(R.id.btn6);
        mRadioButton7 = (RadioButton) getView().findViewById(R.id.btn7);
        mRadioButton8 = (RadioButton) getView().findViewById(R.id.btn8);
        mImageView = (ImageView) getView().findViewById(R.id.img1);

        mHorizontalScrollView = (HorizontalScrollView) getView().findViewById(R.id.horizontalScrollView);

        mViewPager = (ViewPager) getView().findViewById(R.id.pager);
    }


    /**
     * ViewPager的适配器
     *
     */
    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public void destroyItem(View v, int position, Object obj) {
            // TODO Auto-generated method stub
            ((ViewPager) v).removeView(mViews.get(position));
        }

        @Override
        public void finishUpdate(View arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mViews.size();
        }

        @Override
        public Object instantiateItem(View v, int position) {
            ((ViewPager) v).addView(mViews.get(position));
            return mViews.get(position);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0 == arg1;
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
            // TODO Auto-generated method stub

        }

        @Override
        public Parcelable saveState() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
            // TODO Auto-generated method stub

        }

    }

    /**
     * ViewPager的PageChangeListener(页面改变的监听器)
     *
     * @author zj
     *         2012-5-24 下午3:14:27
     */
    private class MyPagerOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub

        }

        /**
         * 滑动ViewPager的时候,让上方的HorizontalScrollView自动切换
         */
        @Override
        public void onPageSelected(int position) {
            // TODO Auto-generated method stub
            //Log.i("zj", "position="+position);

            if (position == 0) {
                mViewPager.setCurrentItem(1);
            } else if (position == 1) {
                mRadioButton1.performClick();
            } else if (position == 2) {
                mRadioButton2.performClick();
            } else if (position == 3) {
                mRadioButton3.performClick();
            } else if (position == 4) {
                mRadioButton4.performClick();
            } else if (position == 5) {
                mRadioButton5.performClick();
            } else if (position == 6) {
                mRadioButton6.performClick();
            } else if (position == 7) {
                mRadioButton7.performClick();
            } else if (position == 8) {
                mRadioButton8.performClick();
            } else if (position == 9) {
                mViewPager.setCurrentItem(8);
            }
        }

    }

}
