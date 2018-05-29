package com.company.bpmsamples.web.task;

import com.company.bpmsamples.entity.Task;
import com.haulmont.bpm.entity.ProcActor;
import com.haulmont.bpm.entity.ProcInstance;
import com.haulmont.bpm.entity.ProcRole;
import com.haulmont.bpm.gui.action.StartProcessAction;
import com.haulmont.bpm.gui.procactions.ProcActionsFrame;
import com.haulmont.bpm.service.BpmEntitiesService;
import com.haulmont.bpm.service.ProcessRuntimeService;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.data.Datasource;
import com.haulmont.cuba.security.entity.User;
import com.haulmont.cuba.security.global.UserSession;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TaskEdit extends AbstractEditor<Task> {

    @Inject
    protected ProcActionsFrame procActionsFrame;

    public static final String PROCESS_CODE = "taskExecution-1";

    @Inject
    protected Metadata metadata;

    @Inject
    protected UserSession userSession;

    @Inject
    protected BpmEntitiesService bpmEntitiesService;

    @Inject
    protected ProcessRuntimeService processRuntimeService;

    @Inject
    protected Datasource<Task> taskDs;

    @Override
    protected void initNewItem(Task item) {
        super.initNewItem(item);
        item.setInitiator(userSession.getCurrentOrSubstitutedUser());
    }

    @Override
    public void ready() {
        super.ready();
        initProcActionsFrame();
        changeStartProcessBtnCaption();
    }

    private void initProcActionsFrame() {
        procActionsFrame.initializer()
                .standard()
                .setBeforeStartProcessPredicate(() -> {
                    //the predicate creates process actors and sets them to the process instance created by the ProcActionsFrame
                    if (commit()) {
                        ProcInstance procInstance = procActionsFrame.getProcInstance();
                        ProcActor initiatorProcActor = createProcActor("initiator", procInstance, getItem().getInitiator());
                        ProcActor executorProcActor = createProcActor("executor", procInstance, getItem().getExecutor());
                        Set<ProcActor> procActors = new HashSet<>();
                        procActors.add(initiatorProcActor);
                        procActors.add(executorProcActor);
                        procInstance.setProcActors(procActors);
                        return true;
                    } else {
                        return false;
                    }
                })
                .setStartProcessActionProcessVariablesSupplier(() -> {
                    //the supplier returns a map with process variables that will be used by the Activiti process
                    Map<String, Object> processVariables = new HashMap<>();
                    processVariables.put("acceptanceRequired", getItem().getAcceptanceRequired());
                    return processVariables;
                })
                .setAfterStartProcessListener(() -> {
                    //custom listener in addition to the standard behavior refreshes the "taskDs", because the process
                    //automatically updates the "processState" field of the "Task" entity.
                    showNotification(messages.getMessage(ProcActionsFrame.class, "processStarted"));
                    initProcActionsFrame();
                    taskDs.refresh();
                })
                .setAfterCompleteTaskListener(() -> {
                    showNotification(messages.getMessage(ProcActionsFrame.class, "taskCompleted"));
                    initProcActionsFrame();
                    taskDs.refresh();
                })
                .init(PROCESS_CODE, getItem());
    }

    private ProcActor createProcActor(String procRoleCode, ProcInstance procInstance, User user) {
        ProcActor initiatorProcActor = metadata.create(ProcActor.class);
        initiatorProcActor.setUser(user);
        ProcRole initiatorProcRole = bpmEntitiesService.findProcRole(PROCESS_CODE, procRoleCode, View.MINIMAL);
        initiatorProcActor.setProcRole(initiatorProcRole);
        initiatorProcActor.setProcInstance(procInstance);
        return initiatorProcActor;
    }

    /**
     * Method demonstrates how to get and modify process actions automatically created by the ProcActionsFrame
     */
    private void changeStartProcessBtnCaption() {
        StartProcessAction startProcessAction = procActionsFrame.getStartProcessAction();
        if (startProcessAction != null) {
            startProcessAction.setCaption("Start process using ProcActionsFrame");
        }
    }

    /**
     * Method starts the process without {@link ProcActionsFrame}
     */
    public void startProcessProgrammatically() {
        if (commit()) {
            //The ProcInstanceDetails object is used for describing a ProcInstance to be created with its proc actors
            BpmEntitiesService.ProcInstanceDetails procInstanceDetails = new BpmEntitiesService.ProcInstanceDetails(PROCESS_CODE)
                    .addProcActor("initiator", getItem().getInitiator())
                    .addProcActor("executor", getItem().getExecutor())
                    .setEntity(getItem());

            //The created ProcInstance will have two proc actors. None of the entities are persisted yet.
            ProcInstance procInstance = bpmEntitiesService.createProcInstance(procInstanceDetails);

            //A map with process variables that must be passed to the Activiti process instance when it is started.
            //This variable is used in the model to make a decision for one of gateways.
            HashMap<String, Object> processVariables = new HashMap<>();
            processVariables.put("acceptanceRequired", getItem().getAcceptanceRequired());

            //Starts the process. The "startProcess" method automatically persist passed procInstance with its actors,
            //if required
            processRuntimeService.startProcess(procInstance, "Process started programmatically", processVariables);
            showNotification(getMessage("processStarted"));

            //refresh the procActionsFrame to display complete tasks buttons (if there are any for the current user)
            initProcActionsFrame();
        }
    }
}