package edu.yu.cs.intro.bank;

import edu.yu.cs.intro.bank.exceptions.InsufficientAssetsException;
import edu.yu.cs.intro.bank.exceptions.UnauthorizedActionException;

public class BrokerageAccount extends Account {
    //DONE
    private double transactionFee = 3.00;
    private double cash;
    /**an array of stocks owned*/
    private Bank.Stock[] stocksOwned = new Bank.Stock[1];
    /**the number of shares of each stock owned by this account*/
    private int[] numberOfShares = new int[1];

    protected BrokerageAccount(long accountNumber, Patron patron) {
        super(accountNumber,patron);
    }


    protected int getNumberOfShares(Bank.Stock stock){
        for (int i = 0; i < this.stocksOwned.length; i++) {
            if (stock.equals(stocksOwned[i])){
                return numberOfShares[i];
            }
        }
        return 0;
    }

    /**
     * Buy the given amount of the given stock. Must have enough cash in the account to purchase them.
     * If there is enough cash, reduce cash and increase shares of the given stock
     * If there is not enough cash, throw an InsufficientAssetsException
     */
    protected void buyShares(Bank.Stock stock, int shares) throws InsufficientAssetsException {
        double price = stock.getSharePrice() * shares + transactionFee;
        if (price <= cash){
            for (int i = 0; i < stocksOwned.length; i++) {
                if(stocksOwned[i] == null){
                    stocksOwned[i] = stock;
                    numberOfShares[i] += shares;
                    break;
                }
                else if (stock.equals(stocksOwned[i])){
                        numberOfShares[i] += shares;
                        break;
                }
                else if(i == stocksOwned.length - 1){
                    stocksOwned = arrayDoubler(stocksOwned);
                    numberOfShares = arrayDoublerAgain(numberOfShares);
                    stocksOwned[i+1] = stock;
                    numberOfShares[i+1] += shares;
                    break;
                }
            }
            cash -= price;
        }
        else{
            throw new InsufficientAssetsException();
        }
    }

    private Bank.Stock[] arrayDoubler(Bank.Stock[] array){
        Bank.Stock[] newArray = new Bank.Stock[array.length * 2];
        System.arraycopy(array, 0, newArray, 0, array.length);
        return newArray;
    }

    private int[] arrayDoublerAgain(int[] array){
        int[] newArray = new int[array.length * 2];
        System.arraycopy(array, 0, newArray, 0, array.length);
        return newArray;
    }

    /**
     * Sell the given amount of the given stock. Must have enough shares in the account to sell.
     * If there are enough shares, reduce shares and increase cash.
     * If there are not enough shares, throw an InsufficientAssetsException
     */
    protected void sellShares(Bank.Stock stock, int shares) throws InsufficientAssetsException, UnauthorizedActionException{
        double value = (stock.getSharePrice() * shares) - transactionFee;
        for (int i = 0; i < this.stocksOwned.length; i++) {
            if (stock.equals(this.stocksOwned[i])){
                if (numberOfShares[i] >= shares){
                    numberOfShares[i] -= shares;
                    this.cash += value;
                    break;
                }
                else{
                    throw new InsufficientAssetsException();
                }
            }
            else{
                if (i == this.stocksOwned.length - 1){
                    throw new UnauthorizedActionException();
                }
            }
        }
    }

    @Override
    protected void depositCash(double amount) {
        this.cash += amount;
    }

    @Override
    protected void withdrawCash(double amount) throws InsufficientAssetsException {
        if (amount <= cash){
            this.cash -= amount;
        }
        else{
            throw new InsufficientAssetsException();
        }
    }

    /**
     * this method must return the total amount of cash + the total market value of all stocks owned.
     * The market value of a single stock is determined by multiplying the share price of the stock times the number of shares owned
     * @return
     */
    @Override
    protected double getTotalBalance(){
        double totalCash = this.cash;
        for (int i = 0; i < this.stocksOwned.length; i++) {
            totalCash += (stocksOwned[i].getSharePrice() * numberOfShares[i]);
            if(stocksOwned[i] == null){
                break;
            }
        }
        return totalCash;
    }

    /**
     * returns the amount of cash in the account
     */
    @Override
    protected double getAvailableBalance() {
        return this.cash;
    }
}