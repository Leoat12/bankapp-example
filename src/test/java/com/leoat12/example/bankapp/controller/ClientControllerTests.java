package com.leoat12.example.bankapp.controller;

import com.leoat12.example.bankapp.exception.ResourceNotFoundException;
import com.leoat12.example.bankapp.model.Client;
import com.leoat12.example.bankapp.service.ClientService;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@WebMvcTest(ClientController.class)
public class ClientControllerTests {

    private Client client;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    @Before
    public void setUp(){
        RestAssuredMockMvc.mockMvc(mockMvc);
        client = new Client("Leonardo", "545.104.110-57", Client.Gender.M, 23);
        client.setId(1);
    }

    @Test
    public void createClient(){
        Client client = new Client("Leonardo", "545.104.110-57", Client.Gender.M, 23);

        when(clientService.createClient(client)).thenReturn(this.client);
        RestAssuredMockMvc
                .given()
                    .body(client)
                    .contentType(ContentType.JSON)
                .when()
                    .post("/client")
                    .prettyPeek()
                .then()
                    .statusCode(201)
                    .body("id", equalTo(1))
                    .body("name", equalTo("Leonardo"));
    }

    @Test
    public void updateClient(){

        when(clientService.updateClient(client)).thenReturn(client);
        RestAssuredMockMvc
                .given()
                    .body(client)
                    .contentType(ContentType.JSON)
                .when()
                    .put("/client")
                    .prettyPeek()
                .then()
                    .statusCode(200)
                    .body("id", equalTo(1))
                    .body("document", equalTo("545.104.110-57"));
    }

    @Test
    public void return400WhenAnyPropertyIsNull(){

        Client client = new Client();
        client.setId(1);
        client.setName("Leonardo");

        RestAssuredMockMvc
                .given()
                    .body(client)
                    .contentType(ContentType.JSON)
                .when()
                    .post("/client")
                    .prettyPeek()
                .then()
                    .statusCode(400);
    }

    @Test
    public void return400WhenIdIsNull(){
        Client client = new Client("Leonardo", "545.104.110-57", Client.Gender.M, 23);

        RestAssuredMockMvc
                .given()
                    .body(client)
                    .contentType(ContentType.JSON)
                .when()
                    .put("/client")
                    .prettyPeek()
                .then()
                    .statusCode(400);
    }

    @Test
    public void return404WhenAClientIsNotFound(){
        Client client = new Client("Leonardo", "545.104.110-57", Client.Gender.M, 23);
        client.setId(2);

        when(clientService.updateClient(eq(client))).thenThrow(ResourceNotFoundException.class);
        RestAssuredMockMvc
                .given()
                    .body(client)
                    .contentType(ContentType.JSON)
                .when()
                    .put("/client")
                    .prettyPeek()
                .then()
                    .statusCode(404);
    }
}
