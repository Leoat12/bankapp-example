package com.leoat12.example.bankapp.controller;

import com.leoat12.example.bankapp.exception.ResourceNotFoundException;
import com.leoat12.example.bankapp.model.Client;
import com.leoat12.example.bankapp.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/client")
public class ClientController {

    private ClientService clientService;

    public ClientController(ClientService clientService){
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<Client> save(@RequestBody @Valid Client client, BindingResult result){

        if(result.hasErrors())
            return ResponseEntity.badRequest().build();

        return ResponseEntity.status(201).body(clientService.createClient(client));
    }

    @PutMapping
    public ResponseEntity<Client> update(@RequestBody @Valid Client client, BindingResult result){

        if(result.hasErrors() || client.getId() == null)
            return ResponseEntity.badRequest().build();

        try {
            return ResponseEntity.ok(clientService.updateClient(client));
        } catch (ResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
}
