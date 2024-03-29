package com.rakkiz.taskmanagerclient.view.factory.TaskCard;

import com.rakkiz.taskmanagerclient.TaskManagerApplication;
import com.rakkiz.taskmanagerclient.controller.TaskCardController;
import com.rakkiz.taskmanagerclient.controller.TaskSackController;
import com.rakkiz.taskmanagerclient.data.model.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.net.URL;

/**
 * The ConcreteTaskCardViewFactory is responsible for loading TaskCard views into the application.
 */
public class ConcreteTaskCardViewFactory implements TaskCardViewFactory {


    private static final String NULL_TASK_EXCEPTION_MESSAGE = "Task Model must not be null";
    private static final String NULL_TASK_ID_EXCEPTION_MESSAGE = "Task Model's ID must not be null";

    private static final String FXML_PATH = "fxml/task-card.fxml";
    private final FXMLLoader loader;

    /**
     * Retrieves FXML resource corresponding to TaskCardView.
     *
     * @return FXML resource's URL
     */
    private static URL getFXMLResource() {
        return TaskManagerApplication.class.getResource(FXML_PATH);
    }

    public ConcreteTaskCardViewFactory() {
        loader = new FXMLLoader(getFXMLResource());
    }

    /**
     * Creates a new TaskCard view for a given Task Model.
     *
     * @param task Task Model to be represented by the TaskCard view.
     * @return TaskCard view's root element
     * @throws Exception if the FXML resource fails to load or the task is invalid or null
     */
    public Node create(Task task, TaskSackController taskSackController) throws Exception {
        if (task == null) throw new NullPointerException(NULL_TASK_EXCEPTION_MESSAGE);
        if (task.getTaskId() == null) throw new IllegalArgumentException(NULL_TASK_ID_EXCEPTION_MESSAGE);

        Node card;
        TaskCardController cardController;

        if (task.getDuration() == 0) {
            FXMLLoader doneLoader = new FXMLLoader(TaskManagerApplication.class.getResource("fxml/done-task-card.fxml"));
            card = doneLoader.load();
            cardController = doneLoader.getController();
        } else if (task.isScheduled()) {
            FXMLLoader scheduledLoader = new FXMLLoader(TaskManagerApplication.class.getResource("fxml/scheduled-task-card.fxml"));
            card = scheduledLoader.load();
            cardController = scheduledLoader.getController();
        } else {
            loader.setRoot(null);
            loader.setController(null);
            card = loader.load();
            cardController = loader.getController();
        }

        cardController.setTaskModel(task);
        cardController.setTaskSackController(taskSackController);
        return card;
    }
}
