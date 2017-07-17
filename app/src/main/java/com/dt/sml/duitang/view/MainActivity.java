package com.dt.sml.duitang.view;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.dt.sml.duitang.R;
import com.dt.sml.duitang.view.fragment.HomeFragment;

public class MainActivity extends Activity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private BottomNavigationBar naviBar;

    private HomeFragment homeFragment = new HomeFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startActivity(new Intent(this, LoadingViewActivity.class));
        initMenu();
        setDefaultFragment();
    }

    /**
     * 设置默认的
     */
    private void setDefaultFragment() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_layout, homeFragment);
        transaction.commit();
    }

    private void initMenu() {
        naviBar = (BottomNavigationBar) findViewById(R.id.navigation_bar);
        naviBar.setMode(BottomNavigationBar.MODE_FIXED);
        naviBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        naviBar.addItem(new BottomNavigationItem(R.drawable.home, R.string.label_home).setActiveColorResource(R.color.colorTheme))
                .addItem(new BottomNavigationItem(R.drawable.discover, R.string.label_discover).setActiveColorResource(R.color.colorTheme))
                .addItem(new BottomNavigationItem(R.drawable.shop, R.string.label_shop).setActiveColorResource(R.color.colorTheme))
                .addItem(new BottomNavigationItem(R.drawable.me, R.string.label_me).setActiveColorResource(R.color.colorTheme))
                .setFirstSelectedPosition(0)
                .initialise();
        naviBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {

            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
    }

}
