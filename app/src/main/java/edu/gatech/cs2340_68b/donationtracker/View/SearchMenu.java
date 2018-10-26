package edu.gatech.cs2340_68b.donationtracker.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.w3c.dom.Text;

import edu.gatech.cs2340_68b.donationtracker.R;

public class SearchMenu extends AppCompatActivity {
    private TextInputEditText searchbar;
    private RadioGroup searchRadioGroup;
    private RadioButton searchItemAllButton;
    private RadioButton searchCatAllButton;
    private RadioButton searchItemLocButton;
    private RadioButton searchCatLocButton;
    private int searchTypeFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.search_menu);

        searchbar = (TextInputEditText) findViewById(R.id.searchbar);
        searchRadioGroup = (RadioGroup) findViewById(R.id.searchRadioGroup);
        searchTypeFlag = -1;

        searchRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                View radioButton = searchRadioGroup.findViewById(checkedId);
                int index = searchRadioGroup.indexOfChild(radioButton);

                switch (index) {
                    case 0: // first button
                        searchTypeFlag = 0;
                        break;
                    case 1: // secondbutton
                        searchTypeFlag = 1;
                        break;
                    case 2:
                        searchTypeFlag = 2;
                        break;
                    case 3:
                        searchTypeFlag = 3;
                        break;
                }

                Intent intent = new Intent(SearchMenu.this, SearchResult.class);
                startActivity(intent);
                intent.putExtra("SearchFlag", searchTypeFlag);
            }
        });

    }

}
