package com.example.sabyasachi.patlas;


import android.content.SharedPreferences;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class MaleActivity extends AppCompatActivity implements View.OnClickListener{
    public EditText editTextnm;
    static int i = 0;
    private GestureDetectorCompat gestureDetectorCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_male);

        gestureDetectorCompat = new GestureDetectorCompat(this, new MyGestureListener());
        editTextnm = (EditText)findViewById(R.id.editText);
        editTextnm.setOnClickListener(this);
    }
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if ( i == 0) {
            i++;
            editTextnm.setText("");
        }
    }

    public void onClick9(View view){
        Intent i= new Intent(this,FemaleActivity.class);
        startActivity(i);
    }

    public void onClick11(View view){
        String name="";
        editTextnm = (EditText)findViewById(R.id.editText);
        name = editTextnm.getText().toString();
        if (name.equals("Enter Your Name") == true || name.length() == 0 )
        {
            Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
        }
        else if (name.length() > 15)
        {
            Toast.makeText(this, "Too Long a name", Toast.LENGTH_SHORT).show();
        }
        else {
            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("Sex", 1);
            System.out.println(name);
            editor.putString("Name", name);
            editor.commit();
            Intent i = new Intent(this, playthegame.class);
            startActivity(i);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MaleActivity.this,newconhigh.class);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
        startActivity(intent);
        finish();
        System.exit(0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureDetectorCompat.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        //handle 'swipe left' action only

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {

         /*
         Toast.makeText(getBaseContext(),
          event1.toString() + "\n\n" +event2.toString(),
          Toast.LENGTH_SHORT).show();
         */

            if(event2.getX() < event1.getX()){
                //Toast.makeText(getBaseContext(),
                        //"Swipe left - startActivity()",
                        //Toast.LENGTH_SHORT).show();

                //switch another activity
                Intent intent = new Intent(
                        MaleActivity.this, FemaleActivity.class);
                startActivity(intent);
            }

            return true;
        }
    }
}
