package org.sparta.cw.springsecurity.model.repositories;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.sparta.cw.springsecurity.model.entities.NoticeDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface NoticeDetailRepository extends JpaRepository<NoticeDetail, Integer> {
    @Query(value="from NoticeDetail n where CURDATE() BETWEEN noticBegDt AND noticEndDt")
    List<NoticeDetail> findAllActiveNotices();

}