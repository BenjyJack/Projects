package edu.yu.cs.intro.bank;

import com.sun.org.apache.xml.internal.resolver.readers.ExtendedXMLCatalogReader;
import edu.yu.cs.intro.bank.exceptions.*;
import org.junit.Test;

import java.sql.Array;
import java.sql.SQLOutput;

import static org.junit.Assert.assertEquals;

public class Bank {
    public static Bank INSTANCE = new Bank();

    public Bank() {
    }

    public static Bank getBank() {
        return INSTANCE;
    }

    private Patron[] bankPatrons = new Patron[1];
    private Account[] bankAccounts = new Account[1];
    private Stock[] stocksOnMarket = new Stock[1];

    public Patron getPatron(int i) {
        return bankPatrons[i];
    }

    public Account getAccount(int i) {
        return bankAccounts[i];
    }

    public Stock getStock(int i) {
        return stocksOnMarket[i];
    }
//    public static void main(String[] args){
//        Bank bank = Bank.getBank();
//            String firstName1 = "first";
//            String firstName2 = "second";
//            String firstName3 = "third";
//            String firstName4 = "fourth";
//            String firstName5 = "fifth";
//            String firstName6 = "sixth";
//            String firstName7 = "seventh";
//            String firstName8 = "eighth";
//            String firstName9 = "ninth";
//            String firstName10 = "tenth";
//
//            String lastName1 = "first";
//            String lastName2 = "second";
//            String lastName3 = "third";
//            String lastName4 = "fourth";
//            String lastName5 = "fifth";
//            String lastName6 = "sixth";
//            String lastName7 = "seventh";
//            String lastName8 = "eighth";
//            String lastName9 = "ninth";
//            String lastName10 = "tenth";
//
//            String userName1 = "first";
//            String userName2 = "second";
//            String userName3 = "third";
//            String userName4 = "fourth";
//            String userName5 = "fifth";
//            String userName6 = "sixth";
//            String userName7 = "seventh";
//            String userName8 = "eighth";
//            String userName9 = "ninth";
//            String userName10 = "tenth";
//
//            String password1 = "first";
//            String password2 = "second";
//            String password3 = "third";
//            String password4 = "fourth";
//            String password5 = "fifth";
//            String password6 = "sixth";
//            String password7 = "seventh";
//            String password8 = "eighth";
//            String password9 = "ninth";
//            String password10 = "tenth";
//
//            long socialSecurityNumber1 = 1;
//            long socialSecurityNumber2 = 2;
//            long socialSecurityNumber3 = 3;
//            long socialSecurityNumber4 = 4;
//            long socialSecurityNumber5 = 5;
//            long socialSecurityNumber6 = 6;
//            long socialSecurityNumber7 = 7;
//            long socialSecurityNumber8 = 8;
//            long socialSecurityNumber9 = 9;
//            long socialSecurityNumber10 = 10;
//
//            String tickerSymbol1 = "one";
//            String tickerSymbol2 = "two";
//
//            double sharePrice1 = 1.1;
//            double sharePrice2 = 2.2;
//
//                try {
//                    bank.createNewPatron(firstName1, lastName1, socialSecurityNumber1, userName1, password1);
//                    bank.createNewPatron(firstName2, lastName2, socialSecurityNumber2, userName2, password2);
//                    bank.createNewPatron(firstName3, lastName3, socialSecurityNumber3, userName3, password3);
//                    bank.createNewPatron(firstName4, lastName4, socialSecurityNumber4, userName4, password4);
//                    bank.createNewPatron(firstName5, lastName5, socialSecurityNumber5, userName5, password5);
//                    bank.createNewPatron(firstName6, lastName6, socialSecurityNumber6, userName6, password6);
//                    bank.createNewPatron(firstName7, lastName7, socialSecurityNumber7, userName7, password7);
//                    bank.createNewPatron(firstName8, lastName8, socialSecurityNumber8, userName8, password8);
//                    bank.createNewPatron(firstName9, lastName9, socialSecurityNumber9, userName9, password9);
//                    bank.createNewPatron(firstName10, lastName10, socialSecurityNumber10, userName10, password10);
//                    bank.addNewStockToMarket(tickerSymbol1, sharePrice1);
//                    bank.addNewStockToMarket(tickerSymbol2, sharePrice2);
//                    bank.openBrokerageAccount(socialSecurityNumber1, userName1, password1);
//                    bank.openSavingsAccount(socialSecurityNumber2, userName2, password2);
//                    bank.openSavingsAccount(socialSecurityNumber3, userName3, password3);
//                    bank.openBrokerageAccount(socialSecurityNumber3, userName3, password3);
//                    bank.depositCashIntoSavings(socialSecurityNumber3, userName3, password3, 34);
//                    System.out.println("sdffggghgjvgj");
//                    bank.openSavingsAccount(socialSecurityNumber5, userName5, password5);
//                    System.out.println("BLAJHSKFJSLKDFJ");
//                    bank.openBrokerageAccount(socialSecurityNumber5, userName5, password5);
//                    bank.depositCashIntoSavings(socialSecurityNumber5, userName5, password5, 1000);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    System.out.println("Set up issue");
//                }
//                System.out.println("Test 1: " + bank.bankPatrons[0].getFirstName());
//            System.out.println("Test 2: " + bank.bankPatrons[1].getLastName());
//            System.out.println("Test 3: " + bank.bankPatrons[2].getSocialSecurityNumber());
//            System.out.println("Test 4: " + bank.bankPatrons[3].getUserName());
//            System.out.println("Test 5: " + bank.bankPatrons[4].getPassword());
//            try{
//                bank.createNewPatron(firstName1, lastName1, socialSecurityNumber1, userName1, password1);
//            }catch (UnauthorizedActionException e){
//                System.out.println("Test 6: Exception thrown correctly: couldn't double Patron");
//            }
//            System.out.println("Test 7: " + bank.getStockBySymbol(tickerSymbol1).getTickerSymbol());
//            System.out.println("Test 8: " + bank.getStockBySymbol(tickerSymbol2).getSharePrice());
//            System.out.println("Test 9: " + bank.getListOfAllStockTickerSymbols()[1]);
//            try{
//                bank.depositCashIntoSavings(socialSecurityNumber1, userName1, password2, 100);
//            }catch (AuthenticationException e){
//                System.out.println("Test 10: Exception thrown correctly: broke security authenitication");
//            }catch (UnauthorizedActionException e){}
//            try{
//                bank.depositCashIntoSavings(socialSecurityNumber4, userName4, password4, 100);
//            }catch(AuthenticationException e) {
//                System.out.println("THIS SHOULD NOT HAVE HAPPENED 1");
//            }catch(UnauthorizedActionException e){
//                System.out.println("Test 11: Exception thrown correctly: no such savings exists");
//            }
//            try{
//                bank.depositCashIntoSavings(socialSecurityNumber2, userName2, password2, 100);
//                System.out.println("Test 12: " + bank.checkBalanceSavings(socialSecurityNumber2, userName2, password2) + " was deposited in savings account");
//            }catch (AuthenticationException e){
//                System.out.println("THIS SHOULD NOT HAVE HAPPENED 1");
//            }catch (UnauthorizedActionException e){
//                System.out.println("THIS SHOULDN'T HAVE HAPPENED 2");
//            }
//            try{
//                bank.transferFromSavingsToBrokerage(socialSecurityNumber3, userName3, password3, 35);
//            }catch (InsufficientAssetsException e){
//                System.out.println("Test 13: Exception thrown correctly: not enough money to transfer");
//            }catch (Exception e){
//                System.out.println("UNEXPECTED EXCEPTION");
//            }
//            try{
//                bank.transferFromSavingsToBrokerage(socialSecurityNumber3, userName3, password3, 34);
//                System.out.println("Test 14: $34 was transferred into the Brokerage Account");
//            }catch (Exception e){
//                System.out.println("This SHOULDN'T have happened");
//            }
//            try{
//                System.out.println("Test 15: " + bank.checkBalanceSavings(socialSecurityNumber5, userName5, password5) + " has been added to account 5");
//
//            } catch (Exception e){
//                System.out.println("this shouldn't have happened");
//            }
//        try{
//            bank.transferFromSavingsToBrokerage(socialSecurityNumber5, userName5, password5, 340);
//            bank.transferFromBrokerageToSavings(socialSecurityNumber5, userName5, password5, 34);
//            System.out.println("Test 16: $34 was transferred back into Savings Account 5");
//        }catch (Exception e){
//            System.out.println("This SHOULDN'T have happened");
//        }
//        try{
//            bank.withdrawCashFromBrokerage(socialSecurityNumber5, userName5, password5, 6);
//            System.out.println("test 17: withdrew cash from brokerage account 5");
//        } catch (Exception e){
//            System.out.println("this shouldn't have happened");
//        }
//        try{
//            bank.withdrawCashFromSavings(socialSecurityNumber5, userName5, password5, 100);
//            System.out.println("Test 18: Withdrew cash from savings account 5");
//        } catch (Exception e){
//            System.out.println("this shouldn't have happened");
//        }
//        try{
//            bank.purchaseStock(socialSecurityNumber5, userName5, password5, tickerSymbol1, 70);
//            System.out.println("Test 19: purchase stock 1");
//        } catch (Exception e){
//            System.out.println("This shouldn't have happened");
//        }
//        try{
//            bank.sellStock(socialSecurityNumber5, userName5, password5, tickerSymbol1, 20);
//            System.out.println("Test 20: selling stock 1");
//        } catch (Exception e){
//            System.out.println("This shouldn't happen");
//            e.printStackTrace();
//        }
//        try{
//            System.out.println("Test 21: checking available balance " + bank.checkAvailableBalanceBrokerage(socialSecurityNumber5, userName5, password5));
//        } catch (Exception e) {
//            System.out.println("This shouldn't have happened");
//        }
//         try{
//             System.out.println("Test 22: checking total balance " + bank.checkTotalBalanceBrokerage(socialSecurityNumber5, userName5, password5));
//         } catch (Exception e){
//             System.out.println("This shouldn't have happened");
//         }
//          try{
//              bank.getListOfAllStockTickerSymbols();
//              System.out.println("Test 23: checked all stock symbols in Bank");
//          }catch (Exception e){
//              System.out.println("This shouldn't have happened");
//          }
//          try{
//              bank.getNumberOfOutstandingShares(tickerSymbol1);
//              System.out.println("Test 24: checking number of shares in stock 1");
//          } catch (Exception e){
//              System.out.println("This shouldn't have happened");
//          }
//          try{
//              bank.getMarketCapitalization(tickerSymbol1);
//              System.out.println("Test 25: Checking market capitilazation");
//          } catch (Exception e){
//              System.out.println("This shouldn't have happened");
//          }
//          try{
//              System.out.println("Test 26: get net worth of account 5: " + bank.getNetWorth(socialSecurityNumber5, userName5, password5));
//          }catch (Exception e){
//              System.out.println("This shouldn't have happened");
//          }
//          try{
//              System.out.println("Test 27: get total savings worth in bank: " + bank.getTotalSavingsInBank());
//          }catch (Exception e){
//              System.out.println("This shouldn't have happened");
//          }
//          try{
//              System.out.println("Test 28: get total Brokerage worth in bank: " + bank.getTotalBrokerageCashInBank());
//          }catch (Exception e){
//              System.out.println("This shouldn't have happened");
//          }
//          try{
//              bank.getTransactionHistory(socialSecurityNumber5, userName5, password5);
//              System.out.println("Test 29: transaction history of account 5 returned");
//          } catch (Exception e){
//              System.out.println("This shouldn't have happened");
//              e.printStackTrace();
//          }
//    }

    /**
     * lists a new stock with the given symbol at the given price
     */
    protected void addNewStockToMarket(String tickerSymbol, double sharePrice) {
        Stock s = new Stock(tickerSymbol, sharePrice);
        if (stocksOnMarket[stocksOnMarket.length - 1] != null) {
            stocksOnMarket = arrayDoubler(stocksOnMarket);
        }
        for (int i = 0; i < stocksOnMarket.length; i++) {
            if (stocksOnMarket[i] == null) {
                stocksOnMarket[i] = s;
                break;
            }
        }
    }

    private Stock[] arrayDoubler(Stock[] array) {
        Stock[] newArray = new Stock[array.length * 2];
        for (int i = 0; i < array.length; i++) {
            newArray[i] = array[i];
        }
        return newArray;
    }

    /**
     * return the stock object for the given stock ticker symbol
     */
    public Stock getStockBySymbol(String symbol) {
        if(stocksOnMarket[0] == null){
            return null;
        }
        for (int i = 0; i < stocksOnMarket.length; i++) {
            if (symbol.equals(stocksOnMarket[i].tickerSymbol)) {
                return stocksOnMarket[i];
            }
        }
        return null;
    }

    /**
     * @return an array of all the stock ticker symbols owned by the patrons of this bank
     */
    public String[] getListOfAllStockTickerSymbols() {
        String[] list = new String[stocksOnMarket.length];
        for (int i = 0; i < stocksOnMarket.length; i++) {
            if (stocksOnMarket[i] == null) {
                break;
            }
            list[i] = stocksOnMarket[i].tickerSymbol;

        }
        return list;
    }

    public int getNumberOfOutstandingShares(String tickerSymbol) {
        Bank.Stock fake = new Bank.Stock(tickerSymbol, 0.0);
        int totalStock = 0;
        for (int i = 0; i < bankAccounts.length; i++) {
            if (bankAccounts[i] instanceof BrokerageAccount) {
                totalStock += (((BrokerageAccount) bankAccounts[i]).getNumberOfShares(fake));
            }
        }
        return totalStock;
    }

    /**
     * @return the total number of shares of the given stock owned by all patrons combined multiplied by the price per share
     * if there is no such Stock or if the tickerSymbol is empty or null, return 0
     */
    public int getMarketCapitalization(String tickerSymbol) {
        int numberOfShares = 0;
        Bank.Stock finder = getStockBySymbol(tickerSymbol);
        for (Account account : bankAccounts) {
            if (account instanceof BrokerageAccount) {
                numberOfShares += ((BrokerageAccount) account).getNumberOfShares(finder) * finder.sharePrice;
            }
        }
        return numberOfShares;
    }

    /**
     * @return all the cash in all savings accounts added up
     */
    public double getTotalSavingsInBank() {
        double totalCash = 0.0;
        for (Account account : bankAccounts) {
            if (account instanceof SavingsAccount) {
                totalCash += ((SavingsAccount) account).getAvailableBalance();
            }
        }
        return totalCash;
    }

    /**
     * @return all the cash in all brokerage accounts added up
     */
    public double getTotalBrokerageCashInBank() {
        double totalCash = 0.0;
        for (Account account : bankAccounts) {
            if (account instanceof BrokerageAccount) {
                totalCash += account.getAvailableBalance();
            }
        }
        return totalCash;
    }

    /**
     * Creates a new Patron in the bank.
     * Throws an UnauthorizedActionException if a Patron already exists with that social security number.
     */
    public void createNewPatron(String firstName, String lastName, long socialSecurityNumber, String userName, String password) throws UnauthorizedActionException {
        Patron insert = new Patron(firstName, lastName, socialSecurityNumber, userName, password);
        for (Patron patron : bankPatrons) {
            if (patron != null) {
                if (patron.getSocialSecurityNumber() == socialSecurityNumber) {
                    throw new UnauthorizedActionException();
                }
            }
        }
        for (int i = 0; i < bankPatrons.length; i++) {
            if (bankPatrons[i] == null) {
                bankPatrons[i] = insert;
                break;
            } else if (i == bankPatrons.length - 1) {
                bankPatrons = arrayDoubler(bankPatrons);
                bankPatrons[i + 1] = insert;
                break;
            }
        }
    }

    private Patron[] arrayDoubler(Patron[] array) {
        Patron[] newArray = new Patron[array.length * 2];
        for (int i = 0; i < array.length; i++) {
            newArray[i] = array[i];
        }
        return newArray;
    }

    /**
     * @return the account number of the opened account
     * @throws AuthenticationException     if the user name or password doesn't match for the patron with the given social security number
     * @throws UnauthorizedActionException if the user already has a savings account
     */
    public long openSavingsAccount(long socialSecurityNumber, String userName, String password) throws AuthenticationException, UnauthorizedActionException {
        long accountNum = (long) (Math.random() * 523001);
        for (int i = 0; i < bankPatrons.length; i++) {
            if (security(i, socialSecurityNumber, userName, password)) {
                throw new AuthenticationException();
            }
            if ((bankPatrons[i].getSocialSecurityNumber() == socialSecurityNumber) && (bankPatrons[i].getPassword().equals(password)) && (bankPatrons[i].getUserName().equals(userName))) {
                if (bankPatrons[i].getSavingsAccount() == null) {
                    SavingsAccount insert = new SavingsAccount(accountNum, bankPatrons[i]);
                    for (int j = 0; j < bankAccounts.length; j++) {
                        if (bankAccounts[j] == null) {
                            bankAccounts[j] = insert;
                            bankPatrons[i].addAccount(bankAccounts[j]);
                            break;
                        } else if (j == bankAccounts.length - 1) {
                            bankAccounts = arrayDoubler(bankAccounts);
                            bankAccounts[j + 1] = insert;
                            bankPatrons[i].addAccount(bankAccounts[j + 1]);
                            break;
                        }
                    }
                } else {
                    throw new UnauthorizedActionException();
                }
                break;
            }
        }
        return accountNum;
    }

    private Account[] arrayDoubler(Account[] array) {
        Account[] newArray = new Account[array.length * 2];
        for (int i = 0; i < array.length; i++) {
            newArray[i] = array[i];
        }
        return newArray;
    }

    private boolean security(int i, long socialSecurityNumber, String userName, String password) {
        if ((bankPatrons[i].getSocialSecurityNumber() == socialSecurityNumber) && !((bankPatrons[i].getPassword().equals(password)) || (bankPatrons[i].getUserName().equals(userName)))) {
            return true;
        }
        if ((bankPatrons[i].getPassword().equals(password)) && !((bankPatrons[i].getSocialSecurityNumber() == socialSecurityNumber) || (bankPatrons[i].getUserName().equals(userName)))) {
            return true;
        }
        if ((bankPatrons[i].getUserName().equals(userName)) && !((bankPatrons[i].getSocialSecurityNumber() == socialSecurityNumber) || (bankPatrons[i].getPassword().equals(password)))) {
            return true;
        }
        return false;
    }

    /**
     * @return the account number of the opened account
     * @throws AuthenticationException     if the user name or password doesn't match for the patron with the given social security number
     * @throws UnauthorizedActionException if the user already has a Brokerage account
     */
    public long openBrokerageAccount(long socialSecurityNumber, String userName, String password) throws AuthenticationException, UnauthorizedActionException {
        long accountNum = (long) (Math.random() * 9223372);
        for (int i = 0; i < bankPatrons.length; i++) {
            if (security(i, socialSecurityNumber, userName, password)) {
                throw new AuthenticationException();
            }
            if ((bankPatrons[i].getSocialSecurityNumber() == socialSecurityNumber) && (bankPatrons[i].getPassword().equals(password)) && (bankPatrons[i].getUserName().equals(userName))) {
                if (bankPatrons[i].getBrokerageAccount() == null) {
                    BrokerageAccount insert = new BrokerageAccount(accountNum, bankPatrons[i]);
                    for (int j = 0; j < bankAccounts.length; j++) {
                        if (bankAccounts[j] == null) {
                            bankAccounts[j] = insert;
                            bankPatrons[i].addAccount(insert);

                            break;
                        } else if (j == bankAccounts.length - 1) {
                            bankAccounts = arrayDoubler(bankAccounts);
                            bankAccounts[j + 1] = insert;
                            bankPatrons[i].addAccount(insert);
                            break;
                        }
                    }
                } else {
                    throw new UnauthorizedActionException();
                }

                break;
            }
        }
        return accountNum;
    }

    /**
     * Deposit cash into the given savings account
     * throw AuthenticationException if the SS#, username, and password don't match a bank patron
     * throw UnauthorizedActionException if the given patron does not have a savings account
     */
    public void depositCashIntoSavings(long socialSecurityNumber, String userName, String password, double amount) throws AuthenticationException, UnauthorizedActionException {
        for (int i = 0; i < bankPatrons.length; i++) {
            if (security(i, socialSecurityNumber, userName, password)) {
                throw new AuthenticationException();
            }
            if ((bankPatrons[i].getSocialSecurityNumber() == socialSecurityNumber) && (bankPatrons[i].getPassword().equals(password)) && (bankPatrons[i].getUserName().equals(userName))) {
                if (bankPatrons[i].getSavingsAccount() == null) {
                    throw new UnauthorizedActionException();
                }
                try {
                    SavingsAccount account = bankPatrons[i].getSavingsAccount();
                    Deposit mover = new Deposit(amount, account, bankPatrons[i]);
                    mover.execute();
                    bankPatrons[i].getSavingsAccount().addTransactionToHistory(mover);
                    break;
                } catch (NoSuchAccountException e) {
                    throw new UnauthorizedActionException();
                } catch (InsufficientAssetsException e) {
                }
                break;
            } else if (i == bankPatrons.length - 1) {
                throw new AuthenticationException();
            }
        }
    }

    /**
     * transfer cash from the patron's savings account to his brokerage account
     * throws AuthenticationException if the SS#, username, and password don't match a bank patron
     * throws UnauthorizedActionException if the given patron does not have both a savings account and a brokerage account
     * throws InsufficientAssetsException if that amount of money is not present in the savings account
     */
    public void transferFromSavingsToBrokerage(long socialSecurityNumber, String userName, String password, double amount) throws AuthenticationException, UnauthorizedActionException, InsufficientAssetsException {
        for (int i = 0; i < bankPatrons.length; i++) {
            if (security(i, socialSecurityNumber, userName, password)) {
                throw new AuthenticationException();
            }
            if ((bankPatrons[i].getSocialSecurityNumber() == socialSecurityNumber) && (bankPatrons[i].getPassword().equals(password)) && (bankPatrons[i].getUserName().equals(userName))) {
                try {
                    Transfer within = new Transfer(amount, bankPatrons[i].getSavingsAccount(), bankPatrons[i].getBrokerageAccount(), bankPatrons[i]);
                    within.execute();
                    bankPatrons[i].getSavingsAccount().addTransactionToHistory(within);
                    bankPatrons[i].getBrokerageAccount().addTransactionToHistory(within);
                } catch (NoSuchAccountException e) {
                    throw new UnauthorizedActionException();
                } catch (InsufficientAssetsException e) {
                    throw new InsufficientAssetsException();
                }
                break;
            } else if (i == bankPatrons.length - 1) {
                throw new AuthenticationException();
            }
        }
    }

    /**
     * transfer cash from the patron's savings account to his brokerage account
     * throws AuthenticationException if the SS#, username, and password don't match a bank patron
     * throws UnauthorizedActionException if the given patron does not have both a savings account and a brokerage account
     * throws InsufficientAssetsException if that amount of money is not present in CASH in the brokerage account
     */
    public void transferFromBrokerageToSavings(long socialSecurityNumber, String userName, String password, double amount) throws AuthenticationException, UnauthorizedActionException, InsufficientAssetsException {
        for (int i = 0; i < bankPatrons.length; i++) {
            if (security(i, socialSecurityNumber, userName, password)) {
                throw new AuthenticationException();
            }
            if ((bankPatrons[i].getSocialSecurityNumber() == socialSecurityNumber) && (bankPatrons[i].getPassword().equals(password)) && (bankPatrons[i].getUserName().equals(userName))) {
                try {
                    Transfer within = new Transfer(amount, bankPatrons[i].getBrokerageAccount(), bankPatrons[i].getSavingsAccount(), bankPatrons[i]);
                    within.execute();
                    bankPatrons[i].getSavingsAccount().addTransactionToHistory(within);
                    bankPatrons[i].getBrokerageAccount().addTransactionToHistory(within);
                    break;
                } catch (NoSuchAccountException e) {
                    throw new UnauthorizedActionException();
                } catch (InsufficientAssetsException e) {
                    throw new InsufficientAssetsException();
                }
            } else if (i == bankPatrons.length - 1) {
                throw new AuthenticationException();
            }
        }
    }

    /**
     * withdraw cash from the patron's savings account
     * throw AuthenticationException if the SS#, username, and password don't match a bank patron
     * throw UnauthorizedActionException if the given patron does not have a savings account
     * throw InsufficientAssetsException if that amount of money is not present the savings account
     */
    public void withdrawCashFromSavings(long socialSecurityNumber, String userName, String password, double amount) throws AuthenticationException, UnauthorizedActionException, InsufficientAssetsException {
        for (int i = 0; i < bankPatrons.length; i++) {
            if (security(i, socialSecurityNumber, userName, password)) {
                throw new AuthenticationException();
            }
            if ((bankPatrons[i].getSocialSecurityNumber() == socialSecurityNumber) && (bankPatrons[i].getPassword().equals(password)) && (bankPatrons[i].getUserName().equals(userName))) {
                try {
                    Withdrawal mover = new Withdrawal(amount, bankPatrons[i].getSavingsAccount(), bankPatrons[i]);
                    mover.execute();
                    bankPatrons[i].getSavingsAccount().addTransactionToHistory(mover);
                } catch (NoSuchAccountException e) {
                    throw new UnauthorizedActionException();
                } catch (InsufficientAssetsException e) {
                    throw new InsufficientAssetsException();
                }
                break;
            } else if (i == bankPatrons.length - 1) {
                throw new AuthenticationException();
            }
        }
    }

    /**
     * withdraw cash from the patron's brokerage account
     * throw AuthenticationException if the SS#, username, and password don't match a bank patron
     * throw UnauthorizedActionException if the given patron does not have a brokerage account
     * throw InsufficientAssetsException if that amount of CASH is not present the brokerage account
     */
    public void withdrawCashFromBrokerage(long socialSecurityNumber, String userName, String password, double amount) throws AuthenticationException, UnauthorizedActionException, InsufficientAssetsException {
        for (int i = 0; i < bankPatrons.length; i++) {
            if (security(i, socialSecurityNumber, userName, password)) {
                throw new AuthenticationException();
            }
            if ((bankPatrons[i].getSocialSecurityNumber() == socialSecurityNumber) && (bankPatrons[i].getPassword().equals(password)) && (bankPatrons[i].getUserName().equals(userName))) {
                try {
                    Withdrawal mover = new Withdrawal(amount, bankPatrons[i].getBrokerageAccount(), bankPatrons[i]);
                    mover.execute();
                    bankPatrons[i].getBrokerageAccount().addTransactionToHistory(mover);
                } catch (NoSuchAccountException e) {
                    throw new UnauthorizedActionException();
                } catch (InsufficientAssetsException e) {
                    throw new InsufficientAssetsException();
                }
                break;
            } else if (i == bankPatrons.length - 1) {
                throw new AuthenticationException();
            }
        }
    }

    /**
     * check how much cash the patron has in his brokerage account
     * throw AuthenticationException if the SS#, username, and password don't match a bank patron
     * throw UnauthorizedActionException if the given patron does not have a brokerage account
     */
    public double checkAvailableBalanceBrokerage(long socialSecurityNumber, String userName, String password) throws AuthenticationException, UnauthorizedActionException {
        for (int i = 0; i < bankPatrons.length; i++) {
            if (security(i, socialSecurityNumber, userName, password)) {
                throw new AuthenticationException();
            }
            if ((bankPatrons[i].getSocialSecurityNumber() == socialSecurityNumber) && (bankPatrons[i].getPassword().equals(password)) && (bankPatrons[i].getUserName().equals(userName))) {
                if (bankPatrons[i].getBrokerageAccount() == null) {
                    throw new UnauthorizedActionException();
                }
                return bankPatrons[i].getBrokerageAccount().getAvailableBalance();
            } else if (i == bankPatrons.length - 1) {
                throw new AuthenticationException();
            }
        }
        return 0.0;
    }

    /**
     * check the total value of the patron's brokerage account
     * throw AuthenticationException if the SS#, username, and password don't match a bank patron
     * throw UnauthorizedActionException if the given patron does not have a brokerage account
     */
    public double checkTotalBalanceBrokerage(long socialSecurityNumber, String userName, String password) throws AuthenticationException, UnauthorizedActionException {
        for (int i = 0; i < bankPatrons.length; i++) {
            if (security(i, socialSecurityNumber, userName, password)) {
                throw new AuthenticationException();
            }
            if ((bankPatrons[i].getSocialSecurityNumber() == socialSecurityNumber) && (bankPatrons[i].getPassword().equals(password)) && (bankPatrons[i].getUserName().equals(userName))) {
                if (bankPatrons[i].getBrokerageAccount() == null) {
                    throw new UnauthorizedActionException();
                }
                return bankPatrons[i].getBrokerageAccount().getTotalBalance();
            } else if (i == bankPatrons.length - 1) {
                throw new AuthenticationException();
            }
        }
        return 0.0;
    }

    /**
     * check how much cash the patron has in his savings account
     * throw AuthenticationException if the SS#, username, and password don't match a bank patron
     * throw UnauthorizedActionException if the given patron does not have a savings account
     */
    public double checkBalanceSavings(long socialSecurityNumber, String userName, String password) throws AuthenticationException, UnauthorizedActionException {
        for (int i = 0; i < bankPatrons.length; i++) {
            if (security(i, socialSecurityNumber, userName, password)) {
                throw new AuthenticationException();
            }
            if ((bankPatrons[i].getSocialSecurityNumber() == socialSecurityNumber) && (bankPatrons[i].getPassword().equals(password)) && (bankPatrons[i].getUserName().equals(userName))) {
                if (bankPatrons[i].getSavingsAccount() == null) {
                    throw new UnauthorizedActionException();
                }
                return bankPatrons[i].getSavingsAccount().getTotalBalance();
            } else if (i == bankPatrons.length - 1) {
                throw new AuthenticationException();
            }
        }
        return 0.0;
    }

    /**
     * buy shares of the given stock
     * throw AuthenticationException if the SS#, username, and password don't match a bank patron
     * throw UnauthorizedActionException if the given patron does not have a brokerage account
     * throw InsufficientAssetsException if the required amount of CASH is not present in the brokerage account
     */
    public void purchaseStock(long socialSecurityNumber, String userName, String password, String tickerSymbol, int shares) throws AuthenticationException, UnauthorizedActionException, InsufficientAssetsException {
        for (int i = 0; i < bankPatrons.length; i++) {
            if (security(i, socialSecurityNumber, userName, password)) {
                throw new AuthenticationException();
            }
            if ((bankPatrons[i].getSocialSecurityNumber() == socialSecurityNumber) && (bankPatrons[i].getPassword().equals(password)) && (bankPatrons[i].getUserName().equals(userName))) {
                try {
                    StockPurchase mover = new StockPurchase(shares, bankPatrons[i].getBrokerageAccount(), bankPatrons[i], getStockBySymbol(tickerSymbol));
                    mover.execute();
                    bankPatrons[i].getBrokerageAccount().addTransactionToHistory(mover);
                    break;
                } catch (NoSuchAccountException e) {
                    throw new UnauthorizedActionException();
                } catch (InsufficientAssetsException e) {
                    throw new InsufficientAssetsException();
                }
            } else if (i == bankPatrons.length - 1) {
                throw new AuthenticationException();
            }
        }
    }

    /**
     * sell shares of the given stock
     * throw AuthenticationException if the SS#, username, and password don't match a bank patron
     * throw UnauthorizedActionException if the given patron does not have a brokerage account
     * throw InsufficientAssetsException if the patron does not have the given number of shares of the given stock
     */
    public void sellStock(long socialSecurityNumber, String userName, String password, String tickerSymbol, int shares) throws AuthenticationException, UnauthorizedActionException, InsufficientAssetsException {
        for (int i = 0; i < bankPatrons.length; i++) {
            if (security(i, socialSecurityNumber, userName, password)) {
                throw new AuthenticationException();
            }
            if ((bankPatrons[i].getSocialSecurityNumber() == socialSecurityNumber) && (bankPatrons[i].getPassword().equals(password)) && (bankPatrons[i].getUserName().equals(userName))) {
                try {
                    StockSale mover = new StockSale(shares, bankPatrons[i].getBrokerageAccount(), bankPatrons[i], getStockBySymbol(tickerSymbol));
                    mover.execute();
                    bankPatrons[i].getBrokerageAccount().addTransactionToHistory(mover);
                    break;
                } catch (NoSuchAccountException e) {
                    throw new UnauthorizedActionException();
                } catch (InsufficientAssetsException e) {
                    throw new InsufficientAssetsException();
                }
            } else if (i == bankPatrons.length - 1) {
                throw new AuthenticationException();
            }
        }
    }

    /**
     * check the net worth of the patron
     * throw AuthenticationException if the SS#, username, and password don't match a bank patron
     * return 0 if the patron doesn't have any accounts
     */

    public double getNetWorth(long socialSecurityNumber, String userName, String password) throws AuthenticationException {
        double netWorth = 0;
        for (int i = 0; i < bankPatrons.length; i++) {
            if (security(i, socialSecurityNumber, userName, password)) {
                throw new AuthenticationException();
            }
            if ((bankPatrons[i].getSocialSecurityNumber() == socialSecurityNumber) && (bankPatrons[i].getPassword().equals(password)) && (bankPatrons[i].getUserName().equals(userName))) {
                netWorth += bankPatrons[i].getNetWorth();
                break;
            } else if (i == bankPatrons.length - 1) {
                throw new AuthenticationException();
            }
        }
        return netWorth;
    }

    /**
     * Get the transaction history on all of the patron's accounts, i.e. the transaction histories of both the savings account and
     * brokerage account (whichever of the two exist), combined. The merged history should be sorted in time order, from oldest to newest.
     * If the patron has no transactions in his history, return an array of length 0.
     * throw AuthenticationException if the SS#, username, and password don't match a bank patron
     */
    public Transaction[] getTransactionHistory(long socialSecurityNumber, String userName, String password) throws AuthenticationException {
        for (int i = 0; i < bankPatrons.length; i++) {
            if (security(i, socialSecurityNumber, userName, password)) {
                throw new AuthenticationException();
            }
            if ((bankPatrons[i].getSocialSecurityNumber() == socialSecurityNumber) && (bankPatrons[i].getPassword().equals(password)) && (bankPatrons[i].getUserName().equals(userName))) {
                Transaction[] savings = null;
                Transaction[] brokerage = null;
                if (bankPatrons[i].getSavingsAccount() != null) {
                    savings = bankPatrons[i].getSavingsAccount().getTransactionHistory();
                }
                if (bankPatrons[i].getBrokerageAccount() != null) {
                    brokerage = bankPatrons[i].getBrokerageAccount().getTransactionHistory();
                }
                if (bankPatrons[i].getBrokerageAccount() == null && bankPatrons[i].getSavingsAccount() == null) {
                    return new Transaction[0];
                }
                if (bankPatrons[i].getSavingsAccount() == null) {
                    return brokerage;
                }
                if (bankPatrons[i].getBrokerageAccount() == null) {
                    return savings;
                }
                Transaction[] merger = new Transaction[savings.length + brokerage.length];
                int j = 0;
                int k = 0;
                int l = 0;
                while (l < merger.length) {
                    if (savings[j].getTime() <= brokerage[k].getTime()) {
                        if (savings[j] == brokerage[k]) {
                            k++;
                        }
                        merger[l] = savings[j];
                        if (j < savings.length - 1) {
                            j++;
                        } else {
                            l++;
                            while (l < merger.length) {
                                merger[l] = brokerage[k];
                                if (k < brokerage.length - 1) {
                                    k++;
                                    l++;
                                } else {
                                    break;
                                }
                            }
                            break;
                        }
                    } else {
                        merger[l] = brokerage[k];
                        if (k < brokerage.length - 1) {
                            k++;
                        } else {
                            l++;
                            while (l < merger.length) {
                                merger[l] = savings[j];
                                if (j < savings.length - 1) {
                                    j++;
                                    l++;
                                } else {
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    l++;
                }
                Transaction[] fake = merger.clone();
                int looper = 0;
                int dumbdumb = 0;
                while (looper < merger.length - dumbdumb) {
                    if (fake[merger.length - 1 - dumbdumb] == null) {
                        dumbdumb++;
                    } else {
                        merger[looper] = fake[merger.length - 1 - dumbdumb - looper];
                        looper++;
                    }
                }
                return merger;
            } else if (i == bankPatrons.length - 1) {
                throw new AuthenticationException();
            }
        }
        return null;
    }

    public static class Stock {
        private final String tickerSymbol;
        private final double sharePrice;

        /**
         * Note that because this constructor is private, the Bank class is the only class that can create instances of Stock.
         * All other classes may refer to, i.e. have variables pointing to, Stock objects, but only Bank can create new Stock Objects.
         */
        private Stock(String tickerSymbol, double sharePrice) {
            this.tickerSymbol = tickerSymbol;
            this.sharePrice = sharePrice;
        }

        public double getSharePrice() {
            return this.sharePrice;
        }

        public String getTickerSymbol() {
            return this.tickerSymbol;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Bank.Stock)) {
                return false;
            }
            Stock other = (Stock) obj;
            if (this.tickerSymbol.equals(other.tickerSymbol)) {
                return true;
            }
            return false;
        }
    }
}