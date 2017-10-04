/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ryansthing.data;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author nbp184
 */
public class ReadTable {
    
    private static final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    private static DocumentBuilder dBuilder = null;
	

    
    public static void read(InputStream file, ArrayList<Table> tables) {
        if(dBuilder == null) {
            try {
                dBuilder = dbFactory.newDocumentBuilder();
            } catch (ParserConfigurationException ex) {
                ex.printStackTrace(System.out);
                System.out.println(ex.getLocalizedMessage());
                throw new RuntimeException("ERROR");
            }
        }
        try {
            Document doc = dBuilder.parse(file);
            NodeList tableNodes = doc.getElementsByTagName("table");
            Table newTable;
            String text;
            String[] rollon;
            int amount, sides, modifier;
            String unit;
            int appears;
            Element ele;
            NodeList items;
            NodeList children;
            for(int i = 0; i < tableNodes.getLength(); i++)  {
                ele = (Element)tableNodes.item(i);
                newTable = new Table(ele.getElementsByTagName("name").item(0).getTextContent());
                items = ele.getElementsByTagName("item");
                for(int j = 0; j < items.getLength(); j++) {
                    ele = (Element)items.item(j);
                    children = ele.getElementsByTagName("text");
                    if(children.getLength() > 0) {
                        text = children.item(0).getTextContent();
                    } else {
                        text = null;
                    }
                    children = ele.getElementsByTagName("rollon");
                    if(children.getLength() > 0) {
                        rollon = new String[children.getLength()];
                        for(int k = 0; k < children.getLength(); k++) {
                            rollon[k] = children.item(k).getTextContent();
                        }
                    } else {
                        rollon = null;
                    }
                    children = ele.getElementsByTagName("dice");
                    if(children.getLength() > 0) {
                        amount = Integer.parseInt(((Element)children.item(0)).getElementsByTagName("amount").item(0).getTextContent());
                        sides = Integer.parseInt(((Element)children.item(0)).getElementsByTagName("sides").item(0).getTextContent());
                        modifier = Integer.parseInt(((Element)children.item(0)).getElementsByTagName("modifier").item(0).getTextContent());
                    } else {
                        amount = -1;
                        sides = -1;
                        modifier = -1;
                    }
                    children = ele.getElementsByTagName("unit");
                    if(children.getLength() > 0) {
                        unit = children.item(0).getTextContent();
                    } else {
                        unit = null;
                    }
                    children = ele.getElementsByTagName("appears");
                    if(children.getLength() > 0) {
                        appears = Integer.parseInt(children.item(0).getTextContent());
                    } else {
                        appears = 1;
                    }
                    if(amount == -1) {
                        newTable.addEntry(new TableEntry(text, rollon, null, unit), appears);
                    } else {
                        newTable.addEntry(new TableEntry(text, rollon, new Dice(amount, sides, modifier), unit), appears);
                    }
                }
                if(items.getLength() == 0) {
                    newTable.setText(ele.getElementsByTagName("text").item(0).getTextContent());
                    items = ele.getElementsByTagName("rollon");
                    for(int j = 0; j < items.getLength(); j++) {
                        newTable.addRollon(items.item(j).getTextContent());
                    }
                }
                tables.add(newTable);
            }
        } catch (SAXException | IOException ex) {
            ex.printStackTrace(System.out);
            System.out.println(ex.getLocalizedMessage());
            throw new RuntimeException("ERROR");
        }
        
    }
    
}
