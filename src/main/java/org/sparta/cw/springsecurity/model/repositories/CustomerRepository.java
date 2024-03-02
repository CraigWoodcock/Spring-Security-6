package org.sparta.cw.springsecurity.model.repositories;

import org.sparta.cw.springsecurity.model.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    List<Customer> findByEmail(String email);

    List<Customer> findByUsername(String username);
}