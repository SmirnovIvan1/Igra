package com.example.igra.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import java.util.Random;


public class MainActivity extends ActionBarActivity {
    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_COUNTER = "score";
    SharedPreferences sharedPreferences;
    public int zagad;
    public int score;
    Random rand;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageLoaderConfiguration config = new
        ImageLoaderConfiguration.Builder(getApplicationContext()).memoryCache(new LruMemoryCache(32 * 1024 * 1024))
                .memoryCacheSize(32 * 1024 * 1024)
                .discCacheSize(100 * 1024 * 1024)
                .discCacheFileCount(1000).build();
        ImageLoader.getInstance().init(config);
        ImageLoader.getInstance().displayImage(getString(R.string.url_left), (ImageView)  findViewById(R.id.button));
        ImageLoader.getInstance().displayImage(getString(R.string.url_down), (ImageView)  findViewById(R.id.button2));
        ImageLoader.getInstance().displayImage(getString(R.string.url_right), (ImageView)  findViewById(R.id.button3));
        sharedPreferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        final TextView text2 = (TextView) findViewById(R.id.textView2);
        rand = new Random();
        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rand();
                if (zagad==1){
                    ImageLoader.getInstance().displayImage(getString(R.string.url_left), (ImageView)  findViewById(R.id.imageView));
                } else {
                    if (zagad==2){
                        ImageLoader.getInstance().displayImage(getString(R.string.url_down), (ImageView)  findViewById(R.id.imageView));
                    }else {
                        ImageLoader.getInstance().displayImage(getString(R.string.url_right), (ImageView)  findViewById(R.id.imageView));
                    }
                }
            }
        });
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (zagad == 1) {
                    score++;
                } else {
                    text2.setText("Проиграл " + score);
                }
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (zagad == 2) {
                    score++;
                } else {
                    text2.setText("Проиграл " + score);
                }
            }
        });
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (zagad == 3) {
                    score++;
                } else {
                    text2.setText("Проиграл " + score);
                }
            }
        });
        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });
    }
    public int rand(){
        zagad = rand.nextInt(3);
        return zagad;
    }
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(APP_PREFERENCES_COUNTER, score);
        editor.apply();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if (sharedPreferences.contains(APP_PREFERENCES_COUNTER)) {
            score = sharedPreferences.getInt(APP_PREFERENCES_COUNTER, 0);
        }
    }
}
