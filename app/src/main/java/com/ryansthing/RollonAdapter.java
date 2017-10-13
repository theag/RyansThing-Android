package com.ryansthing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ryansthing.data.TempEntry;

import java.util.ArrayList;

/**
 * Created by nbp184 on 2017/10/13.
 */

public class RollonAdapter extends BaseAdapter {

    public final ArrayList<String> entries;
    private Context context;
    private AdapterInteractionListener listener;

    public RollonAdapter(Context context, ArrayList<String> entries) {
        this.context = context;
        this.entries = entries;
    }

    public void setListener(AdapterInteractionListener listener) {
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return entries.size();
    }

    @Override
    public String getItem(int position) {
        return entries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        String entry = getItem(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_list_edit_delete, null);
        TextView tv = (TextView)view.findViewById(R.id.txtText);
        tv.setText(entry);
        if(listener != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.buttonPressed(position, v.getId());
                }
            });
            view.findViewById(R.id.btnRemove).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.buttonPressed(position, v.getId());
                }
            });
        }
        return view;
    }

    public interface AdapterInteractionListener {
        void buttonPressed(int position, int id);
    }
}
