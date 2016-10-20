package com.example.administrator.myapplication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.BoringLayout;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationSet;

public class MainActivity extends AppCompatActivity {
    private View im_day;
    private View im_day_bg;
    private View im_night;
    private View im_night_bg;
    private View image_btn;
    private boolean isNightMode = false;
    private float screentHeight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WindowManager manage = (WindowManager) getSystemService(this.WINDOW_SERVICE);
        DisplayMetrics metris = new DisplayMetrics();
        manage.getDefaultDisplay().getMetrics(metris);
        screentHeight = metris.heightPixels;
        initView();
    }

    private void initView() {
        im_day = findViewById(R.id.im_day);
        im_day_bg = findViewById(R.id.im_day_bg);
        im_night = findViewById(R.id.im_night);
        im_night_bg = findViewById(R.id.im_night_bg);
        image_btn = findViewById(R.id.image_btn);
        image_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isNightMode){
                    startNightAnim();
                 }else{
                    startDayAnim();
                }
                isNightMode = !isNightMode;
            }
        });
    }
    private void startNightAnim(){
        im_night.setVisibility(View.VISIBLE);
        im_night_bg.setVisibility(View.VISIBLE);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(im_night,"alpha",0f,1f);
        ObjectAnimator fall = ObjectAnimator.ofFloat(im_night,"y",screentHeight/2,screentHeight/2-100);
//        ObjectAnimator scaleX = ObjectAnimator.ofFloat(im_day,"scaleX",1f,2*(screentHeight/im_day_bg.getMeasuredHeight()));
//        ObjectAnimator scaleY = ObjectAnimator.ofFloat(im_day,"scaleY",1f,2*(screentHeight/im_day_bg.getMeasuredHeight()));
        PropertyValuesHolder holder1 = PropertyValuesHolder.ofFloat("scaleX",1f,2*(screentHeight/im_night_bg.getMeasuredHeight()));
        PropertyValuesHolder holder2 = PropertyValuesHolder.ofFloat("scaleY",1f,2*(screentHeight/im_night_bg.getMeasuredHeight()));
        ObjectAnimator scale = ObjectAnimator.ofPropertyValuesHolder(im_night,holder1,holder2);
        AnimatorSet set = new AnimatorSet();
        set.play(fall).with(scale);
        set.play(alpha).before(fall).after(8000);
        set.setDuration(500);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                endAnim(im_night_bg,im_night,true);
                super.onAnimationEnd(animation);
            }
        });
        set.start();
    }
    private void startDayAnim(){
        im_day.setVisibility(View.VISIBLE);
        im_day_bg.setVisibility(View.VISIBLE);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(im_day,"alpha",0f,1f);
        ObjectAnimator up = ObjectAnimator.ofFloat(im_day,"y",screentHeight/2-100,screentHeight/2);
//        ObjectAnimator scaleX = ObjectAnimator.ofFloat(im_day,"scaleX",1f,2*(screentHeight/im_day_bg.getMeasuredHeight()));
//        ObjectAnimator scaleY = ObjectAnimator.ofFloat(im_day,"scaleY",1f,2*(screentHeight/im_day_bg.getMeasuredHeight()));
        PropertyValuesHolder holder1 = PropertyValuesHolder.ofFloat("scaleX",1f,2*(screentHeight/im_day_bg.getMeasuredHeight()));
        PropertyValuesHolder holder2 = PropertyValuesHolder.ofFloat("scaleY",1f,2*(screentHeight/im_day_bg.getMeasuredHeight()));
        ObjectAnimator scale = ObjectAnimator.ofPropertyValuesHolder(im_day,holder1,holder2);
        AnimatorSet set = new AnimatorSet();
        set.play(up).with(scale);
        set.play(alpha).before(up).after(8000);
        set.setDuration(500);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                endAnim(im_day_bg,im_day,false);
                super.onAnimationEnd(animation);
            }
        });
        set.start();
    }
    private void endAnim(View backgroud,View view,final boolean isNightAnim){
        ObjectAnimator alpha1 = ObjectAnimator.ofFloat(backgroud,"alpha",0f,1f);
        ObjectAnimator alpha2 = ObjectAnimator.ofFloat(view,"alpha",0f,1f);
        AnimatorSet set =new  AnimatorSet();
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if(!isNightAnim){
                    im_day_bg.setVisibility(View.GONE);
                    im_day.setVisibility(View.GONE);
                }else{
                    im_night_bg.setVisibility(View.GONE);
                    im_night.setVisibility(View.GONE);
                }
                super.onAnimationEnd(animation);
            }
        });
        set.play(alpha1);
        set.play(alpha2).after(300);
        set.setDuration(3000);
        set.start();
    }

}
