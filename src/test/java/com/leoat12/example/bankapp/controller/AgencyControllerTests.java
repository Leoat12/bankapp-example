package com.leoat12.example.bankapp.controller;

import com.leoat12.example.bankapp.model.Agency;
import com.leoat12.example.bankapp.service.AgencyService;
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
@WebMvcTest(AgencyController.class)
public class AgencyControllerTests {

    private Agency agency;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AgencyService agencyService;

    @Before
    public void setUp(){
        RestAssuredMockMvc.mockMvc(mockMvc);
        agency = new Agency("5th Avenue");
        agency.setId(1);
    }

    @Test
    public void createAgency(){
        Agency newAgency = new Agency("5th Avenue");

        when(agencyService.createAgency(eq(newAgency))).thenReturn(agency);
        RestAssuredMockMvc
                .given()
                    .body(newAgency)
                    .contentType(ContentType.JSON)
                .when()
                    .post("/agency")
                    .prettyPeek()
                    .then()
                        .statusCode(201)
                        .body("id", equalTo(1))
                        .body("address", equalTo("5th Avenue"));
    }

    @Test
    public void shouldReturn400ToNullAgency(){
        RestAssuredMockMvc
                .given()
                    .body((String) null)
                    .contentType(ContentType.JSON)
                .when()
                    .post("/agency")
                    .then()
                        .statusCode(400);
    }

    @Test
    public void shouldReturn400ToNullAddress(){
        Agency agencyNull = new Agency(null);

        RestAssuredMockMvc
                .given()
                    .body(agencyNull)
                    .contentType(ContentType.JSON)
                .when()
                    .post("/agency")
                    .then()
                        .statusCode(400);

    }

    @Test
    public void shouldReturn409IfAgencyAlreadyExists(){

        when(agencyService.createAgency(agency))
                .thenThrow(UnsupportedOperationException.class);

        RestAssuredMockMvc
                .given()
                    .body(agency)
                    .contentType(ContentType.JSON)
                .when()
                    .post("/agency")
                    .then()
                        .statusCode(409);

    }
}
