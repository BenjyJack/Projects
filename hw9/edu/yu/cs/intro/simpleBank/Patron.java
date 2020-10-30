package edu.yu.cs.intro.simpleBank;

public class Patron {
    private final String firstName;
    private final String lastName;
    private final long socialSecurityNumber;
    private final String userName;
    private final String password;
    private BrokerageAccount brokerageAccount;
    private Account savingsAccount;

    protected Patron(String firstName, String lastName, long socialSecurityNumber, String userName, String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.socialSecurityNumber = socialSecurityNumber;
        this.userName = userName;
        this.password = password;
    }
    protected String getFirstName(){return this.firstName;}
    protected String getLastName(){return this.lastName;}
    protected String getUserName(){
        return this.userName;
    }
    protected String getPassword(){
        return this.password;
    }
    protected long getSocialSecurityNumber(){return this.socialSecurityNumber;}
    protected void addAccount(Account acct){
        if(acct instanceof BrokerageAccount){
            brokerageAccount = (BrokerageAccount) acct;
        }
        else{
            savingsAccount = acct;
        }
    }
    protected Account getAccount(long accountNumber) {
        if(brokerageAccount != null){
            if(brokerageAccount.getAccountNumber() == accountNumber){
                return brokerageAccount;
            }
        }
        if(savingsAccount != null){
            if(savingsAccount.getAccountNumber() == accountNumber){
                return savingsAccount;
            }
        }
        return null;
    }
    protected void setBrokerageAccount(BrokerageAccount account) {
        brokerageAccount = account;
    }
    protected void setSavingsAccount(Account account) {
        savingsAccount = account;
    }
    protected BrokerageAccount getBrokerageAccount() {return this.brokerageAccount;}
    protected Account getSavingsAccount() {return this.savingsAccount;}

    /**
     * total cash in savings + total cash in brokerage + total value of shares in brokerage
     * return 0 if the patron doesn't have any accounts
     */
    protected double getNetWorth(){
        double total = 0;
        if(getSavingsAccount() != null){
            total += getSavingsAccount().getAvailableBalance();
        }
        if(getBrokerageAccount() != null){
            total += getBrokerageAccount().getTotalBalance();
        }
        return total;
    }
}
