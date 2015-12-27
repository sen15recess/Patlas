package com.example.sabyasachi.patlas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class highscores extends Activity {
    SqlLiteDbHelper dbHelper;
    Contact contacts ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);
        dbHelper = new SqlLiteDbHelper(this);
        dbHelper.openDataBase();
        contacts = new Contact();
        String[] scores = dbHelper.get_user();
        String[] names = dbHelper.get_userscore();
        ListAdapter sabhyaAdapter = new CustomAdapter1(this,scores,names);
        ListView ssl=(ListView) findViewById(R.id.ssl1);
        ssl.setAdapter(sabhyaAdapter);



    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(highscores.this,newconhigh.class);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
        startActivity(intent);
        finish();
        System.exit(0);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_highscores, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
