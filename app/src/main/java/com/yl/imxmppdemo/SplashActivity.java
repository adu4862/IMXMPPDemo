package com.yl.imxmppdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends Activity {


    @BindView(R.id.activity_main)
    RelativeLayout activityMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.3f, 1.0f);
        alphaAnimation.setDuration(2000);
        activityMain.startAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(SplashActivity.
                        this,LoginActivity.class));
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
}
