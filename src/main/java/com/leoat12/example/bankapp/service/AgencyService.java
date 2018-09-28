package com.leoat12.example.bankapp.service;

import com.leoat12.example.bankapp.model.Agency;
import com.leoat12.example.bankapp.repository.AgencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AgencyService {

    @Autowired
    private AgencyRepository agencyRepository;

    public Agency createAgency(Agency agency) {

        if(Objects.nonNull(agency.getId())){
            throw new UnsupportedOperationException("Unable to save an existing agency.");
        }

        return agencyRepository.save(agency);
    }

}
