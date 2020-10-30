package edu.yu.cs.intro.bank;

import edu.yu.cs.intro.bank.exceptions.InsufficientAssetsException;
import edu.yu.cs.intro.bank.exceptions.NoSuchAccountException;
import edu.yu.cs.intro.bank.exceptions.UnauthorizedActionException;

public class Deposit extends Transaction {

    protected Deposit(double amount, Account target, Patron patron) throws NoSuchAccountException, InsufficientAssetsException, UnauthorizedActionException {
        super(amount, target, patron);

    }
    protected long time = 0;
    @Override
    protected long getTime() {
        return this.time;
    }

    @Override
    protected double getAmount() {
        return this.amount;
    }

    @Override
    protected Account getTarget() {
        return this.target;
    }

    @Override
    protected Patron getPatron() {
        return this.patron;
    }

    /**
     * Deposit the cash into the account by increasing the accounts cash amount.
     * Set the time of transaction execution.
     */
    @Override
    protected void execute() throws InsufficientAssetsException {
        this.target.depositCash(this.amount);
        time = System.currentTimeMillis();
    }
}
