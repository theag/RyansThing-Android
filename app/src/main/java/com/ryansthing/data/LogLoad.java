/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ryansthing.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author nbp184
 */
public class LogLoad {
    
    public static LogLoad loadLog(File file) throws IOException {
        Pattern itPattern = Pattern.compile("Turn \\d+, Round \\d+");
        Pattern jcPattern = Pattern.compile("\\w+ \\d+(, \\w+ \\d+){" +JournalCalendar.YEAR +"}");
        Matcher matcher;
        String itLine = null;
        String jcLine = null;
        
        BufferedReader fileIn = new BufferedReader(new FileReader(file));
        String line = fileIn.readLine();
        while(line != null) {
            matcher = itPattern.matcher(line);
            if(matcher.find()) {
                itLine = line;
            }
            matcher = jcPattern.matcher(line);
            if(matcher.find()) {
                jcLine = line;
            }
            line = fileIn.readLine();
        }
        fileIn.close();
        
        Pattern numberFinder = Pattern.compile("");
        LogLoad rv = new LogLoad();
        matcher = numberFinder.matcher(itLine);
        matcher.find();
        int value = Integer.parseInt(matcher.group());
        for(int i = 0; i < value; i++) {
            rv.initiativeTracker.addTurn();
        }
        matcher.find();
        value = Integer.parseInt(matcher.group());
        for(int i = 0; i < value; i++) {
            rv.initiativeTracker.addRound();
        }
        matcher = numberFinder.matcher(jcLine);
        for(int i = 0; i <= JournalCalendar.YEAR; i++) {
            matcher.find();
            value = Integer.parseInt(matcher.group());
            for(int j = 0; j < value; j++) {
                rv.journalCalendar.addTime(i);
            }            
        }
        return rv;
    }
    
    public JournalCalendar journalCalendar;
    public InitiativeTracker initiativeTracker;
    
    private LogLoad() {
        journalCalendar = new JournalCalendar();
        initiativeTracker = new InitiativeTracker();
    }
    
}
