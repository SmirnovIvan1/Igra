package com.example.igra.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

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
        sharedPreferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        final TextView text2 = (TextView) findViewById(R.id.textView2);
        rand = new Random();
        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rand();
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
