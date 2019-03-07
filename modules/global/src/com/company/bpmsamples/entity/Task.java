package com.company.bpmsamples.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.security.entity.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NamePattern("%s|caption")
@Table(name = "BPMSAMPLES_TASK")
@Entity(name = "bpmsamples$Task")
public class Task extends StandardEntity implements HasProcessState {
    private static final long serialVersionUID = -7916418735947242528L;

    @NotNull
    @Column(name = "CAPTION", nullable = false)
    protected String caption;

    @Column(name = "PROCESS_STATE")
    protected String processState;

    @Column(name = "DESCRIPTION")
    protected String description;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "INITIATOR_ID")
    protected User initiator;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "EXECUTOR_ID")
    protected User executor;

    @Column(name = "ACCEPTANCE_REQUIRED")
    protected Boolean acceptanceRequired = false;

    public void setProcessState(String processState) {
        this.processState = processState;
    }

    public String getProcessState() {
        return processState;
    }


    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getCaption() {
        return caption;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setInitiator(User initiator) {
        this.initiator = initiator;
    }

    public User getInitiator() {
        return initiator;
    }

    public void setExecutor(User executor) {
        this.executor = executor;
    }

    public User getExecutor() {
        return executor;
    }

    public void setAcceptanceRequired(Boolean acceptanceRequired) {
        this.acceptanceRequired = acceptanceRequired;
    }

    public Boolean getAcceptanceRequired() {
        return acceptanceRequired;
    }


}