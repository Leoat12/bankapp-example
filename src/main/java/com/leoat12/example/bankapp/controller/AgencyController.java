package com.leoat12.example.bankapp.controller;

import com.leoat12.example.bankapp.model.Agency;
import com.leoat12.example.bankapp.service.AgencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/agency")
public class AgencyController {

    private final AgencyService agencyService;

    @Autowired
    public AgencyController(AgencyService agencyService) {
        this.agencyService = agencyService;
    }

    @PostMapping
    public ResponseEntity<Agency> createAgency(@RequestBody  @Valid Agency agency, BindingResult bindingResult){

        if(bindingResult.hasErrors())
            return ResponseEntity.badRequest().build();

        try {
            return ResponseEntity.status(201)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(agencyService.createAgency(agency));
        } catch (UnsupportedOperationException e){
            return ResponseEntity.status(409).build();
        }
    }
}
