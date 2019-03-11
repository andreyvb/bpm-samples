package com.company.bpmsamples.web.forms.contract;

import com.company.bpmsamples.entity.Contract;
import com.haulmont.bpm.entity.ProcInstance;
import com.haulmont.bpm.gui.form.ProcForm;
import com.haulmont.bpm.gui.procactor.ProcActorsFrame;
import com.haulmont.cuba.core.global.MetadataTools;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

@UiController("start-contract-approval-form")
@UiDescriptor("start-contract-approval-form.xml")
public class StartContractApprovalForm extends Screen implements ProcForm {

    @Inject
    protected TextField<Integer> automaticApprovalPeriodField;

    @Inject
    protected TextField contractField;

    @Inject
    protected ProcActorsFrame procActorsFrame;

    @Inject
    private InstanceContainer<ProcInstance> procInstanceDc;

    @Inject
    private MetadataTools metadataTools;

    private Contract contract;

    private ProcInstance procInstance;

    @Subscribe
    private void onBeforeShow(BeforeShowEvent event) {
        procInstanceDc.setItem(procInstance);
        automaticApprovalPeriodField.setValue(30);
        contractField.setValue(metadataTools.getInstanceName(contract));
        procActorsFrame.setProcInstance(procInstance);
    }

    public void setContract(Contract contractEntity) {
        contract = contractEntity;
    }

    public void setProcInstance(ProcInstance procInstanceEntity) {
        procInstance = procInstanceEntity;
    }

    @Subscribe("commit")
    private void onCommitClick(Button.ClickEvent event) {
        getScreenData().getDataContext().commit();
        close(WINDOW_COMMIT_AND_CLOSE_ACTION);
    }

    @Subscribe("cancel")
    private void onCancelClick(Button.ClickEvent event) {
        close(WINDOW_CLOSE_ACTION);
    }

    @Override
    public String getComment() {
        return null;
    }

    private String makeTimerExpression(int period) {
        return "PT" + period + "S";
    }

    @Override
    public Map<String, Object> getFormResult() {
        HashMap<String, Object> processVariables = new HashMap<>();
        processVariables.put("automaticApprovalPeriod", makeTimerExpression(automaticApprovalPeriodField.getValue()));
        return processVariables;
    }
}