package com.leoat12.example.bankapp.model;

import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ClientTests {

    @Test
    public void clientCreation(){
        Client client = new Client("Leonardo", "545.104.110-57", Client.Gender.M, 23);
        assertThat(client.getName(), equalTo("Leonardo"));
        assertThat(client.getDocument(), equalTo("545.104.110-57"));
        assertThat(client.getGender(), equalTo(Client.Gender.M));
        assertThat(client.getAge(), equalTo(23));
    }

    @Test(expected = IllegalArgumentException.class)
    public void clientWithNullEmptyFields(){
        Client client = new Client("", null, Client.Gender.M, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void clientWithInvalidDocument(){
        Client client = new Client("Leonardo", "1686474681", Client.Gender.M, 23);
    }
}
