/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ryansthing.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Random;

/**
 *
 * @author nbp184
 */
public class Dice implements Parcelable {
    
    private static final Random rand = new Random();
    
    private final int amount;
    private final int sides;
    private final int modifier;
    
    public Dice(int amount, int sides, int modifier) {
        this.amount = amount;
        this.sides = sides;
        this.modifier = modifier;
    }
    
    public int roll() {
        int result = modifier;
        for(int i = 0; i < amount; i++) {
            result += rand.nextInt(sides) + 1;
        }
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(amount);
        dest.writeInt(sides);
        dest.writeInt(modifier);
    }

    public static final Parcelable.Creator<Dice> CREATOR = new Creator<Dice>() {
        @Override
        public Dice createFromParcel(Parcel source) {
            return new Dice(source);
        }

        @Override
        public Dice[] newArray(int size) {
            return new Dice[size];
        }
    };

    private Dice(Parcel source) {
        amount = source.readInt();
        sides = source.readInt();
        modifier = source.readInt();
    }

    public int getAmount() {
        return amount;
    }

    public int getSides() {
        return sides;
    }

    public int getModifier() {
        return modifier;
    }
}
