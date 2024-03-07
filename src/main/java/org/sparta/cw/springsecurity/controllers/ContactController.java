package org.sparta.cw.springsecurity.controllers;

import org.sparta.cw.springsecurity.model.entities.ContactMessage;
import org.sparta.cw.springsecurity.model.repositories.ContactMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Random;

@RestController
public class ContactController {

    @Autowired
    ContactMessageRepository contactRepository;

    @PostMapping("/contact")
    public ContactMessage saveContactInquiryDetails(@RequestBody ContactMessage contact){
        contact.setContactId(getServiceReqNumber());
        contact.setCreateDt(new Date(System.currentTimeMillis()));
        return contactRepository.save(contact);
    }

    private String getServiceReqNumber() {
        Random random = new Random();
        int randomNum = random.nextInt(999999999-9999)+9999;
        return "SR"+randomNum;
    }
}
