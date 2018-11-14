package edu.gatech.cs2340_68b.donationtracker.Controllers.Common;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import edu.gatech.cs2340_68b.donationtracker.R;

@SuppressWarnings("AssignmentOrReturnOfFieldWithMutableType")
public class DataListAdapter extends BaseAdapter {
    @Nullable
    private final
    List<Map.Entry<String, String>> data;
    private LayoutInflater inflater;

    DataListAdapter() {
        data = null;
    }


    public DataListAdapter(@Nullable List<Map.Entry<String,
            String>> data, LayoutInflater inflater) {
        this.data = data;
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return (data != null) ? data.size() : 0;
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row = inflater.inflate(R.layout.list_view_layout, parent, false);
        TextView title;
        TextView detail;
        title = row.findViewById(R.id.title);
        detail = row.findViewById(R.id.detail);
        title.setText((data != null) ? data.get(position).getKey() : null);
        detail.setText(Objects.requireNonNull(data).get(position).getValue());
        return (row);
    }
}