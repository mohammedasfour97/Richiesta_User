package com.rkesta.richiesta.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rkesta.richiesta.R;

import java.util.ArrayList;
import java.util.HashMap;

public class spinner_idname_adapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater;

    public spinner_idname_adapter(Activity a, ArrayList<HashMap<String, String>> list) {

//        HashMap<String,String> x = new HashMap<>();
//        for (int i = 0; i < 1000; i++) {
//            x = new HashMap<>();
//            x.put("id",i+"");
//            x.put("name",i+"");
//            list.add(x);
//        }

        this.activity = a;
        this.data = list;
        this.inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.item_spin_id_name, null);

        // int[] To  = {R.id.list_drawerID, R.id.list_drawericon, R.id.list_drawerItemname };
        TextView id = (TextView)          vi.findViewById(R.id.item_idname_id); // id
        TextView name = (TextView)        vi.findViewById(R.id.item_idname_name); //  name


        HashMap<String, String> itemlist = new HashMap<String, String>();
        itemlist = data.get(position);

        // Setting all values in listview
        id.setText(itemlist.get("id").toString());
        name.setText(itemlist.get("name").toString());

        // background.setImageResource((Integer) itemlist.get("background"));
        //imageLoader.DisplayImage(song.get(CustomizedListView.KEY_THUMB_URL), thumb_image);

        return vi;
    }
}