package com.example.poem.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import com.example.poem.Class.User;
import com.example.poem.DB.UserDB;
import com.example.poem.R;
import com.example.poem.Helper.MyConfig;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class RegisterActivity extends Activity {

    @ViewInject(R.id.main_title)
    private TextView tex_main_title;

    @ViewInject(R.id.account)
    private EditText et_account;

    @ViewInject(R.id.email)
    private EditText et_email;

    @ViewInject(R.id.password)
    private EditText et_password;

    @ViewInject(R.id.btn_register)
    private Button btn_register;


    private UserDB allUsers;
    private MyConfig myConfig;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Bmob.initialize(this, "4699c6b92b022a0ee76eee38121356d5");
        x.view().inject(this);
        allUsers = new UserDB(this);
        myConfig = new MyConfig(this);
        et_account.setText(getIntent().getStringExtra("account"));
        init();
    }

    private void init() {
        //设置字体
        tex_main_title.setTypeface(myConfig.getTf());
        et_account.setTypeface(myConfig.getTf());
        et_email.setTypeface(myConfig.getTf());
        et_password.setTypeface(myConfig.getTf());
        btn_register.setTypeface(myConfig.getTf());

    }

    private boolean add_user() {
        String account = et_account.getText().toString();
        String email = et_email.getText().toString();
        String pwd = et_password.getText().toString();
        try {
            if (account.length() < 1) {
                Toast.makeText(RegisterActivity.this, "请输入用户名！", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (email.length() < 1) {
                Toast.makeText(RegisterActivity.this, "请输入邮箱！", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (pwd.length() < 6) {
                Toast.makeText(RegisterActivity.this, "密码长度不少于6位！", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (allUsers.ifNameUsed(account)) {
                Toast.makeText(RegisterActivity.this, "用户名已被注册！", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (allUsers.ifEmailUsed(email)) {
                Toast.makeText(RegisterActivity.this, "邮箱已被注册！", Toast.LENGTH_SHORT).show();
                return false;
            }
            if(!allUsers.isEmail(email)){
                Toast.makeText(RegisterActivity.this, "邮箱格式错误！", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (allUsers.insert(new User(account, pwd, email))) {
                Intent mIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                mIntent.putExtra("account", account);
                mIntent.putExtra("pwd", pwd);
                BmobUser bmobUser=new BmobUser();
                bmobUser.setUsername(et_account.getText().toString());
                bmobUser.setPassword(et_password.getText().toString());
                bmobUser.setEmail(et_email.getText().toString());
                bmobUser.signUp(new SaveListener<BmobUser>() {
                    @Override
                    public void done(BmobUser s, BmobException e) {
                        if (e == null) {
                            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                            System.out.println(e);
                        }
                    }

                });
                startActivity(mIntent);
                return true;
            }
        } catch (Exception e) {
            Toast.makeText(RegisterActivity.this, "出现了添加错误！", Toast.LENGTH_SHORT).show();
        }
        return false;

    }

    @Event(R.id.btn_register)
    private void register(View view) {
        if (add_user()) {
            finish();
        } else {
            Toast.makeText(RegisterActivity.this, "出现了注册错误！", Toast.LENGTH_SHORT).show();
        }
    }

}
