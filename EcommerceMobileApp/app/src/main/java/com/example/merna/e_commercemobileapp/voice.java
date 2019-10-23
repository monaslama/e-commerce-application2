package com.example.merna.e_commercemobileapp;

import android.content.Intent;
import android.database.Cursor;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class voice extends AppCompatActivity {
    EditText voice_search;
    database ecommerce;
    ListView list_voice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice);

        ecommerce = new database(this);
        Button button_voice = findViewById(R.id.button_voice);
        voice_search = findViewById(R.id.search_voice);
        list_voice = findViewById(R.id.list_voice);
        button_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                startActivityForResult(i,200);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==200)
        {
            if(resultCode==this.RESULT_OK && data != null)
            {
                ArrayList<String> result=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String x=result.get(0);
                Toast.makeText(getApplicationContext(),x.toString(),Toast.LENGTH_LONG).show();

                voice_search.setText(result.get(0));

                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1);
                list_voice.setAdapter(adapter);
                Cursor cursor = ecommerce.searchByText(voice_search.getText().toString());
                adapter.clear();

                while(!((Cursor) cursor).isAfterLast())
                {
                    adapter.add(cursor.getString(1));
                    cursor.moveToNext();
                }
            }
        }
    }
}
