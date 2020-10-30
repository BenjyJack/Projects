package edu.yu.cs.intro.simpleBank;

import edu.yu.cs.intro.simpleBank.exceptions.*;

import java.util.*;

public class Bank {
    private Set<Patron> bankPatrons = new HashSet<Patron>();
    private Map<String,Double> stocksSymbolToPrice = new HashMap<String, Double>();
    /**
     * transaction history is no longer stored in each patron object. Instead, the bank maintains a Map of transactions, mapping each Patron to the List of transactions that the given patron has executed.
     */
    private Map<Patron,List<Transaction>> txHistoryByPatron = new HashMap<Patron, List<Transaction>>();
    protected final double transactionFee;
    private static Bank INSTANCE;
    public Bank(double transactionFee){
        this.transactionFee = transactionFee;
        INSTANCE = this;
        //initialize your collections here
    }
    public static Bank getBank(){
        return INSTANCE;
    }
    public static void main(String[] args){
        Bank bank = new Bank(17.30);
        String firstName1 = "first";
        String firstName2 = "second";
        String firstName3 = "third";
        String firstName4 = "fourth";
        String firstName5 = "fifth";
        String firstName6 = "sixth";
        String firstName7 = "seventh";
        String firstName8 = "eighth";
        String firstName9 = "ninth";
        String firstName10 = "tenth";

        String lastName1 = "first";
        String lastName2 = "second";
        String lastName3 = "third";
        String lastName4 = "fourth";
        String lastName5 = "fifth";
        String lastName6 = "sixth";
        String lastName7 = "seventh";
        String lastName8 = "eighth";
        String lastName9 = "ninth";
        String lastName10 = "tenth";

        String userName1 = "first";
        String userName2 = "second";
        String userName3 = "third";
        String userName4 = "fourth";
        String userName5 = "fifth";
        String userName6 = "sixth";
        String userName7 = "seventh";
        String userName8 = "eighth";
        String userName9 = "ninth";
        String userName10 = "tenth";

        String password1 = "first";
        String password2 = "second";
        String password3 = "third";
        String password4 = "fourth";
        String password5 = "fifth";
        String password6 = "sixth";
        String password7 = "seventh";
        String password8 = "eighth";
        String password9 = "ninth";
        String password10 = "tenth";

        long socialSecurityNumber1 = 1;
        long socialSecurityNumber2 = 2;
        long socialSecurityNumber3 = 3;
        long socialSecurityNumber4 = 4;
        long socialSecurityNumber5 = 5;
        long socialSecurityNumber6 = 6;
        long socialSecurityNumber7 = 7;
        long socialSecurityNumber8 = 8;
        long socialSecurityNumber9 = 9;
        long socialSecurityNumber10 = 10;

        String tickerSymbol1 = "one";
        String tickerSymbol2 = "two";

        double sharePrice1 = 1.1;
        double sharePrice2 = 2.2;

        try {
            bank.createNewPatron(firstName1, lastName1, socialSecurityNumber1, userName1, password1);
            bank.createNewPatron(firstName2, lastName2, socialSecurityNumber2, userName2, password2);
            bank.createNewPatron(firstName3, lastName3, socialSecurityNumber3, userName3, password3);
            bank.createNewPatron(firstName4, lastName4, socialSecurityNumber4, userName4, password4);
            bank.createNewPatron(firstName5, lastName5, socialSecurityNumber5, userName5, password5);
            bank.createNewPatron(firstName6, lastName6, socialSecurityNumber6, userName6, password6);
            bank.createNewPatron(firstName7, lastName7, socialSecurityNumber7, userName7, password7);
            bank.createNewPatron(firstName8, lastName8, socialSecurityNumber8, userName8, password8);
            bank.createNewPatron(firstName9, lastName9, socialSecurityNumber9, userName9, password9);
            bank.createNewPatron(firstName10, lastName10, socialSecurityNumber10, userName10, password10);
            bank.addNewStockToMarket(tickerSymbol1, sharePrice1);
            bank.addNewStockToMarket(tickerSymbol2, sharePrice2);
            bank.openBrokerageAccount(socialSecurityNumber1, userName1, password1);
            bank.openSavingsAccount(socialSecurityNumber2, userName2, password2);
            bank.openSavingsAccount(socialSecurityNumber3, userName3, password3);
            bank.openBrokerageAccount(socialSecurityNumber3, userName3, password3);
            bank.depositCashIntoSavings(socialSecurityNumber3, userName3, password3, 34);
            bank.openSavingsAccount(socialSecurityNumber5, userName5, password5);
            bank.openBrokerageAccount(socialSecurityNumber5, userName5, password5);
            bank.depositCashIntoSavings(socialSecurityNumber5, userName5, password5, 1000);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Set up issue");
        }
    }
    /**
     * Lists a new stock with the given symbol at the given price
     * @return false if the stock was previously listed, true if it was added as a result of this call
     */
    protected boolean addNewStockToMarket(String tickerSymbol, double sharePrice){
        if(stocksSymbolToPrice.containsKey(tickerSymbol)){
            return false;
        }
        //if the stock is already listed, return false
        //otherwise, add the key-value pair to the stocksSymbolToPrice map and return true;
        stocksSymbolToPrice.put(tickerSymbol, sharePrice);
        return true;
    }
    /**
     * @return the stock price for the given stock ticker symbol. Return 0 if there is no such stock.
     */
    public double getStockPrice(String symbol){
        return stocksSymbolToPrice.get(symbol);
    }
    /**
     * @return a set the stock ticker symbols listed in this bank
     */
    public Set<String> getAllStockTickerSymbols(){
        return stocksSymbolToPrice.keySet();
    }
    /**
     * @return the total number of shares of the given stock owned by all patrons combined
     * if there is no such Stock or if the tickerSymbol is empty or null, return 0
     */
    public int getNumberOfOutstandingShares(String tickerSymbol){
        if(!stocksSymbolToPrice.containsKey(tickerSymbol)){
            return 0;
        }
        int total = 0;
        for (Patron patron: bankPatrons) {
            total += patron.getBrokerageAccount().getNumberOfShares(tickerSymbol);
        }
        return total;
    }
    /**
     * @return the total number of shares of the given stock owned by all patrons combined multiplied by the price per share
     * if there is no such Stock or if the tickerSymbol is empty or null, return 0
     */
    public int getMarketCapitalization(String tickerSymbol){
       int shares = getNumberOfOutstandingShares(tickerSymbol);
       shares *= stocksSymbolToPrice.get(tickerSymbol);
       int casted = (int) shares;
       return casted;
    }
    /**
     * @return all the cash in all savings accounts added up
     */
    public double getTotalSavingsInBank(){
        double total = 0;
        for (Patron patron: bankPatrons) {
            total += patron.getSavingsAccount().getAvailableBalance();
        }
        return total;
    }
    /**
     * @return all the cash in all brokerage accounts added up
     */
    public double getTotalBrokerageCashInBank(){
        double total = 0;
        for (Patron patron: bankPatrons) {
            total += patron.getBrokerageAccount().getAvailableBalance();
        }
        return 0;
    }
    /**
     * Creates a new Patron in the bank.
     */
    public void createNewPatron(String firstName, String lastName, long socialSecurityNumber, String userName, String password){
        Patron newbie = new Patron(firstName, lastName, socialSecurityNumber, userName, password);
        bankPatrons.add(newbie);
        List<Transaction> list = new ArrayList<Transaction>();
        txHistoryByPatron.put(newbie, list);
    }
    /**
     * @return the account number of the opened account
     */
    public long openSavingsAccount( long socialSecurityNumber, String userName, String password) {
        long account = (long) (Math.random() * 584902001);
        Account savings = new Account(account);
        try {
            getPatron(socialSecurityNumber).addAccount(savings);
        } catch (AuthenticationException e){}
        return account;
    }
    /**
     * @return the account number of the opened account
     */
    public long openBrokerageAccount(long socialSecurityNumber, String userName, String password) {
        long account = (long) (Math.random() * 584902001);
        BrokerageAccount brokerage = new BrokerageAccount(account);
        try {
            getPatron(socialSecurityNumber).addAccount(brokerage);
        } catch (AuthenticationException e){}
        return account;
    }
    /**
     * Deposit cash into the given savings account
     */
    public void depositCashIntoSavings(long socialSecurityNumber, String userName, String password, double amount) throws AuthenticationException{
        if(security(socialSecurityNumber, userName, password)){
            throw new AuthenticationException();
        }
        try {
            Transaction deposit = new Transaction(getPatron(socialSecurityNumber), Transaction.TRANSACTION_TYPE.DEPOSIT, getPatron(socialSecurityNumber).getSavingsAccount(), amount);
            deposit.execute();
            txHistoryByPatron.get(getPatron(socialSecurityNumber)).add(deposit);
        } catch (AuthenticationException | UnauthorizedActionException | InsufficientAssetsException e){
            throw new AuthenticationException();
        }
    }
    protected Patron getPatron(long socialSecurityNumber) throws AuthenticationException{
        for (Patron patron: bankPatrons) {
            if(patron.getSocialSecurityNumber() == socialSecurityNumber){
                return patron;
            }
        }
        throw new AuthenticationException();
    }
    private boolean security (long socialSecurityNumber, String userName, String password){
        for (Patron patron: bankPatrons) {
            if((socialSecurityNumber == patron.getSocialSecurityNumber()) && (!(userName.equals(patron.getUserName())) || !(password.equals(patron.getPassword())))){
                return true;
            }
            if((userName.equals(patron.getUserName())) && (socialSecurityNumber != patron.getSocialSecurityNumber() || !(password.equals(patron.getPassword())))){
                return true;
            }
            if((password.equals(patron.getPassword())) && (socialSecurityNumber != patron.getSocialSecurityNumber() || !(userName.equals(patron.getUserName())))){
                return true;
            }
        }
        for (Patron patron: bankPatrons) {
            if(patron.getSocialSecurityNumber() == socialSecurityNumber){
                return false;
            }
        }
        return true;
    }
    /**
     * withdraw cash from the patron's savings account
     * throw AuthenticationException if the SS#, username, and password don't match a bank patron
     * throw UnauthorizedActionException if the given patron does not have a savings account
     * throw InsufficientAssetsException if that amount of money is not present the savings account
     */
    public void withdrawCashFromSavings(long socialSecurityNumber, String userName, String password, double amount) throws AuthenticationException,UnauthorizedActionException,InsufficientAssetsException{
        if(security(socialSecurityNumber, userName, password)){
            throw new AuthenticationException();
        }
        if(getPatron(socialSecurityNumber).getSavingsAccount() == null){
            throw new UnauthorizedActionException();
        }
        try{
            Transaction withdraw = new Transaction(getPatron(socialSecurityNumber), Transaction.TRANSACTION_TYPE.WITHDRAW, getPatron(socialSecurityNumber).getSavingsAccount(), amount);
            withdraw.execute();
            txHistoryByPatron.get(getPatron(socialSecurityNumber)).add(withdraw);
        } catch (InsufficientAssetsException e){
            throw new InsufficientAssetsException();
        }
    }
    /**
     * withdraw cash from the patron's brokerage account
     * throw AuthenticationException if the SS#, username, and password don't match a bank patron
     * throw UnauthorizedActionException if the given patron does not have a brokerage account
     * throw InsufficientAssetsException if that amount of CASH is not present the brokerage account
     */
    public void withdrawCashFromBrokerage(long socialSecurityNumber, String userName, String password, double amount) throws AuthenticationException,UnauthorizedActionException,InsufficientAssetsException{
        if(security(socialSecurityNumber, userName, password)){
            throw new AuthenticationException();
        }
        if(getPatron(socialSecurityNumber).getBrokerageAccount() == null){
            throw new UnauthorizedActionException();
        }
        try{
            Transaction withdraw = new Transaction(getPatron(socialSecurityNumber), Transaction.TRANSACTION_TYPE.WITHDRAW, getPatron(socialSecurityNumber).getBrokerageAccount(), amount);
            withdraw.execute();
            txHistoryByPatron.get(getPatron(socialSecurityNumber)).add(withdraw);
        } catch (InsufficientAssetsException e){
            throw new InsufficientAssetsException();
        }
    }
    /**
     * check how much cash the patron has in his brokerage account
     * throw AuthenticationException if the SS#, username, and password don't match a bank patron
     * throw UnauthorizedActionException if the given patron does not have a brokerage account
     */
    public double checkCashInBrokerage(long socialSecurityNumber, String userName, String password) throws AuthenticationException,UnauthorizedActionException{
        if(security(socialSecurityNumber, userName, password)){
            throw new AuthenticationException();
        }
        if(getPatron(socialSecurityNumber).getBrokerageAccount() == null){
            throw new UnauthorizedActionException();
        }
        return getPatron(socialSecurityNumber).getBrokerageAccount().getAvailableBalance();
    }
    /**
     * check the total value of the patron's brokerage account
     * throw AuthenticationException if the SS#, username, and password don't match a bank patron
     * throw UnauthorizedActionException if the given patron does not have a brokerage account
     */
    public double checkTotalBalanceBrokerage(long socialSecurityNumber, String userName, String password) throws AuthenticationException,UnauthorizedActionException{
        if(security(socialSecurityNumber, userName, password)){
            throw new AuthenticationException();
        }
        if(getPatron(socialSecurityNumber).getBrokerageAccount() == null){
            throw new UnauthorizedActionException();
        }
        return getPatron(socialSecurityNumber).getBrokerageAccount().getTotalBalance();
    }
    /**
     * check how much cash the patron has in his savings account
     * throw AuthenticationException if the SS#, username, and password don't match a bank patron
     * throw UnauthorizedActionException if the given patron does not have a savings account
     */
    public double checkBalanceSavings(long socialSecurityNumber, String userName, String password) throws AuthenticationException,UnauthorizedActionException{
        if(security(socialSecurityNumber, userName, password)){
            throw new AuthenticationException();
        }
        if(getPatron(socialSecurityNumber).getSavingsAccount() == null){
            throw new UnauthorizedActionException();
        }
        return getPatron(socialSecurityNumber).getSavingsAccount().getAvailableBalance();
    }
    /**
     * buy shares of the given stock
     * throw AuthenticationException if the SS#, username, and password don't match a bank patron
     * throw UnauthorizedActionException if the given patron does not have a brokerage account
     * throw InsufficientAssetsException if the required amount of CASH is not present in the brokerage account
     */
    public void purchaseStock(long socialSecurityNumber, String userName, String password, String tickerSymbol, int shares) throws AuthenticationException,UnauthorizedActionException,InsufficientAssetsException{
        if(security(socialSecurityNumber, userName, password)){
            throw new AuthenticationException();
        }
        if(getPatron(socialSecurityNumber).getBrokerageAccount() == null){
            throw new UnauthorizedActionException();
        }
        try{
            Transaction purchase = new Transaction(getPatron(socialSecurityNumber), Transaction.TRANSACTION_TYPE.BUYSTOCK, getPatron(socialSecurityNumber).getBrokerageAccount(), shares);
            purchase.setStockSymbol(tickerSymbol);
            purchase.execute();
            txHistoryByPatron.get(getPatron(socialSecurityNumber)).add(purchase);
        } catch (InsufficientAssetsException e){
            throw new InsufficientAssetsException();
        }
    }
    /**
     * sell shares of the given stock
     * throw AuthenticationException if the SS#, username, and password don't match a bank patron
     * throw UnauthorizedActionException if the given patron does not have a brokerage account
     * throw InsufficientAssetsException if the patron does not have the given number of shares of the given stock
     */
    public void sellStock(long socialSecurityNumber, String userName, String password, String tickerSymbol, int shares) throws AuthenticationException,UnauthorizedActionException,InsufficientAssetsException{
        if(security(socialSecurityNumber, userName, password)){
            throw new AuthenticationException();
        }
        if(getPatron(socialSecurityNumber).getBrokerageAccount() == null){
            throw new UnauthorizedActionException();
        }
        try{
            Transaction sale = new Transaction(getPatron(socialSecurityNumber), Transaction.TRANSACTION_TYPE.SELLSTOCK, getPatron(socialSecurityNumber).getBrokerageAccount(), shares);
            sale.setStockSymbol(tickerSymbol);
            sale.execute();
            txHistoryByPatron.get(getPatron(socialSecurityNumber)).add(sale);
        } catch (InsufficientAssetsException e){
            throw new InsufficientAssetsException();
        }
    }
    /**
     * check the net worth of the patron
     * throw AuthenticationException if the SS#, username, and password don't match a bank patron
     * return 0 if the patron doesn't have any accounts
     */
    public double getNetWorth(long socialSecurityNumber, String userName, String password) throws AuthenticationException{
        return getPatron(socialSecurityNumber).getNetWorth();
    }
    /**
     * Get the transaction history on all of the patron's accounts, i.e. the transaction histories of both the savings account and
     * brokerage account (whichever of the two exist), combined. The merged history should be sorted in time order, from oldest to newest.
     * If the patron has no transactions in his history, return an array of length 0.
     * throw AuthenticationException if the SS#, username, and password don't match a bank patron
     */
    public Transaction[] getTransactionHistory(long socialSecurityNumber, String userName, String password) throws AuthenticationException{
        return (Transaction[]) (txHistoryByPatron.get(getPatron(socialSecurityNumber))).toArray();
    }
    /**
     * transfer cash from the patron's savings account to his brokerage account
     * throws AuthenticationException if the SS#, username, and password don't match a bank patron
     * throws UnauthorizedActionException if the given patron does not have both a savings account and a brokerage account
     * throws InsufficientAssetsException if that amount of money is not present in the savings account
     */
    public void transferFromSavingsToBrokerage(long socialSecurityNumber, String userName, String password, double amount) throws AuthenticationException,UnauthorizedActionException, InsufficientAssetsException {
        if(security(socialSecurityNumber, userName, password)){
            throw new AuthenticationException();
        }
        if(getPatron(socialSecurityNumber).getSavingsAccount() == null || getPatron(socialSecurityNumber).getBrokerageAccount() == null){
            throw new UnauthorizedActionException();
        }
        try{
            Transaction withdraw = new Transaction(getPatron(socialSecurityNumber), Transaction.TRANSACTION_TYPE.WITHDRAW, getPatron(socialSecurityNumber).getSavingsAccount(), amount);
            withdraw.execute();
            txHistoryByPatron.get(getPatron(socialSecurityNumber)).add(withdraw);
            Transaction deposit = new Transaction(getPatron(socialSecurityNumber), Transaction.TRANSACTION_TYPE.DEPOSIT, getPatron(socialSecurityNumber).getBrokerageAccount(), amount);
            deposit.execute();
            txHistoryByPatron.get(getPatron(socialSecurityNumber)).add(deposit);
        } catch (InsufficientAssetsException e){
            throw new InsufficientAssetsException();
        }
    }
    /**
     * transfer cash from the patron's savings account to his brokerage account
     * throws AuthenticationException if the SS#, username, and password don't match a bank patron
     * throws UnauthorizedActionException if the given patron does not have both a savings account and a brokerage account
     * throws InsufficientAssetsException if that amount of money is not present in CASH in the brokerage account
     */
    public void transferFromBrokerageToSavings(long socialSecurityNumber, String userName, String password, double amount) throws AuthenticationException,UnauthorizedActionException, InsufficientAssetsException{
        if(security(socialSecurityNumber, userName, password)){
            throw new AuthenticationException();
        }
        if(getPatron(socialSecurityNumber).getSavingsAccount() == null || getPatron(socialSecurityNumber).getBrokerageAccount() == null){
            throw new UnauthorizedActionException();
        }
        try{
            Transaction withdraw = new Transaction(getPatron(socialSecurityNumber), Transaction.TRANSACTION_TYPE.WITHDRAW, getPatron(socialSecurityNumber).getBrokerageAccount(), amount);
            withdraw.execute();
            txHistoryByPatron.get(getPatron(socialSecurityNumber)).add(withdraw);
            Transaction deposit = new Transaction(getPatron(socialSecurityNumber), Transaction.TRANSACTION_TYPE.DEPOSIT, getPatron(socialSecurityNumber).getSavingsAccount(), amount);
            deposit.execute();
            txHistoryByPatron.get(getPatron(socialSecurityNumber)).add(deposit);
        } catch (InsufficientAssetsException e){
            throw new InsufficientAssetsException();
        }
    }
}