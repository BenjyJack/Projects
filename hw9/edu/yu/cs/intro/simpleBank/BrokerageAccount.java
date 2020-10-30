package edu.yu.cs.intro.simpleBank;

import edu.yu.cs.intro.simpleBank.exceptions.InsufficientAssetsException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BrokerageAccount extends Account {
    private Map<String,Integer> stocksToNumberOfShares = new HashMap<String, Integer>();

    protected BrokerageAccount(long accountNumber) {
        super(accountNumber);
    }

    protected int getNumberOfShares(String stock){
        return stocksToNumberOfShares.get(stock);
    }
    /**
     * Buy the given amount of the given stock. Must have enough cash in the account to purchase them.
     * If there is enough cash, reduce cash and increase shares of the given stock
     * If there is not enough cash, throw an InsufficientAssetsException
     */
    protected void buyShares(String stock, int shares) throws InsufficientAssetsException {
        if(!stocksToNumberOfShares.containsKey(stock)) {
            stocksToNumberOfShares.put(stock, 0);
        }
        if((stocksToNumberOfShares.get(stock) * shares) + Bank.getBank().transactionFee > this.getAvailableBalance()){
            throw new InsufficientAssetsException();
        }
        int oldValue = stocksToNumberOfShares.get(stock);
        stocksToNumberOfShares.put(stock, oldValue+shares);
        withdrawCash((oldValue + shares) * Bank.getBank().getStockPrice(stock));
    }

    /**
     * Sell the given amount of the given stock. Must have enough shares in the account to sell.
     * If there are enough shares, reduce shares and increase cash.
     * If there are not enough shares, throw an InsufficientAssetsException
     */
    protected void sellShares(String stock, int shares) throws InsufficientAssetsException{
        int oldValue = stocksToNumberOfShares.get(stock);
        if(oldValue < shares){
            throw new InsufficientAssetsException();
        }
        stocksToNumberOfShares.put(stock, oldValue - shares);
        depositCash((shares) * Bank.getBank().getStockPrice(stock));
        withdrawCash(0);
    }

    /**
     * this method must return the total amount of cash + the total market value of all stocks owned.
     * The market value of a single stock is determined by multiplying the share price of the stock times the number of shares owned
     * @return
     */
    protected double getTotalBalance(){
        double total = 0.0;
        total += this.getAvailableBalance();
        Collection<Integer> value = stocksToNumberOfShares.values();
        for (int stock: value) {
            total += stock;
        }
        return total;
    }
}