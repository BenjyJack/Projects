package edu.yu.cs.intro.bank;


import edu.yu.cs.intro.bank.exceptions.AuthenticationException;
import edu.yu.cs.intro.bank.exceptions.InsufficientAssetsException;
import edu.yu.cs.intro.bank.exceptions.NoSuchAccountException;
import edu.yu.cs.intro.bank.exceptions.UnauthorizedActionException;

public abstract class Transaction {
    protected final double amount;
    protected final Account target;
    protected final Patron patron;
    /**
     * Creates a transaction.
     * Must check the following:
     * 1) that amount is positive (if not, throw IllegalArgumentException)
     * 2) that the Account actually exists in the bank (if not, throw NoSuchAccountException)
     * 3) that the Patron actually exists in the bank (if not, throw UnauthorizedActionException)
     * 4) that the Patron is an owner of the given account (if not, throw UnauthorizedActionException)
     */

    protected Transaction(double amount, Account target, Patron patron) throws NoSuchAccountException, InsufficientAssetsException, UnauthorizedActionException, IllegalArgumentException {
        this.amount = amount;
        this.target = target;
        this.patron = patron;
        if (amount < 0) {
            throw new IllegalArgumentException();
        }
        try {
            if(target instanceof SavingsAccount){
            Bank.getBank().checkBalanceSavings(patron.getSocialSecurityNumber(), patron.getUserName(), patron.getPassword());}
        } catch (UnauthorizedActionException e) {
            throw new UnauthorizedActionException();
        } catch (AuthenticationException e) {
        }
        try {
            if(target instanceof SavingsAccount) {
                this.patron.getAccount(patron.getSavingsAccount().getAccountNumber());
            }
            else{
                this.patron.getAccount(patron.getBrokerageAccount().getAccountNumber());
            }
        } catch (NoSuchAccountException e) {
            throw new NoSuchAccountException();
        }
        try{
            patron.getAccount(target.getAccountNumber());
        }catch (NoSuchAccountException e){
            throw new UnauthorizedActionException();
        }
    }


    /**time the tx took place, from System.currentTimeMillis()*/
    protected abstract long getTime();
    protected abstract double getAmount();
    protected abstract Account getTarget();
    protected abstract Patron getPatron();
    /**executes the transaction, and sets the transaction time*/
    protected abstract void execute() throws InsufficientAssetsException, UnauthorizedActionException;
}