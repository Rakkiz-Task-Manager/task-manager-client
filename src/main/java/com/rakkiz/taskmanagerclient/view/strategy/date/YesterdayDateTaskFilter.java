package com.rakkiz.taskmanagerclient.view.strategy.date;

import com.rakkiz.taskmanagerclient.data.model.Task;

import java.time.LocalDate;
import java.time.ZoneId;

public class YesterdayDateTaskFilter extends DateTaskFilter {

    /**
     * Scheduled date should be yesterday
     *
     * @param task Model for reference
     * @return boolean
     */
    @Override
    public boolean filter(Task task) {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        return task.getScheduledTime().atZone(ZoneId.systemDefault()).toLocalDate().isEqual(yesterday);
    }
}
