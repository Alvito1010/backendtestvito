package com.testvito.backendtestvito.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "bill_groups")
public class BillGroup {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public BillGroup() {
    }

    public BillGroup(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
