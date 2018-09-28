package com.leoat12.example.bankapp.repository;

import com.leoat12.example.bankapp.model.Agency;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AgencyRepositoryTests {

    @Autowired
    private AgencyRepository agencyRepository;

    @Test
    public void createAnAccount(){
        agencyRepository.save(new Agency("5th Avenue"));
        List<Agency> agencyList = agencyRepository.findAll();
        assertThat(agencyList.stream().filter(a -> a.getAddress().equals("5th Avenue")).count(), greaterThan(0l));
    }
}
