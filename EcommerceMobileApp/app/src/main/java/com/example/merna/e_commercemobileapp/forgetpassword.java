package com.example.merna.e_commercemobileapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class forgetpassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);

        final database db = new database(this);
        final EditText mail = findViewById(R.id.text_mail);
        final EditText job = findViewById(R.id.text_job);
        final EditText new_pass = findViewById(R.id.text_pass);
        final Button change = findViewById(R.id.btn_change);

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maill = mail.getText().toString();
                String jobb = job.getText().toString();
                Boolean your_answer = db.answer(maill, jobb);

                if(your_answer == true)
                {
                    Intent i = new Intent(forgetpassword.this, LoginActivity.class);
                    String forget_pass = new_pass.getText().toString();
                    db.passwordRecovery(maill, forget_pass);
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                    startActivity(i);
                }

                else
                {
                    Toast.makeText(getApplicationContext(), "job not valid", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
