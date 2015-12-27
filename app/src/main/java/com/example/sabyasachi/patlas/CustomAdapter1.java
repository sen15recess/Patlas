package com.example.sabyasachi.patlas;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;


public class CustomAdapter1 extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] web;
    private final String[] imageId;
    public CustomAdapter1(Activity context,
                  String[] web, String[] imageId) {
        super(context, R.layout.custom_row, web);
        this.context = context;
        this.web = web;
        this.imageId = imageId;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater sabyaInflater=LayoutInflater.from(getContext());
        View customView= sabyaInflater.inflate(R.layout.custom_row1, parent, false);

        String singlePlaceItem=getItem(position);
        TextView sabhyaText= (TextView)customView.findViewById(R.id.textView3);
        TextView ssText= (TextView)customView.findViewById(R.id.textView4);





        sabhyaText.setText(web[position]);
        ssText.setText(imageId[position]);


        return customView;



    }
}
