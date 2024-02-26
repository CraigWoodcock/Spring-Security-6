package org.sparta.cw.springsecurity.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoticesController {

    @GetMapping("/notices")
    public String getNotices(){
        return "Notice details returned from DB";
    }
}
