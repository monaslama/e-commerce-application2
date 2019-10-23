package com.example.merna.e_commercemobileapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CategoriesActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    database db = new database(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Button buton_shopping = findViewById(R.id.button_shopping);
        buton_shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent merna = new Intent(CategoriesActivity.this, shoppingcart.class);
                String cidd = getIntent().getExtras().getString("customerid");
                merna.putExtra("CIDD", cidd);
                startActivity(merna);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.categories, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.men_logOut) {
            Intent i = new Intent(CategoriesActivity.this,LoginActivity.class);
            i.putExtra("logout",true);
            startActivity(i);
            return true;
        }
        else if (id == R.id.men_text) {
            Intent i = new Intent(CategoriesActivity.this, text.class);
            startActivity(i);
            return true;
        }
        else if (id == R.id.Men_voice) {
            Intent i = new Intent(CategoriesActivity.this, voice.class);
            startActivity(i);
            return true;
        }
        else if (id == R.id.Men_camera) {
            Intent i = new Intent(CategoriesActivity.this, camera.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_men) {
            ListView lst = (ListView)findViewById(R.id.lst_categories);
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>( getApplicationContext(), android.R.layout.simple_list_item_1);
            adapter.clear();
            lst.setAdapter(adapter);
            final Cursor c = db.perfumeForMen();

            while (!c.isAfterLast())
            {
                adapter.add(c.getString(1));
                c.moveToNext();
            }

            lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent i = new Intent(CategoriesActivity.this, make_order.class);
                    String product_name = ((TextView)view).getText().toString();
                    i.putExtra("product_name", product_name);
                    String customer_id = getIntent().getExtras().getString("customerid");
                    i.putExtra("cuid", customer_id);
                    startActivity(i);
                }
            });

        }

        else if (id == R.id.nav_women) {
            ListView lst = (ListView)findViewById(R.id.lst_categories);
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>( getApplicationContext(), android.R.layout.simple_list_item_1);
            adapter.clear();
            lst.setAdapter(adapter);
            final Cursor c = db.perfumeForWomen();
            while (!c.isAfterLast())
            {
                adapter.add(c.getString(1));
                c.moveToNext();
            }

            lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent i = new Intent(CategoriesActivity.this, make_order.class);
                    String product_name = ((TextView)view).getText().toString();
                    i.putExtra("product_name", product_name);
                    String customer_id = getIntent().getExtras().getString("customerid");
                    i.putExtra("cuid", customer_id);
                    startActivity(i);
                }
            });
        }
        else if (id == R.id.nav_search) {
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
