package com.example.administrator.androidbodyguard.startanim;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.administrator.androidbodyguard.MainActivity;
import com.example.administrator.androidbodyguard.R;

public class ShadeActivity extends AppCompatActivity {
    private static final String IS_FIRST_RUN = "isFirstRun";
    private ImageView view;
    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏状态栏
        if (Build.VERSION.SDK_INT > 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_shade);
        view = (ImageView) findViewById(R.id.image_view);
        Glide.with(this).load(R.drawable.them_image).into(view);//添加gif动画

        animation = AnimationUtils.loadAnimation(this, R.anim.alpha_anim);
        view.setAnimation(animation);
        animation.setAnimationListener(new MyAnimationListener());
        animation.start();

    }

    @Override
    protected void onStart() {
        super.onStart();
      if(!animation.isInitialized())
            animation.start();


    }

    private class MyAnimationListener implements Animation.AnimationListener {
        @Override
        public void onAnimationStart(Animation animation) {
//            Toast.makeText(MainActivity.this,"动画开始",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAnimationEnd(Animation animation) {
//            Toast.makeText(MainActivity.this,"动画结束",Toast.LENGTH_SHORT).show();
            sharedPreferences();
            finish();

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        animation.cancel();
    }

    public void sharedPreferences() {
        SharedPreferences preferences = getSharedPreferences("into", Context.MODE_PRIVATE);
        boolean isFirstRun = preferences.getBoolean(IS_FIRST_RUN, false);
        if (isFirstRun) {
            startActivity(new Intent(ShadeActivity.this,MainActivity.class) );
            finish();//销毁当前Activity
        } else {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(IS_FIRST_RUN, true);
            editor.commit();
            startActivity(new Intent(ShadeActivity.this, LeadActivity.class));


        }
    }
}
