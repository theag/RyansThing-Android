package com.ryansthing;

import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class TablesActivity extends AppCompatActivity {

    private TabsPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tables);

        PagerTabStrip pagerTabStrip = (PagerTabStrip)findViewById(R.id.pagerTabStrip);
        pagerTabStrip.setTabIndicatorColorResource(R.color.colorAccent);

        //todo add in editing
        adapter = new TabsPagerAdapter(getSupportFragmentManager(), null);
        ViewPager viewPager = (ViewPager)findViewById(R.id.pager);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
    }

    public void buttonClick(View view) {
        switch(view.getId()) {
            case R.id.btnAddTable:
                ViewPager viewPager = (ViewPager)findViewById(R.id.pager);
                viewPager.setCurrentItem(adapter.addTable());
                break;
        }
    }

}
