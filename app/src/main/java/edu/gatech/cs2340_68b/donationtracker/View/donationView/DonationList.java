package edu.gatech.cs2340_68b.donationtracker.View.donationView;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;

import edu.gatech.cs2340_68b.donationtracker.Models.DonationDetail;
import edu.gatech.cs2340_68b.donationtracker.R;

public class DonationList extends AppCompatActivity {

    private ListView donationListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donation_list);
        final String locatioName = (String) getIntent().getSerializableExtra("PLACENAME");
        donationListView = findViewById(R.id.donationList);

        DatabaseReference donationDB = FirebaseDatabase.getInstance().getReference("donations/");
        Query query = donationDB.orderByChild("location").equalTo(locatioName);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Map.Entry<String, String>> donationInfo = new ArrayList<>();
                final ArrayList<DonationDetail> donationList = new ArrayList<>();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    DonationDetail detail = snapshot.getValue(DonationDetail.class);
                    donationList.add(detail);
                    Map.Entry<String,String> entry =
                        new AbstractMap.SimpleEntry<>(detail.getName(), detail.getFullDescription());
                    donationInfo.add(entry);
                }
                donationListView.setAdapter(new dataListAdapter(donationInfo));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    class dataListAdapter extends BaseAdapter {
        //        String[] Title, Detail;
        ArrayList<Map.Entry<String, String>> data;
//        int[] imge;

        dataListAdapter() {
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

        public dataListAdapter(ArrayList<Map.Entry<String, String>> data) {
            this.data = data;
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

            LayoutInflater inflater = getLayoutInflater();
            View row;
            row = inflater.inflate(R.layout.list_view_layout, null, true);
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
}
