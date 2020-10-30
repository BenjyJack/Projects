package edu.yu.cs.intro.simpleBank;
import edu.yu.cs.intro.simpleBank.exceptions.AuthenticationException;
import edu.yu.cs.intro.simpleBank.exceptions.InsufficientAssetsException;
import edu.yu.cs.intro.simpleBank.exceptions.UnauthorizedActionException;
import org.junit.Before;

import java.util.*;

import static org.junit.Assert.*;

public class BankTest {
    Bank bank;
    @org.junit.Before
    public void init(){
        bank = new Bank(5.50);
        bank.createNewPatron("first", "first", 1, "first", "first");
    }
    @org.junit.Test
    public void addNewStockToMarket() {
        bank.addNewStockToMarket("blarb", 85.22);
        assertEquals(85.22,bank.getStockPrice("blarb"), 0.0);
    }

    @org.junit.Test
    public void getAllStockTickerSymbols() {
        bank.addNewStockToMarket("blurb", 345.4);
        bank.addNewStockToMarket("foo", 543.23);
        Set<String> ticker = new HashSet<String>();
        ticker.add("blurb");
        ticker.add("foo");
        assertEquals(ticker, bank.getAllStockTickerSymbols());
    }

    @org.junit.Test
    public void getNumberOfOutstandingShares() throws AuthenticationException, InsufficientAssetsException, UnauthorizedActionException {
        long account = bank.openSavingsAccount(1, "first", "first");
        long brokerage = bank.openBrokerageAccount(1, "first", "first");
        bank.depositCashIntoSavings(1, "first", "first", 250);
        bank.transferFromSavingsToBrokerage(1, "first", "first", 100);
        bank.addNewStockToMarket("blarb", 2.25);
        bank.purchaseStock(1, "first", "first", "blarb", 2);
        assertEquals(2, bank.getNumberOfOutstandingShares("blarb"), 0);
    }

    @org.junit.Test
    public void getMarketCapitalization() throws AuthenticationException, InsufficientAssetsException, UnauthorizedActionException {
        long account = bank.openSavingsAccount(1, "first", "first");
        long brokerage = bank.openBrokerageAccount(1, "first", "first");
        bank.depositCashIntoSavings(1, "first", "first", 250);
        bank.transferFromSavingsToBrokerage(1, "first", "first", 100);
        bank.addNewStockToMarket("blarb", 2.25);
        bank.purchaseStock(1, "first", "first", "blarb", 2);
        assertEquals(2*2.25, bank.getMarketCapitalization("blarb"), .9);
    }

    @org.junit.Test
    public void getTotalSavingsInBank() {
    }

    @org.junit.Test
    public void getTotalBrokerageCashInBank() {
    }

    @org.junit.Test
    public void createNewPatron() throws AuthenticationException {
        Patron patron = new Patron("first", "first", 1, "first", "first");
        bank.createNewPatron("second", "second", 2, "second", "second");
        assertEquals(patron.getPassword(), bank.getPatron(1).getPassword());
    }

    @org.junit.Test
    public void openSavingsAccount() throws AuthenticationException{
        long account = bank.openSavingsAccount(1, "first", "first");
        assertEquals(account, bank.getPatron(1).getAccount(account).getAccountNumber());
    }

    @org.junit.Test
    public void openBrokerageAccount() throws AuthenticationException{
        long account = bank.openBrokerageAccount(1, "first", "first");
        assertEquals(account, bank.getPatron(1).getAccount(account).getAccountNumber());
    }

    @org.junit.Test
    public void depositCashIntoSavings() throws AuthenticationException{
        long account = bank.openSavingsAccount(1, "first", "first");
        bank.depositCashIntoSavings(1, "first", "first", 250);
        assertEquals(250, bank.getPatron(1).getSavingsAccount().getAvailableBalance(), 0.0);
    }

    @org.junit.Test
    public void withdrawCashFromSavings() throws AuthenticationException, InsufficientAssetsException, UnauthorizedActionException {
        long account = bank.openSavingsAccount(1, "first", "first");
        bank.depositCashIntoSavings(1, "first", "first", 250);
        bank.withdrawCashFromSavings(1, "first", "first", 100);
        assertEquals(144.5, bank.getPatron(1).getSavingsAccount().getAvailableBalance(), 0.0);
    }

    @org.junit.Test
    public void withdrawCashFromBrokerage() throws AuthenticationException, InsufficientAssetsException, UnauthorizedActionException {
        long account = bank.openSavingsAccount(1, "first", "first");
        long brokerage = bank.openBrokerageAccount(1, "first", "first");
        bank.depositCashIntoSavings(1, "first", "first", 250);
        bank.transferFromSavingsToBrokerage(1, "first", "first", 100);
    }

    @org.junit.Test
    public void checkCashInBrokerage() {
    }

    @org.junit.Test
    public void checkTotalBalanceBrokerage() throws AuthenticationException, InsufficientAssetsException, UnauthorizedActionException {
        long account = bank.openSavingsAccount(1, "first", "first");
        long brokerage = bank.openBrokerageAccount(1, "first", "first");
        bank.depositCashIntoSavings(1, "first", "first", 250);
        bank.transferFromSavingsToBrokerage(1, "first", "first", 100);
        bank.addNewStockToMarket("blarb", 2.25);
        bank.purchaseStock(1, "first", "first", "blarb", 2);
        assertEquals(89, bank.checkTotalBalanceBrokerage(1, "first", "first"), 0);
    }

    @org.junit.Test
    public void checkBalanceSavings() {
    }

    @org.junit.Test
    public void purchaseStock() throws AuthenticationException, InsufficientAssetsException, UnauthorizedActionException {
        long account = bank.openSavingsAccount(1, "first", "first");
        long brokerage = bank.openBrokerageAccount(1, "first", "first");
        bank.depositCashIntoSavings(1, "first", "first", 250);
        bank.transferFromSavingsToBrokerage(1, "first", "first", 100);
        bank.addNewStockToMarket("blarb", 2.25);
        bank.purchaseStock(1, "first", "first", "blarb", 2);
        assertEquals(90, bank.getPatron(1).getBrokerageAccount().getAvailableBalance(), 0.0);
    }

    @org.junit.Test
    public void sellStock() throws AuthenticationException, InsufficientAssetsException, UnauthorizedActionException {
        long account = bank.openSavingsAccount(1, "first", "first");
        long brokerage = bank.openBrokerageAccount(1, "first", "first");
        bank.depositCashIntoSavings(1, "first", "first", 250);
        bank.transferFromSavingsToBrokerage(1, "first", "first", 100);
        bank.addNewStockToMarket("blarb", 2.25);
        bank.purchaseStock(1, "first", "first", "blarb", 2);
        bank.sellStock(1, "first", "first", "blarb", 2);
        assertEquals(89, bank.getPatron(1).getBrokerageAccount().getAvailableBalance(), 0.0);
    }

    @org.junit.Test
    public void getNetWorth() throws AuthenticationException, InsufficientAssetsException, UnauthorizedActionException {
        long account = bank.openSavingsAccount(1, "first", "first");
        long brokerage = bank.openBrokerageAccount(1, "first", "first");
        bank.depositCashIntoSavings(1, "first", "first", 250);
        bank.transferFromSavingsToBrokerage(1, "first", "first", 100);
        bank.addNewStockToMarket("blarb", 2.25);
        bank.purchaseStock(1, "first", "first", "blarb", 2);
        bank.sellStock(1, "first", "first", "blarb", 2);
        double what = bank.getPatron(1).getNetWorth();
        assertEquals(144.5 + 89, what, 0.0);
    }

    @org.junit.Test
    public void getTransactionHistory() {
    }

    @org.junit.Test
    public void transferFromSavingsToBrokerage() throws AuthenticationException, InsufficientAssetsException, UnauthorizedActionException {
        long account = bank.openSavingsAccount(1, "first", "first");
        long brokerage = bank.openBrokerageAccount(1, "first", "first");
        bank.depositCashIntoSavings(1, "first", "first", 250);
        bank.transferFromSavingsToBrokerage(1, "first", "first", 100);
        assertEquals(100, bank.getPatron(1).getBrokerageAccount().getAvailableBalance(), 0.0);
    }

    @org.junit.Test
    public void transferFromBrokerageToSavings() throws AuthenticationException, InsufficientAssetsException, UnauthorizedActionException {
        long account = bank.openSavingsAccount(1, "first", "first");
        long brokerage = bank.openBrokerageAccount(1, "first", "first");
        bank.depositCashIntoSavings(1, "first", "first", 250);
        bank.transferFromSavingsToBrokerage(1, "first", "first", 100);
        bank.transferFromBrokerageToSavings(1, "first", "first", 50);
        assertEquals(44.5, bank.getPatron(1).getBrokerageAccount().getAvailableBalance(), 0.0);
    }
}