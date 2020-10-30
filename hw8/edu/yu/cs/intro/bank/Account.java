package edu.yu.cs.intro.bank;

import edu.yu.cs.intro.bank.exceptions.InsufficientAssetsException;

import java.util.Arrays;

public abstract class Account {
    //DONE
    private final long accountNumber;
    private Patron patron;
    private Transaction[] txHistory = new Transaction[1];
    protected double cash;

    protected Account(long accountNumber, Patron patron){
        this.accountNumber = accountNumber;
        this.patron = patron;
    }
    protected long getAccountNumber() {
        return this.accountNumber;
    }
    protected Patron getPatron() {
        return this.patron;
    }
    /**
     * returns a copy of the txHistory array
     * why do you think we return a copy and not the original array?
     */
    protected Transaction[] getTransactionHistory(){
        return Arrays.copyOf(this.txHistory, this.txHistory.length);
    }

    //*************************************************
    //below are methods you must complete in this class
    //*************************************************

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Account)) {
            return false;
        }
        Account other = (Account) obj;
        if (this.accountNumber == (other.accountNumber) && this.patron.equals(other.patron)) {
            return true;
        }
        return false;
    }
    /**add a tx to the tx history of this account*/
    protected void addTransactionToHistory(Transaction tx){
        for (int i = 0; i < txHistory.length; i++) {
            if (txHistory[i] == null){
                txHistory[i] = tx;
                break;
            }
            else if (i == txHistory.length - 1){
                txHistory = arrayDoubler(txHistory);
                txHistory[i + 1] = tx;
                break;
            }
        }
    }

    private Transaction[] arrayDoubler(Transaction[] array){
        Transaction[] newArray = new Transaction[array.length * 2];
        for (int i = 0; i < array.length; i++){
            newArray[i] = array[i];
        }
        return newArray;
    }
    //*********************************************************
    //below are abstract methods that subclasses must implement
    //*********************************************************
    protected void depositCash(double amount){
        if(amount < 0){
            throw new IllegalArgumentException("can't deposit negative cash");
        }
        this.cash += amount;
    }
    //*************************************************
    //below are methods you must complete in this class
    //*************************************************
    /**add a tx to the tx history of this account*/
    protected void withdrawCash(double amount) throws InsufficientAssetsException{}

    //*********************************************************
    //below are abstract methods that subclasses must implement
    //*********************************************************
    protected abstract double getTotalBalance();
    protected abstract double getAvailableBalance();
}