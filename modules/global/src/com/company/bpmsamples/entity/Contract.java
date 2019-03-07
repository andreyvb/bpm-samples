package com.company.bpmsamples.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@NamePattern("%s|number")
@Table(name = "BPMSAMPLES_CONTRACT")
@Entity(name = "bpmsamples$Contract")
public class Contract extends StandardEntity {
    private static final long serialVersionUID = 2283797719899291085L;

    @NotNull
    @Column(name = "NUMBER_", nullable = false)
    protected String number;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_")
    protected Date date;

    @Column(name = "AMOUNT")
    protected BigDecimal amount;

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }


}