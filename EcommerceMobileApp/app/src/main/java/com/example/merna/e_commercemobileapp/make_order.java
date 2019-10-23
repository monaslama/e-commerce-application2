package com.example.merna.e_commercemobileapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;

public class make_order extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_order);

        final database db = new database(this);
        final EditText productName = findViewById(R.id.text_order_name);
        final EditText productAddress = findViewById(R.id.text_address);
        final CalendarView calender = findViewById(R.id.calendarView2);
        final EditText customerID = findViewById(R.id.text_customer_id);
        final EditText quantity = findViewById(R.id.text_quantity);
        Button btn = findViewById(R.id.button_make_order);

        productName.setText(getIntent().getExtras().getString("product_name"));
        customerID.setText(getIntent().getExtras().getString("cuid"));

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = null;
                SimpleDateFormat x = new SimpleDateFormat("mm/dd/yyyy");
                if(calender != null)
                {
                    date = x.format(calender.getDate());
                }
                Integer cuid = Integer.parseInt(customerID.getText().toString());
                db.make_order(date, productAddress.getText().toString(), cuid);

                Cursor cursor1 = db.get_order_id(date, productAddress.getText().toString(), cuid);
                Cursor cursor2 = db.get_product_id(productName.getText().toString());

                Integer qu = Integer.parseInt(quantity.getText().toString());
                Integer orid = Integer.parseInt(cursor1.getString(0));
                Integer proid = Integer.parseInt(cursor2.getString(0));
                db.insert_in_order_details(orid, proid, qu);
                Toast.makeText(getApplicationContext(), "Successfully added", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
