package com.testvito.backendtestvito.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.*;

@Entity
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private BillGroup group;

    @ManyToOne
    @JoinColumn(name = "paid_by_id", nullable = false)
    private Participant paidBy;

    @ManyToMany
    @JoinTable(
        name = "expense_participants",
        joinColumns = @JoinColumn(name = "expense_id"),
        inverseJoinColumns = @JoinColumn(name = "participant_id")
    )
    private Set<Participant> splitAmong = new HashSet<>();

    public Expense() {
    }
    
    public Long getId() {
    return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BillGroup getGroup() {
        return group;
    }

    public void setGroup(BillGroup group) {
        this.group = group;
    }

    public Participant getPaidBy() {
        return paidBy;
    }

    public void setPaidBy(Participant paidBy) {
        this.paidBy = paidBy;
    }

    public Set<Participant> getSplitAmong() {
        return splitAmong;
    }

    public void setSplitAmong(Set<Participant> splitAmong) {
        this.splitAmong = splitAmong;
    }
    
}
