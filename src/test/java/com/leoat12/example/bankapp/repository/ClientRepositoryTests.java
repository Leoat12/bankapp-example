package com.leoat12.example.bankapp.repository;

import com.leoat12.example.bankapp.model.Client;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ClientRepositoryTests {

    @Autowired
    private ClientRepository clientRepository;

    @Before
    public void prepareEntity(){
        clientRepository.save(new Client("Leonardo", "545.104.110-57", Client.Gender.M, 23));
    }

    @Test
    public void findByName(){
        Optional<Client> client = clientRepository.findByName("Leonardo");
        assertEquals("Leonardo", client.get().getName());
    }

    @Test
    public void findByDocument(){
        Optional<Client> client = clientRepository.findByDocument("545.104.110-57");
        assertEquals("Leonardo", client.get().getName());
    }
}
