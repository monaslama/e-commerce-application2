package com.example.merna.e_commercemobileapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class updatequantity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatequantity);

        final database ecommerce = new database(this);
        final String order_id = getIntent().getExtras().getString("order_id");
        Cursor cursor = ecommerce.get_quantity(Integer.parseInt(order_id));

        final EditText update = findViewById(R.id.new_quantity);
        update.setText(cursor.getString(0));
        Button btnUpdateQuantity = findViewById(R.id.button_new_quantity);
        btnUpdateQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String new_quantity = update.getText().toString();
                Integer new_quan = Integer.parseInt(new_quantity);
                Integer order = Integer.parseInt(order_id);
                ecommerce.update_quantity(new_quan, order);
                Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
