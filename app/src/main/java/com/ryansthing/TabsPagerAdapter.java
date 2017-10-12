package com.ryansthing;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ryansthing.data.ReadTable;
import com.ryansthing.data.TempTable;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by nbp184 on 2017/10/12.
 */

public class TabsPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<TempTable> tables;

    public TabsPagerAdapter(FragmentManager fragmentManager, InputStream file) {
        super(fragmentManager);
        tables = new ArrayList<>();
        if(file != null) {
            ReadTable.readTemp(file, tables);
        } else {
            tables.add(new TempTable());
        }
    }

    @Override
    public Fragment getItem(int position) {
        return TableFragment.newInstance(tables.get(position));
    }

    @Override
    public int getCount() {
        return tables.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = tables.get(position).name.trim();
        if(title.isEmpty()) {
            title = "New Table";
        }
        return title;
    }

    public TempTable getTable(int position) {
        return tables.get(position);
    }

    public int addTable() {
        tables.add(new TempTable());
        notifyDataSetChanged();
        return tables.size() - 1;
    }

    public boolean removeTable(int position) {
        if(tables.size() == 1) {
            return false;
        }
        tables.remove(position);
        notifyDataSetChanged();
        return true;
    }

}
