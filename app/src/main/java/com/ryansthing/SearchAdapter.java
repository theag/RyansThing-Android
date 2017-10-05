package com.ryansthing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by nbp184 on 2017/09/29.
 */
public class SearchAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Object> list;
    private ArrayList<Object> search;

    public SearchAdapter(Context context, ArrayList<Object> list) {
        this.context = context;
        this.list = list;
        this.search = null;
    }

    public SearchAdapter(Context context, Object[] list) {
        this.context = context;
        this.list = new ArrayList<>();
        for(Object o : list) {
            this.list.add(o);
        }
        this.search= null;
    }

    @Override
    public int getCount() {
        if(search == null) {
            return list.size();
        } else {
            return search.size();
        }
    }

    @Override
    public Object getItem(int i) {
        if(search == null) {
            return list.get(i);
        } else {
            return search.get(i);
        }
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(android.R.layout.simple_list_item_1, null);
        TextView tv = (TextView) view.findViewById(android.R.id.text1);
        tv.setText(getItem(i).toString());
        return view;
    }

    public void search(String text) {
        if(text.isEmpty()) {
            search = null;
        } else {
            search = new ArrayList<>();
            text = text.toLowerCase();
            for(Object o : list) {
                if(o.toString().toLowerCase().contains(text)) {
                    search.add(o);
                }
            }
        }
        notifyDataSetChanged();
    }
}
