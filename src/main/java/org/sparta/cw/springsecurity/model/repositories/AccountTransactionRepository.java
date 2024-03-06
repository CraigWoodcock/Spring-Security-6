package org.sparta.cw.springsecurity.model.repositories;

import org.sparta.cw.springsecurity.model.entities.AccountTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AccountTransactionRepository extends JpaRepository<AccountTransaction, String> {

    List<AccountTransaction> findByCustomerIdOrderByTransactionDtDesc(int customerId);
}