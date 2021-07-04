package com.example.poem.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.poem.DB.PoemDBManager;
import com.example.poem.R;
import com.example.poem.DB.RegexManager;

public class DisplayActivity extends AppCompatActivity {
    private LinearLayout layout;
    private Button leftButton;
    private Button rightButton;
    private TextView centerTitle;
    private int id;
    private String name;
    private String date;
    private String author;
    private String content;
    private float favorite;

    private TextView nameDisplay;
    private TextView dateDisplay;
    private TextView authorDisplay;
    private TextView contentDisplay;
    private RatingBar ratingBar;

    private PoemDBManager dbManager;
    private AlertDialog.Builder dialog;

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        id = intent.getIntExtra("idKey", 1);
        Log.v("911", "Get ID: " + id);

        PoemDBManager dbManager = new PoemDBManager(this);
        Cursor cursor = dbManager.query("poem", null, "id = ?", new String[]{String.valueOf(id)}, null,
                null, null);

        if (cursor.moveToFirst()) {
            name = cursor.getString(cursor.getColumnIndex("name"));
            date = cursor.getString(cursor.getColumnIndex("date"));
            author = cursor.getString(cursor.getColumnIndex("author"));
            content = cursor.getString(cursor.getColumnIndex("content"));
            favorite = cursor.getFloat(cursor.getColumnIndex("favorite"));
            content = new RegexManager(content, "[，。？！；]").replaceAll("\n");
        }
        cursor.close();

        int imgID = (int) (int) (Math.random() * 5);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            /* 竖屏 */
            switch (imgID) {
                case 0:
                    layout.setBackground(getResources().getDrawable(R.drawable.img_0));
                    break;
                case 1:
                    layout.setBackground(getResources().getDrawable(R.drawable.img_1));
                    break;
                case 2:
                    layout.setBackground(getResources().getDrawable(R.drawable.img_2));
                    break;
                case 3:
                    layout.setBackground(getResources().getDrawable(R.drawable.img_3));
                    break;
                case 4:
                    layout.setBackground(getResources().getDrawable(R.drawable.img_4));
                    break;
                case 5:
                    layout.setBackground(getResources().getDrawable(R.drawable.img_5));
                    break;
            }
        } else {
            /* 横屏 */
            switch (imgID) {
                case 0:
                    layout.setBackground(getResources().getDrawable(R.drawable.img_h_0));
                    break;
                case 1:
                    layout.setBackground(getResources().getDrawable(R.drawable.img_h_1));
                    break;
                case 2:
                    layout.setBackground(getResources().getDrawable(R.drawable.img_h_2));
                    break;
                case 3:
                    layout.setBackground(getResources().getDrawable(R.drawable.img_h_3));
                    break;
                case 4:
                    layout.setBackground(getResources().getDrawable(R.drawable.img_h_4));
                    break;
                case 5:
                    layout.setBackground(getResources().getDrawable(R.drawable.img_h_5));
                    break;
            }
        }


        nameDisplay.setText(name);
        dateDisplay.setText(date);
        authorDisplay.setText(author);
        contentDisplay.setText(content);
        ratingBar.setRating(favorite);
        ratingBar.setOnRatingBarChangeListener(mChangeListener);
        leftButton.setOnClickListener(mOnClickListener);
        rightButton.setOnClickListener(mOnClickListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        /* Hide the default title_template. */
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        layout = findViewById(R.id.layout);
        leftButton = findViewById(R.id.left_button);
        rightButton = findViewById(R.id.right_button);
        centerTitle = findViewById(R.id.center_title);

        nameDisplay = findViewById(R.id.name_display);
        dateDisplay = findViewById(R.id.date_display);
        authorDisplay = findViewById(R.id.author_display);
        contentDisplay = findViewById(R.id.content_display);
        ratingBar = findViewById(R.id.rating_bar);

        contentDisplay.setMovementMethod(ScrollingMovementMethod.getInstance());
        dbManager = new PoemDBManager(this);
        centerTitle.setText(R.string.item);
        leftButton.setText(R.string.back);
        rightButton.setText(R.string.delete);

        dialog = new AlertDialog.Builder(DisplayActivity.this);
        dialog.setTitle(R.string.caution);
        dialog.setMessage(R.string.sure);
        dialog.setCancelable(false);
        dialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dbManager.delete("poem", "id = ?", new String[]{String.valueOf(id)});
                Toast.makeText(DisplayActivity.this, "Delete to update《" + name + "》", Toast.LENGTH_SHORT).show();

                finish();
            }
        });
        dialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

    }

    RatingBar.OnRatingBarChangeListener mChangeListener =
            new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    dbManager.update("poem", "favorite", rating, "id = ?",
                            new String[]{String.valueOf(id)});
                }
            };


    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.left_button:
                    Toast.makeText(v.getContext(), "Back", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case R.id.right_button:
                    dialog.show();
                    break;
            }
        }
    };
}
