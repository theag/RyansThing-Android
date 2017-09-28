/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ryansthing.data;

/**
 *
 * @author nbp184
 */
public class JournalCalendar {
    
    public final static int HOUR = 0;
    public final static int DAY = 1;
    public final static int WEEK = 2;
    public final static int SEASON = 3;
    public final static int YEAR = 4;
    
    private final static int conversions[] = {24, 7, 13, 4};
    private final static String units[] = {"Hour", "Day", "Week", "Season", "Year"};
    
    private final int time[]; //hours, days, weeks, seasons, years
    
    public JournalCalendar() {
        time = new int[5];
        for(int i = 0; i < time.length; i++) {
            time[i] = 1;
        }
    }
    
    public void addTime(int timeCode) {
        time[timeCode]++;
        for(int i = timeCode - 1; i >= 0; i--) {
            time[i] = 1;
        }
        doCheck();
    }
    
    @Override
    public String toString() {
        String rv = units[0] +" " +time[0];
        for(int i = 1; i < time.length; i++) {
            rv += ", " +units[i] +" " +time[i];
        }
        return rv;
    }

    private void doCheck() {
        for(int i = 0; i < time.length-1; i++) {
            if(time[i] > conversions[i]) {
                time[i] -= conversions[i];
                time[i+1]++;
            }
        }
    }
    
}
