package edu.yu.cs.intro.bank;

import edu.yu.cs.intro.bank.exceptions.InsufficientAssetsException;
import edu.yu.cs.intro.bank.exceptions.NoSuchAccountException;
import edu.yu.cs.intro.bank.exceptions.UnauthorizedActionException;

public class Transfer extends Transaction {
    //DONE
    protected final Account source;

    /**
     * in addition to the checks done by the super constructor, you must check that the
     * patron is an owner of the source account
     */
    public Transfer(double amount, Account source, Account target, Patron patron) throws NoSuchAccountException, InsufficientAssetsException, IllegalArgumentException, UnauthorizedActionException {
        super(amount,target,patron);
        this.source = source;
    }
    public Account getSource() {return this.source;}
    protected long time = 0;
    @Override
    public long getTime() {
        return this.time;
    }
    @Override
    public double getAmount(){
        return this.amount;
    }

    @Override
    public Account getTarget() {
        return this.target;
    }

    @Override
    public Patron getPatron() {
        return this.patron;
    }

    /**
     * Check that there us enough cash in the source account. If so, transfer from source to target and set the transaction time.
     * If not, throw InsufficientAssetsException
     */
    @Override
    public void execute() throws InsufficientAssetsException {
        try{
            this.source.withdrawCash(amount);
            this.target.depositCash(amount);
        } catch (InsufficientAssetsException e){
            throw new InsufficientAssetsException();
        }
        time = System.currentTimeMillis();
    }
}
