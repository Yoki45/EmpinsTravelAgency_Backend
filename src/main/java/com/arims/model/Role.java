package com.arims.model;


import jakarta.persistence.*;
import lombok.NoArgsConstructor;



@Entity
@Table(name="role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

 /*   private ERole name;

    public Role() {

    }

    public Role(Role name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ERole getName() {
        return name;
    }

    public void setName(ERole name) {
        this.name = name;
    }*/
}





