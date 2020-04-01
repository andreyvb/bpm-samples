package com.company.bpmsamples.role;

import com.company.bpmsamples.entity.Contract;
import com.company.bpmsamples.entity.Order;
import com.company.bpmsamples.entity.Task;
import com.haulmont.cuba.security.app.role.AnnotatedRoleDefinition;
import com.haulmont.cuba.security.app.role.annotation.EntityAccess;
import com.haulmont.cuba.security.app.role.annotation.EntityAttributeAccess;
import com.haulmont.cuba.security.app.role.annotation.Role;
import com.haulmont.cuba.security.app.role.annotation.ScreenAccess;
import com.haulmont.cuba.security.entity.EntityOp;
import com.haulmont.cuba.security.role.EntityAttributePermissionsContainer;
import com.haulmont.cuba.security.role.EntityPermissionsContainer;
import com.haulmont.cuba.security.role.ScreenPermissionsContainer;

@Role(name = SampleBasicRoleDefinition.NAME,
description = "Access to entities and screens of the sample")
public class SampleBasicRoleDefinition extends AnnotatedRoleDefinition {

    public static final String NAME = "sample-basic";

    @Override
    @ScreenAccess(screenIds = {
            "application-bpmsamples",
            "bpmsamples$Contract.browse",
            "bpmsamples$Contract.edit",
            "bpmsamples$Order.browse",
            "bpmsamples$Order.edit",
            "bpmsamples$Task.browse",
            "bpmsamples$Task.edit",
            "start-contract-approval-form"
    })
    public ScreenPermissionsContainer screenPermissions() {
        return super.screenPermissions();
    }

    @Override
    @EntityAccess(entityClass = Contract.class,
            operations = {EntityOp.CREATE, EntityOp.READ, EntityOp.UPDATE, EntityOp.DELETE})
    @EntityAccess(entityClass = Order.class,
            operations = {EntityOp.CREATE, EntityOp.READ, EntityOp.UPDATE, EntityOp.DELETE})
    @EntityAccess(entityClass = Task.class,
            operations = {EntityOp.CREATE, EntityOp.READ, EntityOp.UPDATE, EntityOp.DELETE})
    public EntityPermissionsContainer entityPermissions() {
        return super.entityPermissions();
    }

    @Override
    @EntityAttributeAccess(entityClass = Contract.class, modify = "*")
    @EntityAttributeAccess(entityClass = Order.class, modify = "*")
    @EntityAttributeAccess(entityClass = Task.class, modify = "*")
    public EntityAttributePermissionsContainer entityAttributePermissions() {
        return super.entityAttributePermissions();
    }

    @Override
    public String getLocName() {
        return "Sample basic";
    }
}
