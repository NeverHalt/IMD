package com.example.poem.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.poem.Class.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserDB extends SQLiteOpenHelper {

    private static final String DB = "User.db";
    private SQLiteDatabase mydb;
    private String _TABLE = "Users";


    public UserDB(Context ctx) {
        super(ctx, DB, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        int version = db.getVersion();
        Log.i("db_version", version + "");
        try {

            db.execSQL("create table Users (" +
                    "id integer primary key autoincrement," +
                    "account text," +
                    "pwd text," +
                    "name text," +
                    "email text" +
                    ")");
        } catch (SQLException e) {
            Log.i("user_db", e.toString());
        }
    }


    public boolean insert(User user) {
        boolean result = false;
        mydb = this.getReadableDatabase();
        if (mydb.isOpen()) {
            Log.d("DB", "数据库已经打开！");
        }
        ContentValues tableLine = new ContentValues();
        tableLine.put("account", user.getAccount());
        tableLine.put("pwd", user.getPwd());
        tableLine.put("name", user.getName());
        tableLine.put("email", user.getEmail());
        try {
            mydb.insert(this._TABLE, null, tableLine);
            Log.i("Db_user comment", "插入成功！");
            result = true;
        } catch (SQLException e) {
            Log.d("User DB", e.getMessage());
            result = false;
        } finally {
            mydb.close();
            return result;
        }
    }

    public boolean ifNameUsed(String name) {
        mydb = getReadableDatabase();
        Cursor cursor = mydb.query(_TABLE, new String[]{"account"}, "account = ?",
                new String[]{name}, null, null, null);
        if (cursor.getCount() != 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean ifEmailUsed(String email) {
        mydb = getReadableDatabase();
        Cursor cursor = mydb.query(_TABLE, new String[]{"id"}, "email = ?",
                new String[]{email}, null, null, null);
        if (cursor.getCount() != 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean ifPwdEqual(String account, String pwd) {
        Log.i("Db User", "查询用户与密码是否匹配！");
        mydb = getReadableDatabase();
        Cursor cursor = mydb.query(_TABLE, new String[]{"pwd"}, "account = ?",
                new String[]{account}, null, null, null);
        if (cursor.getCount() != 0) {
            Log.i("Db User", cursor.getCount() + "");
            cursor.moveToFirst();
            String savedPwd = cursor.getString(cursor.getColumnIndex("pwd"));
            Log.i("saved pwd", savedPwd);
            if (pwd.equals(savedPwd)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
