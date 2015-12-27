package com.example.sabyasachi.patlas;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class FemaleActivity extends ActionBarActivity implements View.OnClickListener{

    private GestureDetectorCompat gestureDetectorCompat;
    public EditText editText;
    static int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_female);

       // TextView text2ndActivity = new TextView(this);
        //text2ndActivity.setText("Second Activity");
        //setContentView(text2ndActivity);

        gestureDetectorCompat = new GestureDetectorCompat(this, new My2ndGestureListener());
        editText = (EditText)findViewById(R.id.editText2);
        editText.setOnClickListener(this);
    }
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (i == 0)
        {
        editText.setText("");
        i++;
        }

    }

    public void onClick12(View view){
        String name="";
        editText = (EditText)findViewById(R.id.editText2);
        name = editText.getText().toString();
        if (name.equals("Enter Your Name") == true || name.length() == 0 )
        {
            Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
        }
        else {
            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("Sex", 0);
            editor.putString("Name", name);
            editor.commit();
            Intent i = new Intent(this, playthegame2.class);
            startActivity(i);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureDetectorCompat.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    public void onClick10(View view){

        Intent i= new Intent(this,MaleActivity.class);
        startActivity(i);
    }

    class My2ndGestureListener extends GestureDetector.SimpleOnGestureListener {
        //handle 'swipe right' action only

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {

         /*
         Toast.makeText(getBaseContext(),
          event1.toString() + "\n\n" +event2.toString(),
          Toast.LENGTH_SHORT).show();
         */

            if(event2.getX() > event1.getX()){
                //Toast.makeText(getBaseContext(),
                        //"Swipe right - finish()",
                        //Toast.LENGTH_SHORT).show();

                finish();
            }

            return true;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(FemaleActivity.this,newconhigh.class);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
        startActivity(intent);
        finish();
        System.exit(0);
    }

}
