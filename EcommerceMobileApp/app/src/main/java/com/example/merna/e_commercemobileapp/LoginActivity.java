package com.example.merna.e_commercemobileapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin,btnSignup;
    private EditText etEmail,etPassword;
    database ecommerce;
    CheckBox remembermr;
    String email;
    String pass;
    Integer id;

    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ecommerce = new database(this);
        btnLogin = findViewById(R.id.login);
        btnSignup = findViewById(R.id.signup);
        remembermr = findViewById(R.id.rememberMe);

        etEmail=(EditText) findViewById(R.id.et_email);
        etPassword=(EditText) findViewById(R.id.et_pass);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toSignup = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(toSignup);
            }
        });

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        saveLogin = loginPreferences.getBoolean("saveLogin", false);

        if (saveLogin == true) {
            etEmail.setText(loginPreferences.getString("email", ""));
            etPassword.setText(loginPreferences.getString("pass", ""));
            remembermr.setChecked(true);
        }

        Boolean logout = false;
        try {
            logout = getIntent().getExtras().getBoolean("logout");
        }
        catch (Exception e) {}

        if(ecommerce.login(email, pass) && logout ==false )
        {
            Toast.makeText(getApplicationContext(), "WELCOME - " + email, Toast.LENGTH_SHORT).show();
            Intent i = new Intent(LoginActivity.this, CategoriesActivity.class);
            id = ecommerce.customer_id(email, pass);
            i.putExtra("customerId", id.toString());
            startActivity(i);
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = etEmail.getText().toString();
                pass = etPassword.getText().toString();

                if(ecommerce.login(email, pass))
                {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(etEmail.getWindowToken(), 0);

                    if (remembermr.isChecked()) {
                        loginPrefsEditor.putBoolean("saveLogin", true);
                        loginPrefsEditor.putString("email", email);
                        loginPrefsEditor.putString("pass", pass);
                        loginPrefsEditor.commit();
                    } else {
                        loginPrefsEditor.clear();
                        loginPrefsEditor.commit();
                    }

                    //doSomethingElse();
                    Toast.makeText(getApplicationContext(), "welcome" , Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginActivity.this, CategoriesActivity.class);
                    id = ecommerce.customer_id(email, pass);
                    i.putExtra("customerid", id.toString());
                    startActivity(i);
                }

                else
                {
                    Toast.makeText(getApplicationContext(), "Username Or Password Invalid!", Toast.LENGTH_LONG).show();
                }
            }
        });

        TextView forget = findViewById(R.id.forget_password);
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,forgetpassword.class);
                startActivity(i);
            }
        });
    }
}
