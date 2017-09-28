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
public class InitiativeTracker {
    
    private int round;
    private int turn;
    
    public InitiativeTracker() {
        round = 1;
        turn = 1;
    }
    
    public void addRound() {
        round += 1;
    }
    
    public void addTurn() {
        turn += 1;
        round = 1;
    }
    
    @Override
    public String toString() {
        return "Turn " +turn +", Round " +round;
    }
    
}
