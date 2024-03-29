package org.sparta.cw.springsecurity.controllers;

import org.sparta.cw.springsecurity.model.entities.Loan;
import org.sparta.cw.springsecurity.model.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoansController {

    @Autowired
    LoanRepository loanRepository;

    @GetMapping("/myLoans")
    public List<Loan> getLoanDetails(@RequestParam int id){
        List<Loan> loans = loanRepository.findByCustomerIdOrderByStartDtDesc(id);
        if (loans != null){
            return loans;
        }else {
            return null;
        }
    }
}
