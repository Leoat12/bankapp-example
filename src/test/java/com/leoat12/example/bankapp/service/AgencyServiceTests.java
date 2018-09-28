package com.leoat12.example.bankapp.service;

import com.leoat12.example.bankapp.model.Agency;
import com.leoat12.example.bankapp.repository.AgencyRepository;
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
public class AgencyServiceTests {


    @Autowired
    private AgencyService agencyService;

    @MockBean
    private AgencyRepository agencyRepository;

    @TestConfiguration
    static class agencyServiceConfiguration{

        @Bean
        public AgencyService agencyService(){
            return new AgencyService();
        }
    }

    @Before
    public void setUp(){
        Agency agency = new Agency("5th Avenue");

        Agency agencyWithId = new Agency("5th Avenue");
        agencyWithId.setId(1);

        Mockito.when(agencyRepository.save(agency))
                .thenReturn(agencyWithId);
    }

    @Test
    public void createAgency(){
        Agency agency = new Agency("5th Avenue");

        agency = agencyService.createAgency(agency);

        assertThat(agency.getId(), equalTo(1));
        assertThat(agency.getAddress(), equalTo("5th Avenue"));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void tryToModifyAnExistingAgency(){
        Agency agency = new Agency("5th Avenue");
        agency.setId(1);

        agency = agencyService.createAgency(agency);
    }
}
