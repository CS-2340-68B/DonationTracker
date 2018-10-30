package edu.gatech.cs2340_68b.donationtracker.View.searchViews;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import edu.gatech.cs2340_68b.donationtracker.Models.Location;
import edu.gatech.cs2340_68b.donationtracker.R;
import edu.gatech.cs2340_68b.donationtracker.View.MainPage;
import edu.gatech.cs2340_68b.donationtracker.View.locationViews.LocationForSearch;

public class SearchMenu extends AppCompatActivity {
    private TextInputEditText searchbar;
    private RadioGroup searchRadioGroup;
    private RadioButton searchItemAllButton;
    private RadioButton searchCatAllButton;
    private RadioButton searchItemLocButton;
    private RadioButton searchCatLocButton;
    private int searchTypeFlag;
    private Location locationSearch;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.search_menu);

        searchbar = (TextInputEditText) findViewById(R.id.searchbar);
        searchRadioGroup = (RadioGroup) findViewById(R.id.searchRadioGroup);
        searchButton = (Button) findViewById(R.id.searchButton);
        searchTypeFlag = -1;
//
        searchRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                View radioButton = searchRadioGroup.findViewById(checkedId);
                searchTypeFlag = searchRadioGroup.indexOfChild(radioButton);
            }
        });
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationSearch = null;
                String searchString = searchbar.getText().toString();

                switch (searchTypeFlag) {
                    case 0: // searchItemAll
                        Intent intent1 = new Intent(SearchMenu.this, SearchResult.class);
                        intent1.putExtra("SearchFlag", searchTypeFlag);
                        intent1.putExtra("LOCATIONSEARCH", locationSearch); // should pass null
                        intent1.putExtra("SearchString", searchString);
                        startActivity(intent1);
                        break;
                    case 1: // searchCatAll
                        Intent intent2 = new Intent(SearchMenu.this, SearchResult.class);
                        intent2.putExtra("SearchFlag", searchTypeFlag);
                        intent2.putExtra("LOCATIONSEARCH", locationSearch); // should pass null
                        intent2.putExtra("SearchString", searchString);
                        startActivity(intent2);
                        break;
                    case 2: // searchItemLoc
                        Intent intent3 = new Intent(SearchMenu.this, LocationForSearch.class);
                        intent3.putExtra("SearchFlag", searchTypeFlag);
                        locationSearch = (Location) getIntent().getSerializableExtra("LOCATION");
                        intent3.putExtra("LOCATIONSEARCH", locationSearch);
                        intent3.putExtra("SearchString", searchString);
                        startActivity(intent3);
                        break;
                    case 3: // searchCatLoc
                        Intent intent4 = new Intent(SearchMenu.this, LocationForSearch.class);
                        intent4.putExtra("SearchFlag", searchTypeFlag);
                        locationSearch = (Location) getIntent().getSerializableExtra("LOCATION");
                        intent4.putExtra("LOCATIONSEARCH", locationSearch);
                        intent4.putExtra("SearchString", searchString);
                        startActivity(intent4);
                        break;
                }
            }
        });
    }
}
