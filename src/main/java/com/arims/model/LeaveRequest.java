package com.arims.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name= "permission")
public class LeaveRequest {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public  Long id;

    public String fullNames;
    public String reason;

    @Column(name = "leave_start")
    LocalDateTime start;

    @Column(name = "leave_end")
    LocalDateTime end;


    public Integer totalDays;


    public boolean approved;


    public LeaveRequest(String fullNames,String reason,LocalDateTime start,LocalDateTime end, Integer totalDays,boolean approved) {
        this.fullNames = fullNames;
        this.reason=reason;
        this.start=start;
        this.end=end;
        this.totalDays=totalDays;
        this.approved= approved;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullNames() {
        return fullNames;
    }

    public void setFullNames(String fullNames) {
        this.fullNames = fullNames;
    }


    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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

    public Integer getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(Integer totalDays) {
        this.totalDays = totalDays;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}
