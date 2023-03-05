package com.arims.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name="calendar_events")
@Data
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventid;

    private String text;

    @Column(name = "event_start")
    private LocalDateTime start;

    @Column(name = "event_end")
    private LocalDateTime end;

    private String color;

  

}
