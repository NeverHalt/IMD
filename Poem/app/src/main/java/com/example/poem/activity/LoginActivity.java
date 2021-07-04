package com.example.poem.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.*;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import com.example.poem.DB.UserDB;
import com.example.poem.Helper.MyConfig;
import com.example.poem.MainActivity;
import com.example.poem.R;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class LoginActivity extends Activity {

    @ViewInject(R.id.eye_check)
    private ImageView eye_check;
    @ViewInject(R.id.account)
    private EditText account;
    @ViewInject(R.id.password)
    private EditText password;
    @ViewInject(R.id.register)
    private TextView register;
    @ViewInject(R.id.forget_pass)
    private TextView forget_pass;
    @ViewInject(R.id.btn_register)
    private Button btn_login;

    @ViewInject(R.id.cb_remember_pass)
    private CheckBox cb_save;

    private int eyeChecked = 0;

    private UserDB user_db;
    private MyConfig myConfig;
    private boolean if_new = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        x.view().inject(this);
        Bmob.initialize(this, "4699c6b92b022a0ee76eee38121356d5");
        user_db = new UserDB(this);
        myConfig = new MyConfig(this);

        if (getIntent().getStringExtra("account") != null) {
            if_new = true;
            account.setText(getIntent().getStringExtra("account"));
            password.setText(getIntent().getStringExtra("pwd"));
        }
        init();

    }

    private void init() {
        btnListener myListener = new btnListener();
        eye_check.setOnClickListener(myListener);
        register.setOnClickListener(myListener);
        forget_pass.setOnClickListener(myListener);
        btn_login.setOnClickListener(myListener);

        register.setTypeface(myConfig.getTf());
        btn_login.setTypeface(myConfig.getTf());
        account.setTypeface(myConfig.getTf());
        password.setTypeface(myConfig.getTf());
        forget_pass.setTypeface(myConfig.getTf());
        cb_save.setTypeface(myConfig.getTf());


        SharedPreferences sp = getSharedPreferences("user", Context.MODE_PRIVATE);
        String s_account = sp.getString("account", "");
        String s_pwd = sp.getString("pwd", "");
        if (s_account.length() > 0) {
            cb_save.setChecked(true);
        }
        if (!if_new) {
            account.setText(s_account);
            password.setText(s_pwd);
        }

    }


    private class btnListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_register: {  //登录按钮
                    Intent mIntent = new Intent(v.getContext(), MainActivity.class);
                    String s_account = account.getText().toString();
                    String s_password = password.getText().toString();
                    SharedPreferences.Editor sp = getSharedPreferences("user", Context.MODE_PRIVATE).edit();

                    if (!user_db.ifNameUsed(s_account)) {
                        makeToast("未注册的用户！去注册用户！");
                        Intent newIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                        newIntent.putExtra("account", s_account);
                        startActivity(newIntent);

                    }

                    if (s_account.length() < 1 || s_password.length() < 1) {
                        Toast.makeText(LoginActivity.this, "请输入账号和密码！", Toast.LENGTH_LONG).show();

                    } else {
                        if (user_db.ifPwdEqual(s_account, s_password)) {
                            if (cb_save.isChecked()) {
                                if_new = false;
                                sp.putString("account", s_account);
                                sp.putString("pwd", s_password);

                            } else {
                                sp.putString("account", "");
                                sp.putString("pwd", "");
                            }
                            BmobUser bmobUser=new BmobUser();
                            bmobUser.setUsername(account.getText().toString());
                            bmobUser.setPassword(password.getText().toString());
                            bmobUser.login(new SaveListener<BmobUser>() {
                                @Override
                                public void done(BmobUser bmobUser, BmobException e) {
                                    if (e == null) {
                                        Toast.makeText(LoginActivity.this, "登录成功！\nWelcome !", Toast.LENGTH_LONG).show();

                                    } else {
                                        Toast.makeText(LoginActivity.this, "密码或用户名错误！", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                            sp.commit();
                            startActivity(mIntent);
                            finish();
                        }
                    }
                }
                break;
                case R.id.forget_pass:  //忘记密码
                    makeToast("忘记密码！");
                    break;
                case R.id.register:  //注册用户
                    makeToast("注册用户！");
                    startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                    finish();
                    break;
                case R.id.eye_check: {  //显示密码
                    if (eyeChecked == 1) {//
                        eyeChecked = 0;
                        password.setTransformationMethod(new PasswordTransformationMethod());
                    } else { //显示密码
                        eyeChecked = 1;
                        password.setTransformationMethod(null);
                    }
                }
                break;
                default:
                    break;
            }
        }
    }

    public void makeToast(String flag) {
        Context context = getApplicationContext();
        CharSequence text = flag;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
