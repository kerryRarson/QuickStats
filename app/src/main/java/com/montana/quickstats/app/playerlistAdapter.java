package com.montana.quickstats.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by klarsen on 5/28/2014.
 */
public class playerlistAdapter extends ArrayAdapter<Player> {
    int resource;
    String response;
    Context context;

    //Initialize adapter
    public playerlistAdapter(Context context, int resource, List<Player> items){
        super(context, resource, items);
        this.resource=resource;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout listView;
        //get the current player object
        Player player = getItem(position);
        //Inflate the view
        if(convertView==null)
        {
            listView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi;
            vi = (LayoutInflater)getContext().getSystemService(inflater);
            vi.inflate(resource, listView, true);
        }
        else
        {
            listView = (LinearLayout) convertView;
        }
        //Get the text boxes from the listitem.xml file
        TextView playerName =(TextView)listView.findViewById(R.id.lblName);
        TextView hgt =(TextView)listView.findViewById(R.id.hgt);
        TextView wgt = (TextView)listView.findViewById(R.id.wgt);

        //Assign the appropriate data from our alert object above
        playerName.setText(player.getPlayerName());
        hgt.setText(player.getHeight());
        wgt.setText(player.getWeight());

        return listView;
    }
}
