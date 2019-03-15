package com.company.bpmsamples.web.forms.contract;

import com.company.bpmsamples.entity.Contract;
import com.haulmont.bpm.entity.ProcActor;
import com.haulmont.bpm.entity.ProcInstance;
import com.haulmont.bpm.entity.ProcRole;
import com.haulmont.bpm.gui.form.ProcForm;
import com.haulmont.bpm.service.BpmEntitiesService;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.MetadataTools;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.model.CollectionPropertyContainer;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.security.entity.User;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

@UiController("start-contract-approval-form")
@UiDescriptor("start-contract-approval-form.xml")
public class StartContractApprovalForm extends Screen implements ProcForm {

    private static final String PROCESS_CODE = "contractApproval-1";

    @Inject
    protected TextField<Integer> automaticApprovalPeriodField;

    @Inject
    protected TextField<String> contractField;

    @Inject
    protected Metadata metadata;

    @Inject
    protected BpmEntitiesService bpmEntitiesService;

    @Inject
    protected LookupField<User> approverLookup;

    @Inject
    private InstanceContainer<ProcInstance> procInstanceDc;

    @Inject
    private MetadataTools metadataTools;

    @Inject
    protected CollectionPropertyContainer<ProcActor> procActorsDc;

    @Inject
    protected CollectionLoader<User> usersDl;

    private Contract contract;

    private ProcInstance procInstance;

    @Subscribe
    protected void onInit(InitEvent event) {
        ScreenOptions options = event.getOptions();
        if (options instanceof MapScreenOptions) {
            procInstance = (ProcInstance) ((MapScreenOptions) options).getParams().get("procInstance");
            contract = (Contract) ((MapScreenOptions) options).getParams().get("contract");
        }
    }

    @Subscribe
    private void onBeforeShow(BeforeShowEvent event) {
        usersDl.load();
        procInstanceDc.setItem(procInstance);
        automaticApprovalPeriodField.setValue(30);
        contractField.setValue(metadataTools.getInstanceName(contract));
    }

    @Subscribe("commit")
    private void onCommitClick(Button.ClickEvent event) {
        ProcActor approverProcActor = createProcActor("approver", procInstance, approverLookup.getValue());
        procActorsDc.getMutableItems().add(approverProcActor);
        getScreenData().getDataContext().commit();
        close(WINDOW_COMMIT_AND_CLOSE_ACTION);
    }

    private ProcActor createProcActor(String procRoleCode, ProcInstance procInstance, User user) {
        ProcActor procActor = metadata.create(ProcActor.class);
        procActor.setUser(user);
        ProcRole initiatorProcRole = bpmEntitiesService.findProcRole(PROCESS_CODE, procRoleCode, View.MINIMAL);
        procActor.setProcRole(initiatorProcRole);
        procActor.setProcInstance(procInstance);
        return procActor;
    }

    @Subscribe("cancel")
    private void onCancelClick(Button.ClickEvent event) {
        close(WINDOW_CLOSE_ACTION);
    }


    private String makeTimerExpression(int period) {
        return "PT" + period + "S";
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
}