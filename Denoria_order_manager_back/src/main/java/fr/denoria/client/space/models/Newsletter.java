package fr.denoria.client.space.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Newsletter {


    private int id;
    private String subject;
    private String title;
    private String body;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date sendDate = new Date();

    public Newsletter() {
    }

    public Newsletter(String subject, String title, String body, Date sendDate) {
        this.subject = subject;
        this.title = title;
        this.body = body;
        this.sendDate = sendDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    @Override
    public String toString() {
        return "Newsletter{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
