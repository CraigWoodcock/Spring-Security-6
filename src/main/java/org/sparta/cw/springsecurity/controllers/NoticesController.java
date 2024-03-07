package org.sparta.cw.springsecurity.controllers;

import org.sparta.cw.springsecurity.model.entities.NoticeDetail;
import org.sparta.cw.springsecurity.model.repositories.NoticeDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
public class NoticesController {

    @Autowired
    NoticeDetailRepository noticeRepository;

    @GetMapping("/notices")
    public ResponseEntity<List<NoticeDetail>> getNotice(){
        List<NoticeDetail> notices = noticeRepository.findAllActiveNotices();
        if (notices != null){
            return ResponseEntity.ok()
                    .cacheControl(CacheControl.maxAge(80, TimeUnit.SECONDS))
                    .body(notices);
        }else{
            return null;
        }
    }

}
