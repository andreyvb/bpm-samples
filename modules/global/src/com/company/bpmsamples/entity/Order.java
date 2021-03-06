package com.company.bpmsamples.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@NamePattern("%s|number")
@Table(name = "BPMSAMPLES_ORDER")
@Entity(name = "bpmsamples$Order")
public class Order extends StandardEntity {
    private static final long serialVersionUID = -4130624387513116307L;

    @Column(name = "NUMBER_")
    protected String number;

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }


}