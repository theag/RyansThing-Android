/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ryansthing.data;

import java.util.StringTokenizer;

/**
 *
 * @author nbp184
 */
public class TableEntry {
    
    private final String text;
    private final String rollon;
    private final Dice die;
    private final String unit;
    
    public TableEntry(String text, String rollon, Dice die, String unit) {
        this.text = text;
        this.rollon = rollon;
        this.die = die;
        this.unit = unit;
    }

    public String getText() {
        String rv = "";
        if(text != null) {
            rv += text;
        }
        if(rollon != null) {
            if(!rv.isEmpty()) {
                rv += " ";
            }
            StringTokenizer tokens = new StringTokenizer(rollon, ",");
            rv += "<" +tokens.nextToken() +"> ";
            while(tokens.hasMoreTokens()) {
                rv += "and <" +tokens.nextToken() +"> ";
            }
        }
        if(die != null) {
            rv += die.roll() + " ";
        }
        if(unit != null) {
            rv += unit;
        }
        return rv;
    }
    
}
