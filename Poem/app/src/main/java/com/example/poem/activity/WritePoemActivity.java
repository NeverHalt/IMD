package com.example.poem.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import com.example.poem.Class.CloudPoem;
import com.example.poem.DB.PoemDBManager;
import com.example.poem.R;
import com.example.poem.DB.RegexManager;

public class WritePoemActivity extends AppCompatActivity {
    private Button leftButton;
    private Button rightButton;
    private TextView centerTitle;
    private EditText editName;
    private EditText editDate;
    private EditText editAuthor;
    private EditText editContent;
    private Button saveButton;

    private PoemDBManager dbManager;
    private AlertDialog.Builder dialog;
    private boolean saved = false;
    private Intent intent;

    private int id = -1;
    private String name;
    private String date = "现代";
    private String author = "匿名";
    private String content;
    private String  objectId;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (saved) {
            intent = new Intent();
            intent.putExtra("Refresh", "refresh");
            setResult(RESULT_OK, intent);
            Log.v("911", "Need to refresh");
        }
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();

        editName.addTextChangedListener(mTextWatcher);
        editDate.addTextChangedListener(mTextWatcher);
        editAuthor.addTextChangedListener(mTextWatcher);
        editContent.addTextChangedListener(mTextWatcher);
        saveButton.setOnClickListener(mOnClickListener);
        leftButton.setOnClickListener(mOnClickListener);
        rightButton.setOnClickListener(mOnClickListener);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_poem);
        Bmob.initialize(this, "4699c6b92b022a0ee76eee38121356d5");
        /* Hide the default title_template. */
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        leftButton = findViewById(R.id.left_button);
        rightButton = findViewById(R.id.right_button);
        centerTitle = findViewById(R.id.center_title);

        editName = findViewById(R.id.edit_name);
        editDate = findViewById(R.id.edit_date);
        editAuthor = findViewById(R.id.edit_author);
        editContent = findViewById(R.id.edit_content);
        saveButton = findViewById(R.id.save_button);

        dbManager = new PoemDBManager(this);

        editName.requestFocus();
        saveButton.setText(R.string.save);
        centerTitle.setText(R.string.writing);
        leftButton.setText(R.string.back);
        rightButton.setText(R.string.delete);
        saveButton.setEnabled(false);
        rightButton.setEnabled(false);

        dialog = new AlertDialog.Builder(WritePoemActivity.this);
        dialog.setTitle(R.string.caution);
        dialog.setMessage(R.string.sure);
        dialog.setCancelable(false);
        dialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                intent=new Intent();
                dbManager.delete("poem", "id = ?", new String[]{String.valueOf(id)});
                editName.setText("");
                editDate.setText("");
                editAuthor.setText("");
                editContent.setText("");
                CloudPoem cloudPoem=new CloudPoem();
                cloudPoem.setObjectId(objectId);
                cloudPoem.delete(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Toast.makeText(WritePoemActivity.this, "Success to delete 《" + name + "》",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(WritePoemActivity.this, "Fail to delete 《" + name + "》",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                saveButton.setText(R.string.save);
                saveButton.setEnabled(false);
                saved = false;
                id = -1;
            }
        });
        dialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
    }

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            if (saved) {
                saveButton.setText(R.string.update);
                rightButton.setEnabled(true);
            }

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            saveButton.setEnabled(editName.getText().length() > 0 && editContent.getText().length() > 0);
            if (saved) {
                saveButton.setText(R.string.update);
                rightButton.setEnabled(true);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            saveButton.setEnabled(editName.getText().length() > 0 && editContent.getText().length() > 0);
            if (saved) {
                saveButton.setText(R.string.update);
                rightButton.setEnabled(true);
            }
        }
    };

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.save_button:
                    name = editName.getText().toString();
                    date = editDate.getText().length() > 0 ? editDate.getText().toString() : date;
                    author = editAuthor.getText().length() > 0 ? editAuthor.getText().toString()
                            : author;
                    content = editContent.getText().toString();
                    content = new RegexManager(content, "[\t\n]").replaceAll("");
                    if (!saved) {
                        saved = true;
                        saveButton.setEnabled(false);
                        rightButton.setEnabled(true);
                        dbManager.insert("poem", name, date, author, content, 1);
                        Cursor cursor = dbManager.query("poem", null, null, null, null, null, "id" +
                                " desc");
                        if (cursor.moveToFirst()) {
                            id = cursor.getInt(cursor.getColumnIndex("id"));
                        }
                        cursor.close();
                        final CloudPoem cloudPoem=new CloudPoem();
                        cloudPoem.setName(name);
                        cloudPoem.setDate(date);
                        cloudPoem.setAuthor(author);
                        cloudPoem.setContent(content);
                        cloudPoem.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e == null) {
                                    Toast.makeText(WritePoemActivity.this, "Success to save 《" + name + "》", Toast.LENGTH_SHORT).show();
                                    objectId=cloudPoem.getObjectId();
                                } else {
                                    Toast.makeText(WritePoemActivity.this,"Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        final CloudPoem cloudPoem=new CloudPoem();
                        cloudPoem.setName(name);
                        cloudPoem.setDate(date);
                        cloudPoem.setAuthor(author);
                        cloudPoem.setContent(content);
                        cloudPoem.update(cloudPoem.getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    Toast.makeText(WritePoemActivity.this, "Success to update《" + name + "》",
                                            Toast.LENGTH_SHORT).show();
                                    objectId=cloudPoem.getObjectId();
                                }
                            }
                        });
                        dbManager.update("poem", "name", name, "id = ?",
                                new String[]{String.valueOf(id)});
                        dbManager.update("poem", "date", date, "id = ?",
                                new String[]{String.valueOf(id)});
                        dbManager.update("poem", "author", author, "id = ?",
                                new String[]{String.valueOf(id)});
                        dbManager.update("poem", "content", content, "id = ?",
                                new String[]{String.valueOf(id)});

                    }

                    break;
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
