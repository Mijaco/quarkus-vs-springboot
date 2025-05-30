package com.redhat.example.entity;

import jakarta.persistence.*;

@Entity(name = "T_CALCULATOR")
public class Calculator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long value1;
    private String method;
    private Long value2;
    private Long total;

    public Calculator() {
    }

    public Calculator(Long value1, String method, Long value2, Long total) {
        this.value1 = value1;
        this.method = method;
        this.value2 = value2;
        this.total = total;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getValue1() {
        return value1;
    }

    public void setValue1(Long value1) {
        this.value1 = value1;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Long getValue2() {
        return value2;
    }

    public void setValue2(Long value2) {
        this.value2 = value2;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
