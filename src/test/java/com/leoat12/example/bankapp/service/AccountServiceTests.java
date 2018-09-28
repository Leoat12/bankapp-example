package com.leoat12.example.bankapp.service;

import com.leoat12.example.bankapp.model.Account;
import com.leoat12.example.bankapp.model.Agency;
import com.leoat12.example.bankapp.model.Client;
import com.leoat12.example.bankapp.repository.AccountRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
public class AccountServiceTests {

    @Autowired
    private AccountService accountService;

    @MockBean
    private AccountRepository accountRepository;

    @TestConfiguration
    static class AccountServiceTestConfiguration{
        @Bean
        public AccountService accountService(){
            return new AccountService();
        }
    }

    @Before
    public void setUp(){
        Client clientWithId = new Client("Leonardo", "545.104.110-57", Client.Gender.M, 23);
        clientWithId.setId(1);

        Agency agencyWithId = new Agency("5th Avenue");
        agencyWithId.setId(1);

        Agency agencyWithId2 = new Agency("5th Avenue");
        agencyWithId2.setId(2);

        Account account = new Account(clientWithId, agencyWithId, 50.0, Account.AccountType.COMMON);
        Account goldAccount = new Account(clientWithId, agencyWithId2, 50.0, Account.AccountType.GOLD);
        Account accountUp = new Account(clientWithId, agencyWithId, 60.0, Account.AccountType.COMMON);

        Mockito.when(accountRepository.findById("1-1"))
                .thenReturn(Optional.of(account));

        Mockito.when(accountRepository.findById("1-2"))
                .thenReturn(Optional.of(goldAccount));

        Mockito.when(accountRepository.save(account))
                .thenReturn(account);

        Mockito.when(accountRepository.save(accountUp))
                .thenReturn(accountUp);

        Mockito.when(accountRepository.save(goldAccount))
                .thenReturn(goldAccount);
    }

    @Test
    public void createDefaultAccount(){
        Client clientWithId = new Client("Leonardo", "545.104.110-57", Client.Gender.M, 23);
        clientWithId.setId(1);

        Agency agencyWithId = new Agency("5th Avenue");
        agencyWithId.setId(1);

        Account account = new Account(clientWithId, agencyWithId, 50.0, Account.AccountType.COMMON);
        Account createdAccount = accountService.create(account);

        assertThat(createdAccount.getNumber(), equalTo("1-1"));
        assertThat(createdAccount.getType(), equalTo(Account.AccountType.COMMON));
        assertThat(createdAccount.getBalance(), equalTo(50.0));
    }

    @Test
    public void createGoldAccount(){
        Client clientWithId = new Client("Leonardo", "545.104.110-57", Client.Gender.M, 23);
        clientWithId.setId(1);

        Agency agencyWithId = new Agency("5th Avenue");
        agencyWithId.setId(2);

        Account account = new Account(clientWithId, agencyWithId, 50.0, Account.AccountType.GOLD);
        Account goldAccount = accountService.create(account);

        assertThat(goldAccount.getType(), equalTo(Account.AccountType.GOLD));
    }

    @Test
    public void updateBalanceToUp(){
        Client clientWithId = new Client("Leonardo", "545.104.110-57", Client.Gender.M, 23);
        clientWithId.setId(1);

        Agency agencyWithId = new Agency("5th Avenue");
        agencyWithId.setId(1);

        Account account = new Account(clientWithId, agencyWithId, 50.0, Account.AccountType.COMMON);
        Account updatedAccount = accountService.updateBalance(account, 10.0);

        assertThat(updatedAccount.getBalance(), equalTo(60.0));
    }

    @Test
    public void updateBalanceToDown(){
        Client clientWithId = new Client("Leonardo", "545.104.110-57", Client.Gender.M, 23);
        clientWithId.setId(1);

        Agency agencyWithId = new Agency("5th Avenue");
        agencyWithId.setId(1);

        Account account = new Account(clientWithId, agencyWithId, 50.0, Account.AccountType.COMMON);
        Account updatedAccount = accountService.updateBalance(account, -10.0);

        assertThat(updatedAccount.getBalance(), equalTo(40.0));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void updateBalanceToNegativeWithCommonAccount(){
        Client clientWithId = new Client("Leonardo", "545.104.110-57", Client.Gender.M, 23);
        clientWithId.setId(1);

        Agency agencyWithId = new Agency("5th Avenue");
        agencyWithId.setId(1);

        Account account = new Account(clientWithId, agencyWithId, 50.0, Account.AccountType.COMMON);
        Account updatedAccount = accountService.updateBalance(account, -60.0);
    }

    @Test
    public void updateBalanceToNegativeWithGoldAccount(){
        Client clientWithId = new Client("Leonardo", "545.104.110-57", Client.Gender.M, 23);
        clientWithId.setId(1);

        Agency agencyWithId = new Agency("5th Avenue");
        agencyWithId.setId(2);

        Account account = new Account(clientWithId, agencyWithId, 50.0, Account.AccountType.GOLD);
        Account updatedAccount = accountService.updateBalance(account, -60.0);

        assertThat(updatedAccount.getBalance(), equalTo(-10.0));
    }
}
