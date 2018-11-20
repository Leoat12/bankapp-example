package com.leoat12.example.bankapp.controller;

import com.leoat12.example.bankapp.exception.ResourceNotFoundException;
import com.leoat12.example.bankapp.model.Account;
import com.leoat12.example.bankapp.model.Agency;
import com.leoat12.example.bankapp.model.Client;
import com.leoat12.example.bankapp.service.AccountService;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@WebMvcTest(AccountController.class)
public class AccountControllerTests {

    private Account account;
    private Client client;
    private Agency agency;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Before
    public void setUp(){

        RestAssuredMockMvc
                .mockMvc(mockMvc);

        client = new Client();
        client.setId(1);
        client.setName("Leonardo");
        client.setAge(23);
        client.setDocument("545.104.110-57");
        client.setGender(Client.Gender.M);

        agency = new Agency("5th Avenue");
        agency.setId(1);

        account = new Account(client, agency, 50.0, Account.AccountType.COMMON);
    }

    @Test
    public void createValidAccount(){
        Account account = new Account(client, agency, 50.0, Account.AccountType.COMMON);

        Mockito.when(accountService.create(account)).thenReturn(this.account);
        RestAssuredMockMvc
                .given()
                    .body(account)
                    .contentType(ContentType.JSON)
                .when()
                    .post("/account")
                    .prettyPeek()
                .then()
                    .statusCode(201)
                    .body("number", equalTo("1-1"))
                    .body("client.id", equalTo(1))
                    .body("agency.id", equalTo(1))
                    .body("type", equalTo("COMMON"));
    }

    @Test
    public void shouldReturn400ForAccountWithAnyNullField(){
        Account account = new Account();
        account.setClient(client);
        account.setAgency(agency);
        account.setBalance(50.0);
        account.setType(Account.AccountType.COMMON);

        RestAssuredMockMvc
                .given()
                    .body(account)
                    .contentType(ContentType.JSON)
                .when()
                    .post("/account")
                    .prettyPeek()
                .then()
                    .statusCode(400);
    }

    // Invalid parameter is a new account with a negative balance.
    @Test
    public void shouldReturn400ForAccountWithInvalidParameters(){
        Account account = new Account(client, agency, -50.0, Account.AccountType.GOLD);

        RestAssuredMockMvc
                .given()
                    .body(account)
                    .contentType(ContentType.JSON)
                .when()
                    .post("/account")
                    .prettyPeek()
                .then()
                    .statusCode(400);
    }

    // TODO: Refactor account to use a normal number
    @Test
    public void updateBalanceSuccessfully(){
        Account account = SerializationUtils.clone(this.account);
        account.setBalance(70.0);

        Mockito.when(accountService.updateBalance(this.account, 20.0)).thenReturn(account);
        RestAssuredMockMvc
                .given()
                    .body(this.account)
                    .queryParam("amount", 20.0)
                    .contentType(ContentType.JSON)
                .when()
                    .put("/account")
                    .prettyPeek()
                .then()
                    .statusCode(200)
                    .body("number", equalTo("1-1"))
                    .body("balance", equalTo(70.0f));

    }

    @Test
    public void shouldReturn400ForNullIdOnUpdateBalance(){
        Account account = new Account();

        RestAssuredMockMvc
                .given()
                    .body(account)
                    .queryParam("amount", 20.0)
                .contentType(ContentType.JSON)
                    .when()
                    .put("/account")
                    .prettyPeek()
                .then()
                    .statusCode(400);
    }

    @Test
    public void shouldReturn400ForNullAmountParam(){
        Account account = SerializationUtils.clone(this.account);
        account.setBalance(70.0);

        Mockito.when(accountService.updateBalance(this.account, 20.0)).thenReturn(account);
        RestAssuredMockMvc
                .given()
                    .body(this.account)
                    .contentType(ContentType.JSON)
                .when()
                    .put("/account")
                    .prettyPeek()
                .then()
                    .statusCode(400);
    }

    @Test
    public void shouldReturn400ForInvalidUpdateOfBalance(){
        Mockito.when(accountService.updateBalance(this.account, -60.0))
                .thenThrow(UnsupportedOperationException.class);

        RestAssuredMockMvc
                .given()
                    .body(this.account)
                    .queryParam("amount", -60.0)
                    .contentType(ContentType.JSON)
                .when()
                    .put("/account")
                    .prettyPeek()
                .then()
                    .statusCode(400);
    }

    @Test
    public void shouldReturn404ForNotFoundAccount(){
        Client client = SerializationUtils.clone(this.client);
        Agency agency = SerializationUtils.clone(this.agency);

        client.setId(2);
        agency.setId(2);

        Account account = new Account(client, agency, 30.0, Account.AccountType.COMMON);

        Mockito.when(accountService.updateBalance(account, 20.0)).thenThrow(ResourceNotFoundException.class);

        RestAssuredMockMvc
                .given()
                    .body(account)
                    .queryParam("amount", 20.0)
                    .contentType(ContentType.JSON)
                .when()
                    .put("/account")
                    .prettyPeek()
                .then()
                    .statusCode(404);
    }
}
