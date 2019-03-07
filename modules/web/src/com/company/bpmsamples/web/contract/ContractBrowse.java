package com.company.bpmsamples.web.contract;

import com.haulmont.cuba.gui.screen.*;
import com.company.bpmsamples.entity.Contract;

@UiController("bpmsamples$Contract.browse")
@UiDescriptor("contract-browse.xml")
@LookupComponent("contractsTable")
@LoadDataBeforeShow
public class ContractBrowse extends StandardLookup<Contract> {
}