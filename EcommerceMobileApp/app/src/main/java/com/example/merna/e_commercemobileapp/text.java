package com.example.merna.e_commercemobileapp;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class text extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        final database ecommerce = new database(this);
        final EditText search_text = findViewById(R.id.search_text);
        Button btnTextSearch = findViewById(R.id.button_text);
        ListView lstTextSearch = findViewById(R.id.list_text);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1);
        lstTextSearch.setAdapter(adapter);
        btnTextSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = ecommerce.searchByText(search_text.getText().toString());
                adapter.clear();

                while(!((Cursor) cursor).isAfterLast())
                {
                    adapter.add(cursor.getString(1));
                    cursor.moveToNext();
                }
            }
        });
    }
}
