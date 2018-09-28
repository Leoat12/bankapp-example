package com.leoat12.example.bankapp.model;

import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class AgencyTests {

    @Test
    public void createAgency(){
        Agency agency = new Agency("5th Avenue");
        assertThat(agency.getAddress(), equalTo("5th Avenue"));
    }
}
