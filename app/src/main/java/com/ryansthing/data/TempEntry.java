package com.ryansthing.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by nbp184 on 2017/10/12.
 */

public class TempEntry implements Parcelable {

    public String text;
    public Dice die;
    public String unit;
    public int appears;
    public ArrayList<String> rollon;

    public TempEntry() {
        appears = 1;
    }


    @Override
    public String toString() {
        String rv = "";
        if(text != null) {
            rv += text;
        }
        if(rollon != null) {
            if(!rv.isEmpty()) {
                rv += " ";
            }
            rv += "<" +rollon.get(0) +"> ";
            for(int i = 1; i < rollon.size(); i++) {
                rv += " <" +rollon.get(i) +">";
            }
        }
        if(die != null) {
            rv += die.roll() + " ";
        }
        if(unit != null) {
            rv += unit;
        }
        if(appears > 1) {
            rv += " (" +appears +")";
        }
        return rv;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(text);
        dest.writeTypedObject(die, flags);
        dest.writeString(unit);
        dest.writeInt(appears);
        dest.writeStringList(rollon);
    }

    public static final Parcelable.Creator<TempEntry> CREATOR = new Creator<TempEntry>() {
        @Override
        public TempEntry createFromParcel(Parcel source) {
            return new TempEntry(source);
        }

        @Override
        public TempEntry[] newArray(int size) {
            return new TempEntry[size];
        }
    };

    private TempEntry(Parcel source) {
        text = source.readString();
        die = source.readTypedObject(Dice.CREATOR);
        unit = source.readString();
        appears = source.readInt();
        rollon = new ArrayList<>();
        source.readStringList(rollon);
    }

}
