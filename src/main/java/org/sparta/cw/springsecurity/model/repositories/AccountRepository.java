package org.sparta.cw.springsecurity.model.repositories;

import org.sparta.cw.springsecurity.model.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    Account findByCustomerId(int customerId);
}