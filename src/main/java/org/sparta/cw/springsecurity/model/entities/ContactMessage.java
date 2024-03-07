package org.sparta.cw.springsecurity.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "contact_messages")
public class ContactMessage {
    @Id
    @Column(name = "contact_id", nullable = false, length = 50)
    private String contactId;

    @Column(name = "contact_name", nullable = false, length = 50)
    private String contactName;

    @Column(name = "contact_email", nullable = false, length = 100)
    private String contactEmail;

    @Column(name = "subject", nullable = false, length = 500)
    private String subject;

    @Column(name = "message", nullable = false, length = 2000)
    private String message;

    @Column(name = "create_dt")
    private Date createDt;

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }

}