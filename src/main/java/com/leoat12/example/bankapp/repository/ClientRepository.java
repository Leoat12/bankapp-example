package com.leoat12.example.bankapp.repository;

import com.leoat12.example.bankapp.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    Optional<Client> findByName(String name);

    Optional<Client> findByDocument(String document);
}
