package com.leoat12.example.bankapp.service;

import com.leoat12.example.bankapp.exception.ResourceNotFoundException;
import com.leoat12.example.bankapp.model.Client;
import com.leoat12.example.bankapp.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public Client createClient(Client client){
        return clientRepository.save(client);
    }

    public Client updateClient(Client client){
        if(client.getId() == null)
            throw new UnsupportedOperationException("The client to be updated need to have an ID");
        if(!clientRepository.existsById(client.getId()))
            throw new ResourceNotFoundException("The client must exist to be updated");

        return clientRepository.save(client);
    }
}
