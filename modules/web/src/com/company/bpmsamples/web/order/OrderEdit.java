package com.company.bpmsamples.web.order;

import com.haulmont.bpm.gui.procactionsfragment.ProcActionsFragment;
import com.haulmont.cuba.gui.components.Fragment;
import com.haulmont.cuba.gui.screen.*;
import com.company.bpmsamples.entity.Order;

import javax.inject.Inject;

@UiController("bpmsamples$Order.edit")
@UiDescriptor("order-edit.xml")
@EditedEntityContainer("orderDc")
@LoadDataBeforeShow
public class OrderEdit extends StandardEditor<Order> {

    private static final String PROCESS_CODE = "orderDelivery-1";

    @Inject
    private ProcActionsFragment procActionsFragment;

    @Subscribe
    private void onBeforeShow(BeforeShowEvent event) {
        procActionsFragment.initializer()
                .standard()
                .init(PROCESS_CODE, getEditedEntity());
    }
}