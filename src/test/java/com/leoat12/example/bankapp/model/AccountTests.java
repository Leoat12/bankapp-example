package com.leoat12.example.bankapp.model;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class AccountTests {

    private Client client;
    private Agency agency;

    @Before
    public void prepareObjetcs(){
        client = new Client("Leonardo", "545.104.110-57", Client.Gender.M, 23);
        client.setId(1);
        agency = new Agency("5th Avenue");
        agency.setId(1);
    }


    @Test
    public void createDefaultAccount(){
        Account account = new Account(client, agency);
        assertThat(account.getNumber(), equalTo("1-1"));
        assertThat(account.getBalance(), equalTo(0.0));
        assertThat(account.getType(), equalTo(Account.AccountType.COMMON));
    }

    @Test
    public void createCustomAccount(){
        Account account = new Account(client, agency, 50.0, Account.AccountType.GOLD);
        assertThat(account.getNumber(), equalTo("1-1"));
        assertThat(account.getBalance(), equalTo(50.0));
        assertThat(account.getType(), equalTo(Account.AccountType.GOLD));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldThrowExceptionForNullClientOrAgency(){
        Account account = new Account(client, null, 50.0, Account.AccountType.GOLD);
    }

    @Test
    public void addToBalance(){
        Account account = new Account(client, agency,50.0, Account.AccountType.GOLD);
        account.updateBalance(50.0);
        assertThat(account.getBalance(), equalTo(100.0));
    }

    @Test
    public void subtractFromBalance(){
        Account account = new Account(client, agency, 50.0, Account.AccountType.GOLD);
        account.updateBalance(-40.0);
        assertThat(account.getBalance(), equalTo(10.0));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void createCommonCountWithNegativeBalance(){
        Account account = new Account(client, agency, -10.0, Account.AccountType.COMMON);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void decreaseCommonAccountBalanceToNegativeNumber(){
        Account account = new Account(client, agency, 10.0, Account.AccountType.COMMON);
        account.updateBalance(-11.0);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void setCommonTypeWithNegativeBalance(){
        Account account = new Account(client, agency, 10.0, Account.AccountType.GOLD);
        account.updateBalance(-11.0);
        account.setType(Account.AccountType.COMMON);
    }
}
