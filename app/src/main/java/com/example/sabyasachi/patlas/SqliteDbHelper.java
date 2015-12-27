package com.example.sabyasachi.patlas;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

class SqlLiteDbHelper extends SQLiteOpenHelper {

    // All Static variables
// Database Version
    int delcheck = 0;
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "patlasdata.db";
    private static final String DB_PATH_SUFFIX = "/databases/";
    static Context ctx;
    public SqlLiteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        ctx = context;
    }

    // Getting single contact
    public Contact Get_ContactDetails(String word_input,int mode) {


        char word_input_last,c;
        String nm2,nm3,nm4,nm5;
        if (delcheck == 0 && mode == 1) { //delete temp contents
            SQLiteDatabase quickdel;
            quickdel = this.getWritableDatabase();
            quickdel.delete("temp", null, null);
            quickdel.close();
            delcheck = 1;
            //delete done
        }
        word_input_last = word_input.charAt(word_input.length()-1);
        word_input_last = Character.toUpperCase(word_input_last);
        SQLiteDatabase db = this.getWritableDatabase();
        Log.v("myTag", word_input);
        nm2 = "SELECT * FROM countries WHERE PLACES = " + "\'" + word_input + "\'";
        Cursor curse = db.rawQuery(nm2,null);
        if ( curse.getCount() == 0)
        {
            Contact invalid = new Contact(-1,"Place does not exist!");
            db.close();
            return invalid;
        }

        nm3 = "SELECT * FROM temp WHERE PLACES = " + "\'" + word_input + "\'";
        Cursor tempcurse = db.rawQuery(nm3, null);
        if (tempcurse.getCount() != 0) {
            Contact rep = new Contact(-2, "Place repeating!");
            db.close();
            return rep;
        }


        else
        {
            ContentValues values = new ContentValues();
            values.put("PLACES",word_input);
            db.insert("temp",null,values);
            db.close();
        }






        SQLiteDatabase dbh = this.getWritableDatabase();
        //patlas return query form
        for (int j = 1; j <=2;j++) {
            String nm = "SELECT * FROM countries t1 LEFT JOIN temp t2 ON t2.places = t1.places WHERE t2.places IS NULL and t1.places like " + "\'" + word_input_last + "%\'";
            Cursor cursor = dbh.rawQuery(nm, null);
            String ret_place;
            String search;
            Cursor usecase;
            int i = 0, p = 0;
            //return data
            if (cursor.moveToFirst()) {

                while (cursor.isAfterLast() == false) {
                    i++;
                    Contact cont = new Contact(cursor.getInt(0), cursor.getString(1));
                    ret_place = cursor.getString(1);
                    search = "SELECT * FROM usecase where places =\'" + ret_place + "\'";
                    usecase = dbh.rawQuery(search, null);
                    if (usecase.getCount() == 0) {
                        ContentValues values = new ContentValues();
                        values.put("PLACES", cursor.getString(1));
                        dbh.insert("temp", null, values);
                        dbh.insert("usecase", null, values);
                        cursor.close();
                        usecase.close();
                        curse.close();
                        tempcurse.close();
                        dbh.close();
                        return cont;
                    } else {
                        p++;
                        cursor.moveToNext();
                    }
                }
                if (p == i && j == 1) {
                    dbh.delete("usecase", null, null);
                    cursor.close();
                    curse.close();
                    tempcurse.close();
                    dbh.close();
                    // DELETE USECASE CONTENTS
                }
                if (p == i && j == 2) {
                    cursor.close();
                    curse.close();
                    tempcurse.close();
                    dbh.close();
                    Contact cont = new Contact(-1, "WIN");
                    return cont;
                    //YOU WIN!
                    // calling VICTORY INTENT
                }
            }
        }


        return null;

    }

    //fetch player details

    public void put_user(String name,int score,String sex,String mode)
    {
        SQLiteDatabase dbh = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Name",name);
        values.put("Score",score);
        values.put("Sex",sex);
        values.put("Mode",mode);
        dbh.insert("User", null, values);
        dbh.close();
    }

    public String[] get_user()
    {
        String users[]= new String[100];
        int i = 0,j=0;
        ArrayList<String> names = new ArrayList<String>();
        SQLiteDatabase dbh = this.getWritableDatabase();
        String nm = "SELECT * FROM User ORDER BY Score DESC";
        Cursor cursor = dbh.rawQuery(nm, null);
        cursor.moveToFirst();
        if (cursor .moveToFirst()) {

            while (cursor.isAfterLast() == false) {
                String name = cursor.getString(cursor
                        .getColumnIndex("Name"));
                for ( j = 0; j < names.size();j++)
                {
                    if (name.equalsIgnoreCase(names.get(j)) == true)
                    {
                        j = -1;
                        break;
                    }
                }
                if ( i== 5)
                    break;
                if ( j != -1) {
                    names.add(name);
                    i++;
                }
                cursor.moveToNext();

            }
        }
        String[] stockArr = new String[names.size()];
        stockArr = names.toArray(stockArr);
        return stockArr;

    }
    public String[] get_userscore()
    {
        String users[]= new String[100];
        int i = 0,j=0;
        ArrayList<String> names = new ArrayList<String>();
        ArrayList<String> scores = new ArrayList<String>();
        SQLiteDatabase dbh = this.getWritableDatabase();
        String nm = "SELECT * FROM User ORDER BY Score DESC";
        Cursor cursor = dbh.rawQuery(nm, null);
        cursor.moveToFirst();
        if (cursor .moveToFirst()) {

            while (cursor.isAfterLast() == false) {
                String name = cursor.getString(cursor
                        .getColumnIndex("Name"));
                String score = String.valueOf(cursor.getInt(cursor
                        .getColumnIndex("Score")));
                for ( j = 0; j < names.size();j++)
                {
                    if (name.equalsIgnoreCase(names.get(j)) == true)
                    {
                        j = -1;
                        break;
                    }
                }
                if ( i== 5)
                    break;
                if ( j != -1) {
                    i++;
                    names.add(name);
                    scores.add(score);
                }
                cursor.moveToNext();
            }
        }
        String[] stockArr = new String[scores.size()];
        stockArr = scores.toArray(stockArr);
        return stockArr;

    }

    public String[] temp_data()
    {
        String temp[] = new String[2000];
        ArrayList<String> names = new ArrayList<String>();
        SQLiteDatabase dbh = this.getWritableDatabase();
        String nm = "SELECT * FROM temp";
        Cursor cursor = dbh.rawQuery(nm, null);
        cursor.moveToFirst();
        if (cursor .moveToFirst()) {

            while (cursor.isAfterLast() == false) {
                String name = cursor.getString(cursor
                        .getColumnIndex("places"));

                names.add(name);
                cursor.moveToNext();
            }
        }
        String[] stockArr = new String[names.size()];
        stockArr = names.toArray(stockArr);
        return stockArr;
    }
    public void del_temp()
    {
        SQLiteDatabase quickdel;
        quickdel = this.getWritableDatabase();
        quickdel.delete("temp", null, null);
        quickdel.close();
    }

    public void CopyDataBaseFromAsset() throws IOException {

        InputStream myInput = ctx.getAssets().open(DATABASE_NAME);

// Path to the just created empty db
        String outFileName = getDatabasePath();

// if the path doesn't exist first, create it
        File f = new File(ctx.getApplicationInfo().dataDir + DB_PATH_SUFFIX);
        if (!f.exists())
            f.mkdir();

// Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

// transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

// Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }
    private static String getDatabasePath() {
        return ctx.getApplicationInfo().dataDir + DB_PATH_SUFFIX
                + DATABASE_NAME;
    }

    public SQLiteDatabase openDataBase() throws SQLException {
        File dbFile = ctx.getDatabasePath(DATABASE_NAME);

        if (!dbFile.exists()) {
            try {
                CopyDataBaseFromAsset();
                System.out.println("Copying success from Assets folder");
            } catch (IOException e) {
                throw new RuntimeException("Error creating source database", e);
            }
        }


        return SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.CREATE_IF_NECESSARY);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// TODO Auto-generated method stub

    }

}