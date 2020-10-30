package edu.yu.cs.intro.simpleBank;

import edu.yu.cs.intro.simpleBank.exceptions.InsufficientAssetsException;

public class Account {
    private final long accountNumber;
    private double cash;

    protected Account(long accountNumber){
        this.accountNumber = accountNumber;
    }
    protected long getAccountNumber() {
        return this.accountNumber;
    }
    protected double getAvailableBalance(){
        return this.cash;
    }
    //*************************************************
    //below are methods you must complete inside this class
    //*************************************************
    protected void depositCash(double amount){
        cash += amount;
    }
    protected void withdrawCash(double amount) throws InsufficientAssetsException{
        if((amount + Bank.getBank().transactionFee) > cash){
            throw new InsufficientAssetsException();
        }
        cash -= (amount + Bank.getBank().transactionFee);
    }
}