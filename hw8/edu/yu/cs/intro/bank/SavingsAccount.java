package edu.yu.cs.intro.bank;

import edu.yu.cs.intro.bank.exceptions.InsufficientAssetsException;

public class SavingsAccount extends Account {
    //DONE
    private double cash;

    protected SavingsAccount(long accountNumber, Patron patron) {
        super(accountNumber,patron);
    }

    @Override
    protected void depositCash(double amount) {
        cash += amount;
    }
    @Override
    protected void withdrawCash(double amount) throws InsufficientAssetsException {
        if(amount <= cash){
            cash -= amount;
        }
        else {
            throw new InsufficientAssetsException();
        }
    }
    @Override
    protected double getTotalBalance() {
        return this.cash;
    }
    @Override
    protected double getAvailableBalance() {
        return this.cash;

    }
}