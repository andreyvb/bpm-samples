package com.company.bpmsamples.web.order;

import com.haulmont.cuba.gui.screen.*;
import com.company.bpmsamples.entity.Order;

@UiController("bpmsamples$Order.browse")
@UiDescriptor("order-browse.xml")
@LookupComponent("ordersTable")
@LoadDataBeforeShow
public class OrderBrowse extends StandardLookup<Order> {
}