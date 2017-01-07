package com.fanciestw.listpro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by William Lin on 1/6/2017.
 */

public class AllListAdapter extends ArrayAdapter<List> {
    Context context;
    int layoutResourceId;
    ArrayList<List> allList = null;

    public AllListAdapter(Context context, ArrayList<List> allList){
        super(context, 0, allList);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.allList = allList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        //Get the data item for this position
        List list = allList.get(position);
        //Check if an existing view is being reused, otherwise inflate the view
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.all_list_item_layout, parent, false);
        }
        //Lookup view for data population
        TextView tvTitle = (TextView)convertView.findViewById(R.id.list_item_title);
        TextView tvDesc = (TextView)convertView.findViewById(R.id.list_item_desc);
        //Populate the date into the template view using the data object
        tvTitle.setText(list.listTitle);
        tvDesc.setText(list.listDescription);
        //Return the completed view to render on screen
        return convertView;
    }
}
