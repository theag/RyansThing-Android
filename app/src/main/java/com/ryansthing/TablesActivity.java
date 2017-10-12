package com.ryansthing;

import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class TablesActivity extends AppCompatActivity implements TableFragment.OnFragmentInteractionListener {

    private TabsPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tables);

        PagerTabStrip pagerTabStrip = (PagerTabStrip)findViewById(R.id.pagerTabStrip);
        pagerTabStrip.setTabIndicatorColorResource(R.color.colorAccent);
        pagerTabStrip.setTextColor(getColor(android.R.color.white));

        //todo: add in editing
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

    @Override
    public void onFragmentInteraction(int id) {
        ViewPager viewPager = (ViewPager)findViewById(R.id.pager);
        switch(id) {
            case R.id.btnUpdateTab:
                adapter.getTable(viewPager.getCurrentItem()).name = ((EditText)adapter.getItem(viewPager.getCurrentItem()).getView().findViewById(R.id.txtName)).getText().toString().trim();
                adapter.notifyDataSetChanged();
                break;
            case R.id.btnDelete:
                if(!adapter.removeTable(viewPager.getCurrentItem())) {
                    //todo: notify cant remove last table
                } else {
                    if(viewPager.getCurrentItem() == adapter.getCount()) {
                        viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
                    }
                }
                break;
        }
    }
}
