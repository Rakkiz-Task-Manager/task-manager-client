package com.rakkiz.management.task.client.view.strategy.type;

import com.rakkiz.management.task.client.data.model.Task;

public class ScheduledTypeTaskFilter extends TypeTaskFilter {

    /**
     * Task should be scheduled
     *
     * @param task Model for reference
     * @return boolean
     */
    @Override
    public boolean filter(Task task) {
        return task.isScheduled();
    }
}