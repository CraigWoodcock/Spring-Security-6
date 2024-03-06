package org.sparta.cw.springsecurity.controllers;

import org.sparta.cw.springsecurity.model.entities.AccountTransaction;
import org.sparta.cw.springsecurity.model.repositories.AccountTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BalanceController {

    @Autowired
    private AccountTransactionRepository accountTransactionRepository;

    @GetMapping("/myBalance")
    public List<AccountTransaction> getBalanceDetails(@RequestParam int id){
           List<AccountTransaction> accountTransactions = accountTransactionRepository.findByCustomerIdOrderByTransactionDtDesc(id);
           if (accountTransactions != null){
               return accountTransactions;
           }else {
               return null;
           }

    }
}
