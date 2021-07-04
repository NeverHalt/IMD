package com.example.poem;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.poem.activity.DisplayActivity;
import com.example.poem.DB.PoemDBManager;

public class MainActivity extends AppCompatActivity {
    private int id;
    private TextView sentenceView;
    private String[] array;
    private PoemDBManager dbManager;

    @Override
    protected void onResume() {
        Log.v("110", "MainActivity: onResume()");
        super.onResume();

        int IMG_MAX_NUM = 20;
        this.id = (int) (Math.random() * IMG_MAX_NUM);
        Log.v("119", "Initially id: " + String.valueOf(id));
        while (!dbManager.query("poem", null, "id = ?", new String[]{String.valueOf(this.id + 1)}
        , null, null, null).moveToFirst()) {
            this.id = (int) (Math.random() * IMG_MAX_NUM);
            Log.v("119", "Turn to: " + String.valueOf(id));
        }
        sentenceView.setText(array[id]);
        this.id++;

        int imgID = (int) (int) (Math.random() * 5);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            /* 竖屏 */
            switch (imgID) {
                case 0:
                    sentenceView.setBackground(getResources().getDrawable(R.drawable.img_0));
                    break;
                case 1:
                    sentenceView.setBackground(getResources().getDrawable(R.drawable.img_1));
                    break;
                case 2:
                    sentenceView.setBackground(getResources().getDrawable(R.drawable.img_2));
                    break;
                case 3:
                    sentenceView.setBackground(getResources().getDrawable(R.drawable.img_3));
                    break;
                case 4:
                    sentenceView.setBackground(getResources().getDrawable(R.drawable.img_4));
                    break;
                case 5:
                    sentenceView.setBackground(getResources().getDrawable(R.drawable.img_5));
                    break;
            }
        } else {
            /* 横屏 */
            switch (imgID) {
                case 0:
                    sentenceView.setBackground(getResources().getDrawable(R.drawable.img_h_0));
                    break;
                case 1:
                    sentenceView.setBackground(getResources().getDrawable(R.drawable.img_h_1));
                    break;
                case 2:
                    sentenceView.setBackground(getResources().getDrawable(R.drawable.img_h_2));
                    break;
                case 3:
                    sentenceView.setBackground(getResources().getDrawable(R.drawable.img_h_3));
                    break;
                case 4:
                    sentenceView.setBackground(getResources().getDrawable(R.drawable.img_h_4));
                    break;
                case 5:
                    sentenceView.setBackground(getResources().getDrawable(R.drawable.img_h_5));
                    break;
            }
        }

        sentenceView.setOnClickListener(mClickListener);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v("110", "MainActivity: onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /* Hide the default title_template. */
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        dbManager = new PoemDBManager(this);
        dbManager.init("poem");
        sentenceView = findViewById(R.id.sentence_view);
        Resources res = getResources();
        array = res.getStringArray(R.array.sentence);
    }


    private final View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.sentence_view) {
                Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                intent.putExtra("idKey", id);
                Log.v("911", "Put ID: " + id);
                startActivity(intent);
            }
        }
    };

}
