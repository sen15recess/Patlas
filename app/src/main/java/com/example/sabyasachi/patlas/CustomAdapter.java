package com.example.sabyasachi.patlas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;


class CustomAdapter extends ArrayAdapter<String> {

    CustomAdapter(Context context,String[] places){
        super(context, R.layout.custom_row, places);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater sabyaInflater=LayoutInflater.from(getContext());
        View customView= sabyaInflater.inflate(R.layout.custom_row, parent, false);

        String singlePlaceItem=getItem(position);
        TextView sabyaText= (TextView)customView.findViewById(R.id.textView);



        sabyaText.setText(singlePlaceItem);


        return customView;



    }
}
