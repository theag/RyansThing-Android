package com.ryansthing;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.ryansthing.data.Table;
import com.ryansthing.data.TempEntry;
import com.ryansthing.data.TempTable;

public class TablesActivity extends AppCompatActivity implements TableFragment.OnFragmentInteractionListener {

    private static final int REQUEST_ADD_TABLE_ENTRY = 1;
    private static final int REQUEST_EDIT_TABLE_ENTRY = 2;

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
    public void onFragmentInteraction(int id, Bundle data) {
        ViewPager viewPager = (ViewPager)findViewById(R.id.pager);
        TempTable tbl = adapter.getTable(viewPager.getCurrentItem());
        Intent intent;
        switch(id) {
            case R.id.btnUpdateTab:
                tbl.name = data.getString(TableFragment.KEY_NAME);
                adapter.notifyDataSetChanged();
                break;
            case R.id.btnDeleteTable:
                if(!adapter.removeTable(viewPager.getCurrentItem())) {
                    //todo: notify cant remove last table
                } else {
                    if(viewPager.getCurrentItem() == adapter.getCount()) {
                        viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
                    }
                }
                break;
            case R.id.btnAddItem:
                intent = new Intent(this, TableEntryActivity.class);
                intent.putExtra(TableEntryActivity.TABLE_NAME, tbl.name);
                startActivityForResult(intent, REQUEST_ADD_TABLE_ENTRY);
                break;
            case R.id.loItemListEditDelete:
                if(data.containsKey(TableFragment.KEY_ENTRY)) {
                    intent = new Intent(this, TableEntryActivity.class);
                    intent.putExtra(TableEntryActivity.TABLE_NAME, tbl.name);
                    intent.putExtra(TableEntryActivity.TEMP_ENTRY_POSITION, data.getInt(TableFragment.KEY_ENTRY));
                    intent.putExtra(TableEntryActivity.TEMP_ENTRY, tbl.entries.get(data.getInt(TableFragment.KEY_ENTRY)));
                    startActivityForResult(intent, REQUEST_EDIT_TABLE_ENTRY);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ViewPager viewPager = (ViewPager)findViewById(R.id.pager);
        TempTable tbl = adapter.getTable(viewPager.getCurrentItem());
        switch(requestCode) {
            case REQUEST_ADD_TABLE_ENTRY:
                if(resultCode == RESULT_OK) {
                    tbl.entries.add((TempEntry)data.getParcelableExtra(TableEntryActivity.TEMP_ENTRY));
                    tbl.adapter.notifyDataSetChanged();
                }
                break;
            case REQUEST_EDIT_TABLE_ENTRY:
                if(resultCode == RESULT_OK) {
                    tbl.entries.set(data.getIntExtra(TableEntryActivity.TEMP_ENTRY_POSITION, -1), (TempEntry)data.getParcelableExtra(TableEntryActivity.TEMP_ENTRY));
                    tbl.adapter.notifyDataSetChanged();
                }
                break;
        }
    }

}
