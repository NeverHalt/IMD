package com.example.poem.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.poem.R;

public class MainActivityTitle extends LinearLayout {
    private Button leftButton;
    private Button rightButton;
    private TextView centerTitle;

    public MainActivityTitle(final Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.title_template, this);

        leftButton = findViewById(R.id.left_button);
        rightButton = findViewById(R.id.right_button);
        centerTitle = findViewById(R.id.center_title);

        centerTitle.setText(R.string.app_name);
        leftButton.setText(R.string.back);
        rightButton.setText(R.string.directory);

        leftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Back", Toast.LENGTH_SHORT).show();

                ((Activity) getContext()).finish();
            }
        });

        rightButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(context, SelectActivity.class);
                ActivityController.getActivity(context).startActivity(mIntent);
            }
        });

    }
}
