package com.arims.model;


import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


@Entity
@Table(name="tasks")
public class Task implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskid;
    private String title;
    private String description;
    @Column(name = "task_start")
    private LocalDateTime start;
    @Column(name = "task_end")
    private LocalDateTime end;



    @Column(nullable=false, columnDefinition="boolean default false")
    private boolean assigned;


    @ColumnDefault("false")
    private boolean completed;


    public Task() {

    }


    public Task(String title, String description, LocalDateTime start, LocalDateTime end, Boolean assigned, Boolean completed) {
        this.title = title;
        this.description = description;
        this.start = start;
        this.end = end;
        this.assigned = assigned;
        this.completed = completed;
    }

    public Long getTaskid() {
        return taskid;
    }

    public void setTaskid(Long taskid) {
        this.taskid = taskid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }


    public Boolean getAssigned() {
        return assigned;
    }

    public void setAssigned(Boolean assigned) {
        this.assigned = assigned;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }


}