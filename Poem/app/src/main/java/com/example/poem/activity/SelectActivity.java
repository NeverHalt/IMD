package com.example.poem.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import com.example.poem.Adapter.PoemAdapter;
import com.example.poem.Class.CloudPoem;
import com.example.poem.Class.Poem;
import com.example.poem.DB.PoemDBManager;
import com.example.poem.R;
import com.example.poem.DB.RegexManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class SelectActivity extends AppCompatActivity {
    private List<Poem> poemList = new ArrayList<>();
    private Cursor cursor;
    private PoemDBManager dbManager;

    private int lastPosition = 0;
    private int lastOffset = 0;
    private final int WRITE_POEM_ACTIVITY = 911;

    private int id;
    private String name;
    private String date;
    private String author;
    private String content;
    private float favorite;

    private FloatingActionButton fab;
    private RelativeLayout relativeLayout;

    @Override
    protected void onResume() {
        Log.v("110", "SelectActivity: onResume()");
        super.onResume();


        RecyclerView recyclerView = findViewById(R.id.poem_recycler_view);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        PoemAdapter adapter = new PoemAdapter(this, poemList);
        recyclerView.setAdapter(adapter);

        // Go back last position of RecyclerView.
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                View topView = layoutManager.getChildAt(0);
                // Get position when topView != null
                if (topView != null) {
                    lastOffset = topView.getTop();
                    lastPosition = layoutManager.getPosition(topView);
                    Log.v("110", "last off set: " + lastOffset);
                    Log.v("110", "last position: " + lastPosition);
                }
            }
        });

        try {
            layoutManager.scrollToPositionWithOffset(lastPosition,
                    lastOffset);
        } catch (Exception e) {
            e.getStackTrace();
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectActivity.this, WritePoemActivity.class);
                startActivityForResult(intent, WRITE_POEM_ACTIVITY);
            }
        });

        /* Change position of FAB by changing RelativeLayout */
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            /* 竖屏 */
        } else {
            /* 横屏 */
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) relativeLayout.getLayoutParams();
            lp.rightMargin = -400;
            relativeLayout.setLayoutParams(lp);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v("110", "SelectActivity: onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        /* Hide the default title_template. */
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        fab = findViewById(R.id.fab);
        relativeLayout = findViewById(R.id.relative_layout);
        dbManager = new PoemDBManager(this);
        cursor = dbManager.query("poem", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int position = 1;
            do {
                id = cursor.getInt(cursor.getColumnIndex("id"));
                name = cursor.getString(cursor.getColumnIndex("name"));
                date = cursor.getString(cursor.getColumnIndex("date"));
                author = cursor.getString(cursor.getColumnIndex("author"));
                content = cursor.getString(cursor.getColumnIndex("content"));
                favorite = cursor.getFloat(cursor.getColumnIndex("favorite"));
                content = new RegexManager(content, "[\n\t ]").replaceAll("");
                poemList.add(new Poem(id, name, date, author, content, favorite, position));
                position++;
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

}
