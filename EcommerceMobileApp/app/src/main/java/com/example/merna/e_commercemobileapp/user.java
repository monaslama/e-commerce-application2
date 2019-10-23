package com.example.merna.e_commercemobileapp;

import android.content.Context;
import android.content.SharedPreferences;

public class user  {

    String sp_name = "user";

    //for user data
    private SharedPreferences userData;
    private SharedPreferences.Editor editor;

    private Context context;

    private Boolean isLogged;

    /*****************************/
    /***** User Constructor ******/
    /*****************************/
    public user(Context context) {
        this.context = context;

        initSharedPref();
    }

    /********************************************/
    /***** Initialize the SharedPreference ******/
    /*******************************************/
    private void initSharedPref() {
        userData = context.getSharedPreferences(sp_name,Context.MODE_PRIVATE);
        editor = userData.edit();
    }

    public Boolean getLogged() {
        return userData.getBoolean("islogged",false);
    }

    public void setLogged(Boolean logged) {
        isLogged = logged;
        editor.putBoolean("islogged",isLogged);
        editor.apply();
    }
}
