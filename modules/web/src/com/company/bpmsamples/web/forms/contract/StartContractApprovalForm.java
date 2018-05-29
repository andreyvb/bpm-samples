package com.company.bpmsamples.web.forms.contract;

import com.company.bpmsamples.entity.Contract;
import com.haulmont.bpm.entity.ProcInstance;
import com.haulmont.bpm.gui.form.ProcForm;
import com.haulmont.bpm.gui.procactor.ProcActorsFrame;
import com.haulmont.cuba.gui.WindowParam;
import com.haulmont.cuba.gui.components.AbstractWindow;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.data.Datasource;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * This BPM process form contains the {@link ProcActorsFrame} and component for displaying an information about the
 * {@link Contract} entity that was passed in windows parameters
 */
public class StartContractApprovalForm extends AbstractWindow implements ProcForm {

    @Inject
    protected Datasource<ProcInstance> procInstanceDs;

    //this window parameter was passed from the com.company.bpmsamples.web.contract.ContractEdit
    @WindowParam(required = true)
    private Contract contract;

    @Inject
    protected TextField automaticApprovalPeriodField;

    @Inject
    protected TextField contractField;

    @Inject
    protected ProcActorsFrame procActorsFrame;

    //procInstance window parameter is passed to every process form if it is opened by the ProcActionsFrame
    @WindowParam(name = "procInstance", required = true)
    protected ProcInstance procInstance;

    @Override
    public void ready() {
        super.ready();
        procInstanceDs.setItem(procInstance);
        initCustomUI();
        initProcActorsFrame();
    }

    private void initCustomUI() {
        automaticApprovalPeriodField.setValue(30);
        contractField.setValue(contract.getInstanceName());
    }

    private void initProcActorsFrame() {
        procActorsFrame.setProcInstance(procInstance);
    }

    @Override
    public String getComment() {
        return null;
    }

    /**
     * The method returns a map of process variables that will be added to the process wen it is started. The
     * "automaticApprovalPeriod" variable is used as a an expression for the period of the boundary timer event
     */
    @Override
    public Map<String, Object> getFormResult() {
        HashMap<String, Object> processVariables = new HashMap<>();
        processVariables.put("automaticApprovalPeriod", makeTimerExpression(automaticApprovalPeriodField.getValue()));
        return processVariables;
    }

    private String makeTimerExpression(int period) {
        return "PT" + period + "S";
    }

    public void onCommit() {
        //need to commit the context because we want to persist process actors added by the ProcActorsFrame
        getDsContext().commit();
        close(COMMIT_ACTION_ID);
    }

    public void onCancel() {
        close(CLOSE_ACTION_ID);
    }
}