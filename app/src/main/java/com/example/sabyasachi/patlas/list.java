package com.example.sabyasachi.patlas;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class list extends Activity {
    SqlLiteDbHelper dbHelper;
    Contact contacts ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        dbHelper = new SqlLiteDbHelper(this);
        dbHelper.openDataBase();
        contacts = new Contact();
        String[] places = dbHelper.temp_data();

        ListAdapter sabyaAdapter = new CustomAdapter(this,places);
        ListView ssl=(ListView) findViewById(R.id.ssl);
        ssl.setAdapter(sabyaAdapter);

        ssl.setOnItemClickListener(

                new AdapterView.OnItemClickListener(){

                   @Override
                   public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                       String place=String.valueOf(parent.getItemAtPosition(position));
                       SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                       SharedPreferences.Editor editor = pref.edit();
                       editor.putString("Place_list", place);
                       editor.commit();
                       //Toast.makeText(list.this,place,Toast.LENGTH_LONG).show();
                       Intent i = new Intent(list.this,wikimap.class);
                       startActivity(i);
                   }
               }
        );
    }

    @Override
    //male or female??
    public void onBackPressed() {
        int sex= -1;
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("Mode", 0);
        editor.commit();
        sex = pref.getInt("Sex",-1);
        if ( sex == 1 )
        {
            Intent i=new Intent(this,playthegame.class);
            startActivity(i);
        }
        if ( sex == 0)
        {
            Intent j=new Intent(this,playthegame2.class);
            startActivity(j);
        }

    }







    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);
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
