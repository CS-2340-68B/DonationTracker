package edu.gatech.cs2340_68b.donationtracker.Controllers.Common;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import edu.gatech.cs2340_68b.donationtracker.R;

public class DataListAdapter extends BaseAdapter {
    //        String[] Title, Detail;
    ArrayList<Map.Entry<String, String>> data;
    LayoutInflater inflater;
//        int[] imge;

    DataListAdapter() {
//            Title = null;
//            Detail = null;
//            imge=null;
        data = null;
    }

//        public dataListAdapter(String[] text, String[] text1) {
//            Title = text;
//            Detail = text1;
//            imge = text3;
//        }

    public DataListAdapter(ArrayList<Map.Entry<String, String>> data, LayoutInflater inflater) {
        this.data = data;
        this.inflater = inflater;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row = inflater.inflate(R.layout.list_view_layout, parent, false);
        TextView title, detail;
//            ImageView i1;
        title = (TextView) row.findViewById(R.id.title);
        detail = (TextView) row.findViewById(R.id.detail);
//            i1=(ImageView)row.findViewById(R.id.img);
//            title.setText(Title[position]);
//            detail.setText(Detail[position]);
//            i1.setImageResource(imge[position]);
        title.setText(data.get(position).getKey());
        detail.setText(data.get(position).getValue());

        return (row);
    }
}