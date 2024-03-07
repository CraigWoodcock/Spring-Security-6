package org.sparta.cw.springsecurity.controllers;

import org.sparta.cw.springsecurity.model.entities.Card;
import org.sparta.cw.springsecurity.model.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CardsController {

    @Autowired
    CardRepository cardRepository;
    @GetMapping("/myCards")
    public List<Card> getCardDetails(@RequestParam int id){
        List<Card> cards = cardRepository.findByCustomerId(id);
        if (cards != null){
            return cards;
        }else {
            return null;
        }
    }
}
