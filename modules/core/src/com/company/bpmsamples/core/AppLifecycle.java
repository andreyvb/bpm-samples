package com.company.bpmsamples.core;

import com.company.bpmsamples.core.bpm.ModelDeployer;
import com.haulmont.cuba.core.global.Events;
import com.haulmont.cuba.core.sys.events.AppContextInitializedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component("bpmsamples_AppLifecycle")
public class AppLifecycle {

    @Inject
    private ModelDeployer modelDeployer;

    @EventListener(AppContextInitializedEvent.class) // notify after AppContext is initialized
    @Order(Events.LOWEST_PLATFORM_PRECEDENCE + 100)  // run after all platform listeners
    public void onAppContextInitialized() {
        modelDeployer.deployAllModels();
    }
}
