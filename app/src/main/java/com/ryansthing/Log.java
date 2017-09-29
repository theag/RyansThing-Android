package com.ryansthing;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by nbp184 on 2017/09/28.
 */
public class Log {

    private String filename;
    public final ArrayList<String> data;
    private final Context context;

    public static String getToday() {
        Calendar today = Calendar.getInstance();
        String rv = "" +today.get((Calendar.YEAR));
        if(today.get(Calendar.MONTH) < 10) {
            rv += "0";
        }
        rv += today.get(Calendar.MONTH);
        if(today.get(Calendar.DAY_OF_MONTH) < 10) {
            rv += "0";
        }
        rv += today.get(Calendar.DAY_OF_MONTH);
        if(today.get(Calendar.HOUR_OF_DAY) < 10) {
            rv += "0";
        }
        rv += today.get(Calendar.HOUR_OF_DAY);
        if(today.get(Calendar.MINUTE) < 10) {
            rv += "0";
        }
        rv += today.get(Calendar.MINUTE);
        return rv;
    }

    public static String getRandom() {
        String rv = "";
        for(int i = 0; i < 3; i++) {
            rv += (int)(Math.random()*10);
        }
        return rv;
    }

    public Log(Context context) {
        this.context = context;
        data = new ArrayList<>();
        filename = "log-" +getToday() +"-" +getRandom() +".txt";
    }

    public void update(File file) {
        data.clear();
        filename = file.getName();
        try {
            BufferedReader fileIn = new BufferedReader(new FileReader(file));
            String line = fileIn.readLine();
            while(line != null) {
                data.add(line);
                line = fileIn.readLine();
            }
            fileIn.close();
        } catch(IOException ex) {
            ex.printStackTrace(System.out);
            System.out.println(ex.getLocalizedMessage());
            //todo:JOptionPane.showMessageDialog(null, "Error reading log.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String getLabel() {
        return "Log: " +filename;
    }

    public void addText(String text) {
        data.add(text);
        save();
    }

    public void save() {
        try {
            PrintWriter outFile = new PrintWriter(context.openFileOutput(filename, Context.MODE_PRIVATE));
            outFile.println(getFixedText());
            outFile.close();
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
            System.out.println(ex.getLocalizedMessage());
            //todo:JOptionPane.showMessageDialog(null, "Error saving log.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String getFixedText() {
        String rv = "";
        for(String line : data) {
            if(rv.length() != 0) {
                rv += "\n";
            }
            rv += line;
        }
        return rv;
    }

    public String getFilename() {
        return filename;
    }

    public String makeNew() {
        if(!data.isEmpty()) {
            save();
        }
        data.clear();
        filename = "log-" +getToday() +"-" +getRandom() +".txt";
        return getLabel();
    }
}
