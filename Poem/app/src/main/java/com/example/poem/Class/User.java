package com.example.poem.Class;


public class User {
    private String account;
    private String pwd;
    private String nickname;
    private String email;

    public User(String account, String pwd, String email) {
        this.account = account;
        this.pwd = pwd;
        this.nickname = "昵称";
        this.email = email;
    }

    public String getName() {
        return nickname;
    }

    public String getAccount() {
        return account;
    }

    public String getPwd() {
        return pwd;
    }

    public String getEmail() {
        return email;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.nickname = name;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

}
