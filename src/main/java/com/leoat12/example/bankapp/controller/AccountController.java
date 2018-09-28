package com.leoat12.example.bankapp.controller;

import com.leoat12.example.bankapp.exception.ResourceNotFoundException;
import com.leoat12.example.bankapp.model.Account;
import com.leoat12.example.bankapp.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<Account> save(@RequestBody @Valid Account account, BindingResult result){

        if(result.hasErrors()){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.status(201).body(accountService.create(account));
    }

    @PutMapping
    public ResponseEntity<Account> updateBalance(@RequestBody Account account, @RequestParam("amount") Double amount){
        try {
            return ResponseEntity.ok(accountService.updateBalance(account, amount));
        } catch (UnsupportedOperationException e){
            return ResponseEntity.badRequest().build();
        } catch (ResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
}
