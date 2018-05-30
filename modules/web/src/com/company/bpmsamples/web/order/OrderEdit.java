package com.company.bpmsamples.web.order;

import com.haulmont.bpm.gui.procactions.ProcActionsFrame;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.company.bpmsamples.entity.Order;

import javax.inject.Inject;

/**
 * The screen demonstrates the simplest way of working with process actions - using the {@link ProcActionsFrame}
 * initialized with the {@link ProcActionsFrame.Initializer#standard()} method
 */
public class OrderEdit extends AbstractEditor<Order> {

    private static final String PROCESS_CODE = "orderDelivery-1";

    @Inject
    protected ProcActionsFrame procActionsFrame;

    @Override
    public void ready() {
        super.ready();
        //the standard() initialization adds predicates and listeners to start and complete task events. The predicates
        //commit the entity editor, listeners show notifications and re-initialize procActionsFrame after the process
        //action is performed
        procActionsFrame.initializer()
                .standard()
                .init(PROCESS_CODE, getItem());
    }
}