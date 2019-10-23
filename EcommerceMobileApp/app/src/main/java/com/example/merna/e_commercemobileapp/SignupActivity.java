package com.example.merna.e_commercemobileapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;

public class SignupActivity extends AppCompatActivity {
    Button signup;
    EditText Name;
    EditText email;
    EditText Pass;
    EditText Jop;
    CheckBox check;
    RadioButton Male;
    RadioButton Female;
    String gender = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Name=findViewById(R.id.name);
        email=findViewById(R.id.et_email);
        Pass=findViewById(R.id.password);
        Jop=findViewById(R.id.job);

        check=findViewById(R.id.checkBox2);
        Male=findViewById(R.id.rdMale);
        Female=findViewById(R.id.rdFemale);

        if(Male.isChecked())
            gender = "Male";

        else if(Female.isChecked())
            gender = "Female";


        final CalendarView calender = findViewById(R.id.calendarView);

        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    // show password
                    Pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    // hide password
                    Pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });

        signup = findViewById(R.id.signup);
        final database db = new database(this);
        //final user user = new user(this);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Name.getText().toString().equals("") &&
                        email.getText().toString().equals("")&&
                        Pass.getText().toString().equals("") &&
                        Jop.getText().toString().equals("")){
                    Toast.makeText(SignupActivity.this, "empty fileds not allowed", Toast.LENGTH_SHORT).show();
                }
                else{
                    String date = null;
                    SimpleDateFormat x = new SimpleDateFormat("mm/dd/yyyy");
                    if(calender != null)
                    {
                        date = x.format(calender.getDate());
                    }

                    db.signup(Name.getText().toString(),email.getText().toString(),
                            Pass.getText().toString(),gender, date, Jop.getText().toString());
                    //Toast.makeText(SignupActivity.this, "done", Toast.LENGTH_SHORT).show();
                    //user.setLogged(true);
                    Intent toLogin = new Intent(SignupActivity.this,LoginActivity.class);
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                    startActivity(toLogin);
                    finish();
                }
            }
        });



    }


}



