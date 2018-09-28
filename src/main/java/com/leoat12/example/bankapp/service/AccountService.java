package com.leoat12.example.bankapp.service;

import com.leoat12.example.bankapp.exception.ResourceNotFoundException;
import com.leoat12.example.bankapp.model.Account;
import com.leoat12.example.bankapp.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;


    public Account create(Account account) {
        return accountRepository.save(account);
    }

    public Account updateBalance(Account account, Double usd) {
        Optional<Account> accountOptional = accountRepository.findById(account.getNumber());

        if(accountOptional.isPresent()) {
            Account accountFromDB = accountOptional.get();
            accountFromDB.updateBalance(usd);
            return accountRepository.save(accountFromDB);
        }
        else {
            throw new ResourceNotFoundException("Account not found");
        }
    }
}
