package com.toharifqi.um.ukmq;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.toharifqi.um.ukmq.adapter.HistoryTabAdapter;

public class HistoryActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private HistoryTabAdapter historyTabAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        //fab
        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //tabLayout
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.viewPagerHistory);

        historyTabAdapter = new HistoryTabAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(historyTabAdapter);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                    historyTabAdapter.notifyDataSetChanged();
                }else if (tab.getPosition() == 1) {
                    historyTabAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }
}