package com.example.igra.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
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
    MediaPlayer mediaPlayer;
    AudioManager am;
    public int zagad;
    public int score;
    boolean run = true;
    Random rand;
    android.os.Handler handler;
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
                score = 0;
                game();
            }
        });

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (zagad == 1) {
                    score++;
                } else {
                    run = false;
                    text2.setText("Проиграл " + score);
                    newactiv();
                }
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (zagad == 2) {
                    score++;
                } else {
                    run = false;
                    text2.setText("Проиграл " + score);
                    newactiv();
                }
            }
        });

        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (zagad == 3) {
                    score++;
                } else {
                    run = false;
                    text2.setText("Проиграл " + score);
                    newactiv();
                }
            }
        });

        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newactiv();
            }
        });

        findViewById(R.id.button6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.pause();
                run = false;
            }
        });

        findViewById(R.id.button7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                run = true;
                game();
            }
        });
    }
    public void newactiv(){
        Intent intent = new Intent(MainActivity.this, AboutActivity.class);
        startActivity(intent);

    }
    public int rand(){
        zagad = rand.nextInt(3);
        return zagad;
    }
    public int rand2(){
       int a = (int) (5000-(5000-2000)*Math.random());
       return a;
    }

   public void game(){
        android.os.Handler.Callback hc = new android.os.Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                rand();
                if (zagad == 1) {
                    ImageLoader.getInstance().displayImage(getString(R.string.url_left), (ImageView) findViewById(R.id.imageView));

                } else {
                    if (zagad == 2) {
                        ImageLoader.getInstance().displayImage(getString(R.string.url_down), (ImageView) findViewById(R.id.imageView));
                    } else {
                        ImageLoader.getInstance().displayImage(getString(R.string.url_right), (ImageView) findViewById(R.id.imageView));
                    }
                }
                if(run){
                    handler.sendEmptyMessageDelayed(1, rand2());
                    return false;
                }
                return false;
            }
        };
        handler = new Handler(hc);
        handler.sendEmptyMessageDelayed(1, 2000);
       releaseMP();
       mediaPlayer = MediaPlayer.create(this, R.raw.explosion);
       mediaPlayer.start();
    }

    private void releaseMP() {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.release();
                mediaPlayer = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMP();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.score:
                newactiv();
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }
}


