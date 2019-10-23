package com.example.merna.e_commercemobileapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class camera extends AppCompatActivity {
    database ecommerce;
    public static EditText search_camera;
    ListView list_camera;
    Button cameraSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        ecommerce = new database(this);
        search_camera = findViewById(R.id.search_camera);
        list_camera = findViewById(R.id.list_camera);
        Button btnCameraSearch = findViewById(R.id.button_camera);
        btnCameraSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), cameraCode.class);
                startActivity(i);
            }
        });

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1);
        list_camera.setAdapter(adapter);
        cameraSearch = findViewById(R.id.btnSearchCamera);
        cameraSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = ecommerce.searchByText(search_camera.getText().toString());
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
