package com.example.poem.Helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class PoemDBHelper extends SQLiteOpenHelper {
    private static final String APPLICATION_ID="4699c6b92b022a0ee76eee38121356d5";
    private static final String CREATE_POEM_TABLE = "create table poem ("
            + "id integer primary key autoincrement, "
            + "name text, "
            + "date text, "
            + "author text, "
            + "content text,"
            + "favorite float,"
            + "summary text)";

    private static final String CREATE_POET_TABLE = "create table poet ("
            + "id integer primary key autoincrement, "
            + "name text, "
            + "date text, "
            + "summary text)";

    private Context context;

    public PoemDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                        int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_POEM_TABLE);
        db.execSQL(CREATE_POET_TABLE);
        Toast.makeText(this.context, "Create poem table", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists poem");
        db.execSQL("drop table if exists poet");
        onCreate(db);
    }
}
