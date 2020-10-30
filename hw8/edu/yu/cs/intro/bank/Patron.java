package edu.yu.cs.intro.bank;

import edu.yu.cs.intro.bank.exceptions.NoSuchAccountException;

public class Patron {
    private final String firstName;
    private final String lastName;
    private final long socialSecurityNumber;
    private final String userName;
    private final String password;
    private BrokerageAccount brokerageAccount = null;
    private SavingsAccount savingsAccount = null;

    protected Patron(String firstName, String lastName, long socialSecurityNumber, String userName, String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.socialSecurityNumber = socialSecurityNumber;
        this.userName = userName;
        this.password = password;
    }
<<<<<<< HEAD
    protected String getFirstName(){return fisrtName;}
    protected String getLastName(){return null;}
    protected long getSocialSecurityNumber(){return 0;}
    protected void addAccount(Account acct){}
    protected Account getAccount(long accountNumber) throws NoSuchAccountException {return null;}
    protected void setBrokerageAccount(BrokerageAccount account) {}
    protected void setSavingsAccount(SavingsAccount account) {}
    protected BrokerageAccount getBrokerageAccount() {return null;}
    protected SavingsAccount getSavingsAccount() {return null;}
=======

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Patron)) {
            return false;
        }
        Patron other = (Patron) obj;
        if (this.firstName.equals(other.firstName) && this.lastName.equals(other.lastName) && this.socialSecurityNumber == other.socialSecurityNumber && this.userName.equals(other.userName) && this.password.equals(other.password)) {
            return true;
        }
        return false;
    }
    protected String getFirstName(){return this.firstName;}
    protected String getLastName(){return this.lastName;}
    protected long getSocialSecurityNumber(){return this.socialSecurityNumber;}
    protected String getUserName(){return this.userName;}
    protected String getPassword(){return this.password;}
    protected void addAccount(Account acct){
        if (acct instanceof SavingsAccount){
            savingsAccount = (SavingsAccount) acct;
        }
        else{
            brokerageAccount = (BrokerageAccount) acct;
        }
    }


    protected Account getAccount(long accountNumber) throws NoSuchAccountException {
        if(getBrokerageAccount() != null){
            if (getBrokerageAccount().getAccountNumber() == accountNumber){
                return brokerageAccount;
            }
        }
        if(getSavingsAccount() != null){
            if (getSavingsAccount().getAccountNumber() == accountNumber) {
                return savingsAccount;
            }
        }
        throw new NoSuchAccountException();
    }
    protected BrokerageAccount getBrokerageAccount() {
        return brokerageAccount;
    }
    protected SavingsAccount getSavingsAccount() {
        return savingsAccount;
    }
>>>>>>> 781d57844ade9bb18daea2d235e88dde2cd5b37c

    /**
     * total cash in savings + total cash in brokerage + total value of shares in brokerage
     * return 0 if the patron doesn't have any accounts
     */
    protected double getNetWorth(){
        double netWorth = 0;
        if(savingsAccount != null) {
            netWorth += savingsAccount.getTotalBalance();
        }
        if (brokerageAccount != null) {
            netWorth += brokerageAccount.getTotalBalance();
        }
        return netWorth;
    }
}
