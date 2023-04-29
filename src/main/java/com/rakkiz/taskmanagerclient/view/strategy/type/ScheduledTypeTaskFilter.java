package com.rakkiz.taskmanagerclient.view.strategy.type;

import com.rakkiz.taskmanagerclient.data.model.Task;

public class ScheduledTypeTaskFilter extends TypeTaskFilter {

    @Override
    public boolean filter(Task task) {
        return task.isScheduled();
    }
}