package com.leoat12.example.bankapp.service;

import com.leoat12.example.bankapp.model.Client;
import com.leoat12.example.bankapp.repository.ClientRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
public class ClientServiceTests {

    @Autowired
    private ClientService clientService;

    @MockBean
    private ClientRepository clientRepository;

    @TestConfiguration
    static class ClientServiceConfiguration{
        @Bean
        public ClientService clientService(){
            return new ClientService();
        }
    }

    @Before
    public void setUp(){
        Client client = new Client("Leonardo", "545.104.110-57", Client.Gender.M, 23);
        Client clientWithId = new Client("Leonardo", "545.104.110-57", Client.Gender.M, 23);
        clientWithId.setId(1);
        Mockito.when(clientRepository.save(client))
                .thenReturn(clientWithId);

        Client updatedClient = new Client("Leonardo dos Anjos", "545.104.110-57", Client.Gender.M, 24);
        updatedClient.setId(1);

        Mockito.when(clientRepository.save(updatedClient))
                .thenReturn(updatedClient);

        Mockito.when(clientRepository.existsById(1))
                .thenReturn(true);
    }

    @Test
    public void createClient(){
        Client client = new Client("Leonardo", "545.104.110-57", Client.Gender.M, 23);
        client = clientService.createClient(client);

        assertThat(client.getId(), equalTo(1));
        assertThat(client.getName(), equalTo("Leonardo"));
        assertThat(client.getDocument(), equalTo("545.104.110-57"));
        assertThat(client.getGender(), equalTo(Client.Gender.M));
        assertThat(client.getAge(), equalTo(23));
    }

    @Test
    public void updateClient(){
        Client updatedClient = new Client("Leonardo dos Anjos", "545.104.110-57", Client.Gender.M, 24);
        updatedClient.setId(1);

        updatedClient = clientService.updateClient(updatedClient);

        assertThat(updatedClient.getId(), equalTo(1));
        assertThat(updatedClient.getName(), equalTo("Leonardo dos Anjos"));
        assertThat(updatedClient.getDocument(), equalTo("545.104.110-57"));
        assertThat(updatedClient.getGender(), equalTo(Client.Gender.M));
        assertThat(updatedClient.getAge(), equalTo(24));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldThrowErrorWhenAClientWithoutIdIsUpdated(){
        Client updatedClient = new Client("Leonardo dos Anjos", "545.104.110-57", Client.Gender.M, 24);

        clientService.updateClient(updatedClient);
    }

}
