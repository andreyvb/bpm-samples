package com.company.bpmsamples.web.contract;

import com.haulmont.bpm.gui.procactionsfragment.ProcActionsFragment;
import com.haulmont.cuba.gui.screen.*;
import com.company.bpmsamples.entity.Contract;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

@UiController("bpmsamples$Contract.edit")
@UiDescriptor("contract-edit.xml")
@EditedEntityContainer("contractDc")
@LoadDataBeforeShow
public class ContractEdit extends StandardEditor<Contract> {

    private static final String PROCESS_CODE = "contractApproval-1";

    @Inject
    protected ProcActionsFragment procActionsFragment;

    @Subscribe
    private void onBeforeShow(BeforeShowEvent event) {

        procActionsFragment.initializer()
                .standard()
                .setStartProcessActionScreenParametersSupplier(() -> {
                    Map<String, Object> screenParams = new HashMap<>();
                    screenParams.put("contract", getEditedEntity());
                    return screenParams;
                })
                .init(PROCESS_CODE, getEditedEntity());
    }
}