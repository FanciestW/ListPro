package com.fanciestw.listpro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by William on 3/16/2017.
 */

public class ListItemAdapter extends ArrayAdapter<ListItem> {
    Context context;
    int layoutResourceId;
    ArrayList<ListItem> allList = null;

    public ListItemAdapter(Context context, ArrayList<ListItem> allList){
        super(context, 0, allList);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.allList = allList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        //Get the data item for this position
        final ListItem listItem = allList.get(position);
        //Check if an existing view is being reused, otherwise inflate the view
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_items_layout, parent, false);
        }
        //Lookup view for data population
        TextView tvTitle = (TextView)convertView.findViewById(R.id.list_item_title);
        TextView tvDesc = (TextView)convertView.findViewById(R.id.list_item_desc);
        final CheckBox checkBox = (CheckBox)convertView.findViewById(R.id.list_item_checkBox);
        //Populate the date into the template view using the data object
        tvTitle.setText(listItem.itemTitle);
        tvDesc.setText(listItem.itemDescription);
        checkBox.setChecked(listItem.checked);
        //Return the completed view to render on screen

        ImageView deleteBtn = (ImageView)convertView.findViewById(R.id.itemdelete);
        deleteBtn.setOnClickListener(new View.OnClickListener(){    //Onclick Listener handler for trash button
            @Override
            public void onClick(View view){
                if(context instanceof listDetails){
                    ((com.fanciestw.listpro.listDetails)context).deleteListItem(position, listItem);
                }
            }
        });
        checkBox.setOnClickListener(new View.OnClickListener(){
           @Override
            public void onClick(View view){
               if(context instanceof listDetails){
                   ((com.fanciestw.listpro.listDetails)context).checkOnOff(position, listItem, checkBox.isChecked());
               }
           }
        });

        return convertView;
    }

    public void updateListItem(ListItem updatedListItem){
        for(int i = 0; i < allList.size(); i++){
            if(allList.get(i).itemID == updatedListItem.itemID){
                allList.set(i, updatedListItem);
                this.notifyDataSetChanged();
                break;
            }
        }
    }
}
