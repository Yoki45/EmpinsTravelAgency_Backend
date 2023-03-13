package com.arims.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name= "articles")
public class Research {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    public Long id;
    public String title;

    public String  body;

    public Research(){

    }


    public Research(String title,String body) {
        this.title= title;
        this.body =body;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
