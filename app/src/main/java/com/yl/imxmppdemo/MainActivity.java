package com.yl.imxmppdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.yl.imxmppdemo.fragment.BaseFragment;
import com.yl.imxmppdemo.fragment.SelfInfoFragment;
import com.yl.imxmppdemo.fragment.TalkFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/27 0027.
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener {
    private static final String TAG="MainActivity";


    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.tv_talk)
    TextView tvTalk;
    @BindView(R.id.tv_selfinfo)
    TextView tvSelfinfo;
    private ArrayList<BaseFragment> baseFragments;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        fm = getSupportFragmentManager();
        baseFragments = new ArrayList<>();
        baseFragments.add(new TalkFragment());
        baseFragments.add(new SelfInfoFragment());
        tvTalk.setOnClickListener(this);
        tvSelfinfo.setOnClickListener(this);
        //tvSelfinfo.setEnabled(false);
        tvTalk.setEnabled(false);

        viewpager.setAdapter(new FragmentPagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                return baseFragments.get(position);
            }

            @Override
            public int getCount() {
                return baseFragments.size();
            }
        });
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        tvTalk.setEnabled(false);
                        tvSelfinfo.setEnabled(true);
                        break;
                    case 1:

                        tvTalk.setEnabled(true);
                        tvSelfinfo.setEnabled(false);
                        break;
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }


            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_talk:
                Log.d(TAG, "onClick: 监听到点击事件R.id.tv_talk");
               viewpager.setCurrentItem(0,true);
                break;
            case R.id.tv_selfinfo:
                Log.d(TAG, "onClick: 监听到点击事件tv_selfinfo");
                viewpager.setCurrentItem(1,true);
                break;
        }
    }
}
