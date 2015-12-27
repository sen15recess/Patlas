package com.example.sabyasachi.patlas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class playthegame2 extends ActionBarActivity {

    SqlLiteDbHelper dbHelper;
    private Cursor cursor;
    SQLiteDatabase relief;
    Contact contacts ;
    static int foo = 0,tr = 0;
    static CountDownTimer tk;
    static String nm1="",nm2="",nm3="",nm4="";
    static String sendsql,sendch="";
    int l,l1,level;
    TextView tv2_text, tv4_text, tv6_text, high_score;
    EditText et1_input;
    char chlast;
    static int time=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new SqlLiteDbHelper(this);
        dbHelper.openDataBase();
        contacts = new Contact();
        setContentView(R.layout.activity_playthegame2);
        final EditText et1_input;
        tv2_text = (TextView)findViewById(R.id.editText9);
        tv4_text = (TextView)findViewById(R.id.textView9);
        et1_input = (EditText)findViewById(R.id.editText7);
        high_score = (TextView)findViewById(R.id.editText10);
        /*et1_input.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    onClickGo();
                    return true;
                }
                return false;
            }
        });*/
        et1_input.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
                if (s.toString().substring(arg1).contains("\n")) {
                    if (s.length() == 1)
                        et1_input.setText("");
                    onClickGo();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }

        });

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("status", 1);
        editor.commit();
        int tr = pref.getInt("Mode", -1);// getting Integer
        if(tr == 0)
        {
            sendch = "";
            nm4 = pref.getString("Last_set","Asia");
            sendch = sendch + nm4.charAt(nm4.length() - 1);
            tv2_text.setText(nm4);
            tv4_text.setText(sendch);
            high_score.setText(String.valueOf(pref.getInt("Last_score",0)));
            foo = pref.getInt("Last_score",0);
        }
        if(tr == 1 )
        {
            sendch="";
            foo = 0;
            tv2_text.setText("Asia");
            tv4_text.setText("a");
            high_score.setText("0");
        }
        level = pref.getInt("Level", -1);
        if( level != 1) {
            if (level == 2)
                time = 30000;
            if (level == 3)
                time = 15000;
            final TextView mTextField = (TextView) findViewById(R.id.textView10);
            tk = new CountDownTimer(time, 1000) {

                public void onTick(long millisUntilFinished) {
                    mTextField.setText("" + millisUntilFinished / 1000);
                }

                public void onFinish() {
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putInt("status", 0);
                    editor.commit();
                    int score = foo;
                    String name = pref.getString("Name", "");
                    String sex = "M/F";
                    String mode = "easy";
                    dbHelper.put_user(name, score, sex, mode);
                    Intent i = new Intent(playthegame2.this, earthcry.class);
                    startActivity(i);
                }
            }.start();
        }
    }

    public void onClickGo(){

        String nm1,nm2,nm3,nm4;
        String sendsql,sendch="";
        int l,l1;
        TextView tv2_text, tv4_text, tv6_text, high_score;
        EditText et1_input;
        char chlast;
        tv2_text = (TextView)findViewById(R.id.editText9);
        tv4_text = (TextView)findViewById(R.id.textView9);
        tv6_text = (TextView)findViewById(R.id.editText8);
        et1_input = (EditText)findViewById(R.id.editText7);
        high_score = (TextView)findViewById(R.id.editText10);

        nm1 = et1_input.getText().toString();
        nm2 = tv4_text.getText().toString();

        l = nm1.length();

        if (l==0) {
            Log.v("l", "0");
            tv6_text.setText("Come on, play along !");
            return;
        }
        nm4 = nm1.toLowerCase();
        nm4 = nm4.trim();
        nm1 = CapsFirst(nm4);

        chlast = nm1.charAt(0);
        chlast=Character.toLowerCase(chlast);
        //trial
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        int tr = pref.getInt("Mode", -1);
        contacts = dbHelper.Get_ContactDetails(nm1,tr);

        if (nm2.charAt(0) != chlast)
        {
            tv6_text.setText("Duh! Last Letter ?");
        }


        else if (contacts.id ==-1 || contacts.id ==-2 )
        {
            tv6_text.setText(contacts.name);
            et1_input.setText("");
            return;

        }

        else {
            final TextView mTextField = (TextView)findViewById(R.id.textView10);
            level = pref.getInt("Level", -1);
            if( level != 1) {
                if (level == 2)
                    time = 30000;
                if (level == 3)
                    time = 15000;
                tk.cancel();
                tk = new CountDownTimer(time, 1000) {

                    public void onTick(long millisUntilFinished) {
                        mTextField.setText("" + millisUntilFinished / 1000);
                    }

                    public void onFinish() {
                        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putInt("status", 0);
                        editor.commit();
                        int score = foo;
                        String name = pref.getString("Name", "");
                        String sex = "M/F";
                        String mode = "easy";
                        dbHelper.put_user(name, score, sex, mode);
                        Intent i = new Intent(playthegame2.this, earthcry.class);
                        startActivity(i);
                    }
                }.start();
            }
            foo = foo + 5;
            nm3 = String.valueOf(foo);
            sendsql = contacts.name;
            if ( sendsql.equals("WON") == true) {
                Intent i = new Intent(playthegame2.this,Maps.class);
                startActivity(i);
            }
            l1 = sendsql.length();
            pref = getApplicationContext().getSharedPreferences("MyPref", 0);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("Last_set", sendsql);
            editor.putInt("Last_score", foo);
            editor.commit();
            sendch = "";
            sendch = sendch + sendsql.charAt(l1 - 1);
            tv2_text.setText(sendsql+"!");
            tv4_text.setText(sendch);
            high_score.setText(nm3);
            tv6_text.setText("Game on!");
        }
        et1_input.setText("");
        return;


    }

    public void OnClickList2(View view){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        if (foo == 0) {
            Toast.makeText(this, "List is empty", Toast.LENGTH_SHORT).show();
        }
        else {
            if ( time != -1)
            {
                tk.cancel();
            }
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("Last_score", foo);
            editor.commit();
            Intent i = new Intent(this, list.class);
            startActivity(i);
        }
    }

    @Override
    public void onBackPressed() {
        if ( level !=1) {
            tk.cancel();
        }
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        int score = foo;
        if (foo == 0)
        {
            dbHelper.del_temp();
            pref = getApplicationContext().getSharedPreferences("MyPref", 0);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("Last_set", "Asia");
            editor.putInt("Last_score", 0);
            editor.commit();
        }
        String name = pref.getString("Name", "");
        System.out.println(name);
        String sex = "M/F";
        String mode = "easy";
        dbHelper.put_user(name, score, sex, mode);
        Intent intent = new Intent(playthegame2.this,newconhigh.class);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
        startActivity(intent);
        finish();
        System.exit(0);
    }

    public void OnClickHelp(View view)
    {
        if ( time != -1)
        {
            tk.cancel();
        }
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        if (foo == 0)
        {
            dbHelper.del_temp();
            pref = getApplicationContext().getSharedPreferences("MyPref", 0);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("Last_set", "Asia");
            editor.putInt("Last_score", 0);
            editor.commit();
        }
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("Last_score", foo);
        editor.commit();
        Intent i = new Intent(playthegame2.this,help.class);
        startActivity(i);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_playthegame2, menu);
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
    String CapsFirst(String str) {
        String[] words = str.split(" ");
        StringBuilder ret = new StringBuilder();
        for(int i = 0; i < words.length; i++) {
            ret.append(Character.toUpperCase(words[i].charAt(0)));
            ret.append(words[i].substring(1));
            if(i < words.length - 1) {
                ret.append(' ');
            }
        }
        return ret.toString();
    }
}
