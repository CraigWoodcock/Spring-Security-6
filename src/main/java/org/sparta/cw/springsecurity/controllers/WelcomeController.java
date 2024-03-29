package org.sparta.cw.springsecurity.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    @SuppressWarnings("SameReturnValue")
    @GetMapping("/welcome")
    public String welcomeMessage(){
        return "Welcome to our app with very basic security";
    }


}