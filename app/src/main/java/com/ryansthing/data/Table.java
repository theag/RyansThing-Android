/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ryansthing.data;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author nbp184
 */
public class Table {
    
    private static final Random rand = new Random();
    
    public final String name;
    private final ArrayList<TableEntry> entries;
    
    private String text;
    private final ArrayList<String> rollon;
    
    public Table(String name) {
        this.name = name;
        entries = new ArrayList<>();
        rollon = new ArrayList<>();
    }
    
    public void addEntry(TableEntry te, int amount) {
        for(int i = 0; i < amount; i++) {
            entries.add(te);
        }
    }
    
    public void setText(String text) {
        this.text = text;
    }
    
    public void addRollon(String rollon) {
        this.rollon.add(rollon);
    }
    
    public String roll() {
        if(!entries.isEmpty()) {
            return entries.get(rand.nextInt(entries.size())).getText();
        } else {
            String rv = text;
            for(String t : rollon) {
                rv += " <" +t +">";
            }
            return rv;
        }
    }
    
    @Override
    public String toString() {
        return name;
    }
    
}
