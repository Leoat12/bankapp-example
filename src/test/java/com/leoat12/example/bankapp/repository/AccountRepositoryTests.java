package com.leoat12.example.bankapp.repository;

import com.leoat12.example.bankapp.model.Account;
import com.leoat12.example.bankapp.model.Agency;
import com.leoat12.example.bankapp.model.Client;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AccountRepositoryTests {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AgencyRepository agencyRepository;

    private Client client;
    private Agency agency;

    @Before
    public void createEntities(){
        client = new Client("Leonardo", "545.104.110-57", Client.Gender.M, 23);
        client = clientRepository.save(client);
        agency = new Agency("5th Avenue");
        agency = agencyRepository.save(agency);
    }

    @Test
    public void createAnAccount(){
        accountRepository.save(new Account(client, agency));
        Optional<Account> account = accountRepository.findById(client.getId().toString().concat("-").concat(agency.getId().toString()));
        assertThat(account.isPresent(), equalTo(true));
    }

    @Test
    public void updateBalance(){
        accountRepository.save(new Account(client, agency));
        Optional<Account> accountOptional = accountRepository.findById(client.getId().toString().concat("-").concat(agency.getId().toString()));

        assertThat(accountOptional.isPresent(), equalTo(true));

        Account account = accountOptional.get();
        account.updateBalance(50.0);
        account = accountRepository.save(account);

        assertThat(account.getBalance(), equalTo(50.0));

    }
}
