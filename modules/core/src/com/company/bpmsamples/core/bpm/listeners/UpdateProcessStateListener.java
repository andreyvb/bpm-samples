package com.company.bpmsamples.core.bpm.listeners;

import com.company.bpmsamples.entity.HasProcessState;
import com.google.common.base.Strings;
import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.Metadata;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.event.ActivitiActivityEvent;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * The listener updates the "processState" field of the {@link HasProcessState} with the name of current BPM process
 * node. This listener is used in the "taskExecution-1" BPM process
 */
public class UpdateProcessStateListener implements ActivitiEventListener {

    private static final Logger log = LoggerFactory.getLogger(UpdateProcessStateListener.class);

    private Metadata metadata;

    public UpdateProcessStateListener() {
        metadata = AppBeans.get(Metadata.class);
    }

    @Override
    public void onEvent(ActivitiEvent event) {
        RuntimeService runtimeService = event.getEngineServices().getRuntimeService();
        String executionId = event.getExecutionId();
        UUID entityId = (UUID) runtimeService.getVariable(executionId, "entityId");
        String entityName = (String) runtimeService.getVariable(executionId, "entityName");
        if (entityId == null) {
            log.error("Cannot update process state. entityId variable is null");
            return;
        }
        if (Strings.isNullOrEmpty(entityName)) {
            log.error("Cannot update process state. entityName variable is null");
            return;
        }
        MetaClass metaClass = metadata.getClass(entityName);
        if (metaClass == null) {
            log.error("Cannot update process state. MetaClass {} not found", entityName);
            return;
        }

        if (!HasProcessState.class.isAssignableFrom(metaClass.getJavaClass())) {
            log.error("{} doesn't implement the HasProcessState");
            return;
        }

        switch (event.getType()) {
            case ACTIVITY_STARTED:
                //activityName is the name of the current element taken from the process model
                String activityName = ((ActivitiActivityEvent) event).getActivityName();
                if (!Strings.isNullOrEmpty(activityName)) {
                    updateProcessState(metaClass, entityId, activityName);
                }
                break;
        }
    }

    /**
     * Method updates the process state of the entity linked with the process instance
     */
    private void updateProcessState(MetaClass metaClass, UUID entityId, String processState) {
        Persistence persistence = AppBeans.get(Persistence.class);
        try (Transaction tx = persistence.getTransaction()) {
            EntityManager em = persistence.getEntityManager();
            Entity entity = em.find(metaClass.getJavaClass(), entityId);
            if (entity != null) {
                ((HasProcessState) entity).setProcessState(processState);
            } else {
                log.error("Entity {} with id {} not found", metaClass.getName(), entityId);
            }
            tx.commit();
        }
    }

    @Override
    public boolean isFailOnException() {
        return false;
    }
}
