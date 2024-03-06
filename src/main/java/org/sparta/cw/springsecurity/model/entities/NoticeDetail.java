package org.sparta.cw.springsecurity.model.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "notice_details")
public class NoticeDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id", nullable = false)
    private Integer id;

    @Column(name = "notice_summary", nullable = false, length = 200)
    private String noticeSummary;

    @Column(name = "notice_details", nullable = false, length = 500)
    private String noticeDetails;

    @Column(name = "notic_beg_dt", nullable = false)
    private LocalDate noticBegDt;

    @Column(name = "notic_end_dt")
    private LocalDate noticEndDt;

    @Column(name = "create_dt")
    private LocalDate createDt;

    @Column(name = "update_dt")
    private LocalDate updateDt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNoticeSummary() {
        return noticeSummary;
    }

    public void setNoticeSummary(String noticeSummary) {
        this.noticeSummary = noticeSummary;
    }

    public String getNoticeDetails() {
        return noticeDetails;
    }

    public void setNoticeDetails(String noticeDetails) {
        this.noticeDetails = noticeDetails;
    }

    public LocalDate getNoticBegDt() {
        return noticBegDt;
    }

    public void setNoticBegDt(LocalDate noticBegDt) {
        this.noticBegDt = noticBegDt;
    }

    public LocalDate getNoticEndDt() {
        return noticEndDt;
    }

    public void setNoticEndDt(LocalDate noticEndDt) {
        this.noticEndDt = noticEndDt;
    }

    public LocalDate getCreateDt() {
        return createDt;
    }

    public void setCreateDt(LocalDate createDt) {
        this.createDt = createDt;
    }

    public LocalDate getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(LocalDate updateDt) {
        this.updateDt = updateDt;
    }

}