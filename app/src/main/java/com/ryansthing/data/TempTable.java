package com.ryansthing.data;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by nbp184 on 2017/10/12.
 */

public class TempTable implements Parcelable {

    public String name;
    private boolean hasItems;
    public String text;
    public final ArrayList<String> rollon;
    public final ArrayList<TempEntry> entries;

    public TempTable() {
        name = "New Table";
        hasItems = true;
        text = null;
        rollon = new ArrayList<>();
        entries = new ArrayList<>();
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean getHasItems() {
        return hasItems;
    }

    public void setHasItems(boolean hasItems) {
        if(this.hasItems != hasItems) {
            this.hasItems = hasItems;
            if(hasItems) {
                text = null;
                rollon.clear();
            } else {
                entries.clear();
            }
        }
    }

    public void addEntry(String text, String[] rollon, Dice dice, String unit, int appears) {
        TempEntry entry = new TempEntry();
        entry.text = text;
        entry.rollon.addAll(Arrays.asList(rollon));
        entry.die = dice;
        entry.unit = unit;
        entry.appears = appears;
        entries.add(entry);
    }

    public void setText(String text) {
        this.text = text;
    }

    public void addRollon(String text) {
        rollon.add(text);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        if(hasItems) {
            dest.writeByte((byte)1);
            dest.writeTypedList(entries);
        } else {
            dest.writeByte((byte)0);
            dest.writeString(text);
            dest.writeStringList(rollon);
        }
    }

    public static final Parcelable.Creator<TempTable> CREATOR = new Creator<TempTable>() {
        @Override
        public TempTable createFromParcel(Parcel source) {
            return new TempTable(source);
        }

        @Override
        public TempTable[] newArray(int size) {
            return new TempTable[size];
        }
    };

    private TempTable(Parcel source) {
        rollon = new ArrayList<>();
        entries = new ArrayList<>();
        name = source.readString();
        hasItems = source.readByte() == 1;
        if(hasItems) {
            source.readTypedList(entries, TempEntry.CREATOR);
        } else {
            text = source.readString();
            source.readStringList(rollon);
        }
    }

}
