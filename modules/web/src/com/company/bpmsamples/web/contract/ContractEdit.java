package com.company.bpmsamples.web.contract;

import com.company.bpmsamples.entity.Contract;
import com.haulmont.bpm.gui.procactions.ProcActionsFrame;
import com.haulmont.cuba.gui.components.AbstractEditor;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

public class ContractEdit extends AbstractEditor<Contract> {

    private static final String PROCESS_CODE = "contractApproval-1";

    @Inject
    protected ProcActionsFrame procActionsFrame;

    @Override
    public void ready() {
        super.ready();
        initProcActionsFrame();
    }

    private void initProcActionsFrame() {
        procActionsFrame.initializer()
                .standard()
                //screen parameters supplier returns a map of window parameters that may be used in the process form
                .setStartProcessActionScreenParametersSupplier(() -> {
                    Map<String, Object> screenParams = new HashMap<>();
                    screenParams.put("contract", getItem());
                    return screenParams;
                })
                .init(PROCESS_CODE, getItem());
    }
}