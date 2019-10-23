package com.example.merna.e_commercemobileapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class shoppingcart extends AppCompatActivity {
    database db;
    ArrayAdapter<String> card_adapter;
    ArrayAdapter<String> price_adapter;
    ArrayAdapter<String> quantity_adapter;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppingcart);

        db = new database(this);
        final ListView list_cart = findViewById(R.id.list_cart);
        card_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        price_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        quantity_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        list_cart.setAdapter(card_adapter);
        Integer cid2 = Integer.parseInt(getIntent().getExtras().getString("CIDD"));

        Cursor cursor = db.get_all_orders(cid2);
        card_adapter.clear();
        price_adapter.clear();
        quantity_adapter.clear();
        while(!(cursor).isAfterLast())
        {
            card_adapter.add((cursor).getString(1));
            price_adapter.add((cursor).getString(2));
            quantity_adapter.add((cursor).getString(7));
            (cursor).moveToNext();
        }

        list_cart.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                name = ((TextView)view).getText().toString();
            }
        });

        Button button_total = findViewById(R.id.button_total_order);
        button_total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String [] items = new String[card_adapter.getCount()];
                String [] items_prices = new String[card_adapter.getCount()];
                String [] items_quantity = new String[card_adapter.getCount()];
                int total=0;
                for (int i=0;i<card_adapter.getCount();i++)
                {
                    items[i]=card_adapter.getItem(i);
                    items_prices[i]=price_adapter.getItem(i);
                    items_quantity[i]=quantity_adapter.getItem(i);
                    total += (Integer.parseInt((items_prices[i])) * Integer.parseInt((items_quantity[i])));
                }
                Toast.makeText(getApplicationContext(),"Total price = " + total,Toast.LENGTH_LONG).show();
            }
        });

        registerForContextMenu(list_cart);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.shoppingmenu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        String selected_order = ((TextView)info.targetView).getText().toString();
        Cursor nn = db.get_order_id_by_name(name);
        String orid = nn.getString(0);
        Integer oidd = Integer.parseInt(orid);
        switch (item.getItemId())
        {
            case R.id.menu_update:
                Intent i = new Intent(shoppingcart.this, updatequantity.class);
                i.putExtra("order_id", orid);
                startActivity(i);
                return true;
            case R.id.menu_delete:
                card_adapter.remove(selected_order);
                db.delete_order(oidd);
                return true;
        }
        return false;
    }
}
