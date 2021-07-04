package com.example.poem.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.poem.Helper.PoemDBHelper;
import com.example.poem.Helper.ResourceHelper;

public class PoemDBManager{
    private Context context;
    private SQLiteDatabase db;
    private ContentValues values;

    public PoemDBManager(Context context) {
        this.context = context;
        PoemDBHelper dbHelper = new PoemDBHelper(context, "PoemStore.db", null, 2);
        db = dbHelper.getWritableDatabase();
        values = new ContentValues();
    }


    public void init(String table) {
        if (!query(table, null, null, null, null, null, null).moveToFirst()) {
            for (String[] item : ResourceHelper.get2DResArray(this.context, table + "_array")) {
                item[3] = new RegexManager(item[3], " ").replaceAll("");
                insert(table, item[0], item[1], item[2], item[3], 1);
            }
        }
    }

    public void insert(String table, String name, String date, String author, String content, float favorite) {
        values.put("name", name);
        values.put("date", date);
        values.put("author", author);
        values.put("content", content);
        values.put("favorite", favorite);
        db.insert(table, null, values);
        values.clear();
    }

    public void update(String table, String key, String newValue, String whereClause, String[] whereArgs) {
        values.put(key, newValue);
        db.update(table, values, whereClause, whereArgs);
        values.clear();
    }

    public void update(String table, String key, float newValue, String whereClause, String[] whereArgs) {
        values.put(key, newValue);
        db.update(table, values, whereClause, whereArgs);
        values.clear();
    }

    public void delete(String table, String whereClause, String[] whereArgs) {
        db.delete(table, whereClause, whereArgs);
    }

    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs,
                        String groupBy, String having, String orderBy) {
        Cursor cursor = db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
        return cursor;
    }
}
