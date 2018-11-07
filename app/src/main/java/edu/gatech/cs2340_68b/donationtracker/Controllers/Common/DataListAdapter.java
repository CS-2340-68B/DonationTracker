package edu.gatech.cs2340_68b.donationtracker.Controllers.Common;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import edu.gatech.cs2340_68b.donationtracker.R;

public class DataListAdapter extends BaseAdapter {
    @Nullable
    ArrayList<Map.Entry<String, String>> data;
    LayoutInflater inflater;

    DataListAdapter() {
        data = null;
    }


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
        title = row.findViewById(R.id.title);
        detail = row.findViewById(R.id.detail);
        title.setText(data.get(position).getKey());
        detail.setText(data.get(position).getValue());
        return (row);
    }
}