package com.company.bpmsamples.web.task;

import com.haulmont.cuba.gui.screen.*;
import com.company.bpmsamples.entity.Task;

@UiController("bpmsamples$Task.browse")
@UiDescriptor("task-browse.xml")
@LookupComponent("tasksTable")
@LoadDataBeforeShow
public class TaskBrowse extends StandardLookup<Task> {
}