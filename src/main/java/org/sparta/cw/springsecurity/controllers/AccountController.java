package org.sparta.cw.springsecurity.controllers;

import org.sparta.cw.springsecurity.model.entities.Account;
import org.sparta.cw.springsecurity.model.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/myAccount")
   public Account getAccountDetails(@RequestParam int id){
        Account account = accountRepository.findByCustomerId(id);
        if (account != null){
            return account;
        }else {
            return null;
        }
    }
}
