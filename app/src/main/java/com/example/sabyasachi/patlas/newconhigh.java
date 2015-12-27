package com.example.sabyasachi.patlas;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import android.os.Handler;

public class newconhigh extends ActionBarActivity {

    boolean doubleBackToExitPressedOnce = false;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    static long backButtonCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newconhigh);

    }

    public void onClick4(View view) {
        int sex = -1;
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        sex = pref.getInt("Sex",-1);
        if ( sex == -1)
            Toast.makeText(this, "No challengers yet", Toast.LENGTH_SHORT).show();
        else {
            Intent i = new Intent(this, highscores.class);
            startActivity(i);
        }
    }

        public void onClick2(View view){
            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("Mode",1);
            editor.commit();
        Intent i=new Intent(this,eameha.class);
        startActivity(i);
    }

    public void onClick3(View view){
        int sex = -1,status;
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("Mode", 0);
        editor.commit();
        sex = pref.getInt("Sex",-1);
        status = pref.getInt("status",-1);
        System.out.println(pref.getInt("status", -1));
        if(sex == -1)
            Toast.makeText(this, "No challengers yet", Toast.LENGTH_SHORT).show();
        if (status == 0) {
            Intent i = new Intent(this, eameha.class);
            startActivity(i);
        }
        if ( sex == 1 && status !=0)
        {
            Intent i=new Intent(this,playthegame.class);
            startActivity(i);
        }
        if ( sex == 0 && status != 0)
        {
            Intent j=new Intent(this,playthegame2.class);
            startActivity(j);
        }

    }




    @Override
    public void onBackPressed()
    {
        if(backButtonCount >= 1)
        {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
            startActivity(intent);
            finish();
            System.exit(0);
        }
        else
        {
            Toast.makeText(this, "Press the back button once again to close the application.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_newconhigh, menu);
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
