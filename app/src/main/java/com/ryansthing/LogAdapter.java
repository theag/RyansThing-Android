package com.ryansthing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.File;
import java.io.FilenameFilter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by nbp184 on 2017/09/29.
 */
public class LogAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<File> files;
    private SimpleDateFormat sdf;

    public LogAdapter(Context context, final String exclude) {
        this.context = context;
        File[] files = context.getFilesDir().listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File file, String s) {
                return s.startsWith("log-") && s.endsWith(".txt") && s.compareTo(exclude) != 0;
            }
        });
        this.files = new ArrayList<>();
        for(File f : files) {
            this.files.add(f);
        }
        sdf = new SimpleDateFormat("yyyy-MM-dd");
    }

    @Override
    public int getCount() {
        return files.size();
    }

    @Override
    public File getItem(int i) {
        return files.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.item_log, null);
        File f = getItem(i);
        TextView tv = view.findViewById(R.id.lblName);
        tv.setText(f.getName());
        tv = view.findViewById(R.id.lblDate);
        tv.setText(sdf.format(new Date(f.lastModified())));
        view.findViewById(R.id.btnDelete).setTag(f);
        return view;
    }

    public void delete(File file) {
        file.delete();
        files.remove(file);
        notifyDataSetChanged();
    }

    public void cleanUp(long now, long diff) {
        ArrayList<File> toRemove = new ArrayList<>();
        for(File f : files) {
            if(now - f.lastModified() > diff) {
                f.delete();
                toRemove.add(f);
            }
        }
        for(File f : toRemove) {
            files.remove(f);
        }
        notifyDataSetChanged();
    }
}
