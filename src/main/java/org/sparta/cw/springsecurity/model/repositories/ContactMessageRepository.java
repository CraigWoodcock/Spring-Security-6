package org.sparta.cw.springsecurity.model.repositories;

import org.sparta.cw.springsecurity.model.entities.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactMessageRepository extends JpaRepository<ContactMessage, String> {
}