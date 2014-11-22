package com.example.igra.app;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by ivan on 15.11.14.
 */
public class AboutActivity extends Activity{
    ArrayAdapter<String> adapter;
    SharedPreferences sharedPreferences;
    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_COUNTER = "score";
    public int score;
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        sharedPreferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        ListView listView = (ListView) findViewById(R.id.listMain);
        final EditText editText = (EditText) findViewById(R.id.editText);
        final ArrayList<String> items = loadSpecialBalloonsPreferences(sharedPreferences);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.remove(adapter.getItem(position));
                saveSpecialBalloonsPreferences(items, sharedPreferences);
                return false;
            }
        });

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.add(0,editText.getText().toString()+" "+score);
                adapter.notifyDataSetChanged();
                editText.setText(" ");
                saveSpecialBalloonsPreferences(items, sharedPreferences);
            }
        });
    }

    public void saveSpecialBalloonsPreferences(ArrayList<String> items, SharedPreferences sharedPreferences){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        StringBuilder str = new StringBuilder();
        for (int i=0; i<items.size(); i++) {
            str.append(items.get(i)).append(",");
        }
        editor.putString("list_of_scores", str.toString());
        editor.commit();
    }

    public ArrayList<String> loadSpecialBalloonsPreferences(SharedPreferences sharedPreferences) {
        ArrayList<String> sBalloons = new ArrayList<String>();
        String savedString = sharedPreferences
                .getString("list_of_scores", "");
        StringTokenizer st = new StringTokenizer(savedString, ",");
        int indice = st.countTokens();
        for (int i = 0; i<indice; i++) {
            try {
                sBalloons.add(st.nextToken());
            } catch (Exception e) {
            }
        }
        return sBalloons;
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
