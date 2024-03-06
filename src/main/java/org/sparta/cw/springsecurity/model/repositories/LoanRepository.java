package org.sparta.cw.springsecurity.model.repositories;

import org.sparta.cw.springsecurity.model.entities.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Integer> {

        List<Loan> findByCustomerIdOrderByStartDtDesc(int customerId);
}