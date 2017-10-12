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
        return tables.get(position).name;
    }

    public int addTable() {
        tables.add(new TempTable());
        return tables.size() - 1;
    }

}
