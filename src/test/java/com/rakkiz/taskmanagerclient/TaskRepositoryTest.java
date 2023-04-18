package com.rakkiz.taskmanagerclient;

import com.rakkiz.taskmanagerclient.data.DerbyTaskRepository;
import com.rakkiz.taskmanagerclient.data.TaskRepository;
import com.rakkiz.taskmanagerclient.data.model.Task;
import junit.framework.TestCase;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.SQLException;
import java.time.Instant;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TaskRepositoryTest extends TestCase {
    TaskRepository repository;

    @BeforeAll
    public void testConstruction() throws SQLException {
        repository = new DerbyTaskRepository();
    }

    @Test
    public void testIDAssignedOnCreation() throws Exception {
        Task t = new Task();
        repository.create(t);
        assertNotNull("Task ID should be assigned after insertion (i.e. not null)", t.getTaskId());

        //  Reverting changes
        repository.delete(t);
    }

    @Test
    public void testAllNewlyCreatedTaskReadability() throws Exception {

        Task[] myTasks = new Task[]{
                new Task(null, "my", "newTask", Instant.now()),
                new Task(null, "mine", "Task", Instant.now()),
                new Task(null, "mine", "newTask", Instant.now()),
                new Task(null, "my", "Task", Instant.now())
        };
        for (Task t : myTasks) {
            repository.create(t);
        }
        List<Task> list = repository.getAllTasks();
        for (Task t : myTasks) {
            int index = list.indexOf(t);
            assertTrue("newly created Task must exist in new task listing", index != -1);
            Task tRead = list.get(index);

            assertEquals("ID should not change on creation or read", tRead.getTaskId(), t.getTaskId());
            assertEquals("Name should not change on creation or read", tRead.getName(), t.getName());
            assertEquals("Description should not change on creation or read", tRead.getDescription(), t.getDescription());
            assertEquals("Creation date should not change on creation or read", tRead.getCreationTime(), t.getCreationTime());
        }
        for (Task t : myTasks) {
            repository.delete(t);
        }
    }

    @Test
    public void testIDUnassignedOnDelete() throws Exception {
        Task t = new Task();
        repository.create(t);
        repository.delete(t);
        assertNull("Task ID should be unassigned after deletion (i.e. null)", t.getTaskId());
    }

    @Test
    public void testNoDataChangeOnCreation() throws Exception {
        Task t = new Task();
        repository.create(t);

        Task tRead = repository.getByTaskId(t.getTaskId());

        assertEquals("ID should not change on creation", tRead.getTaskId(), t.getTaskId());
        assertEquals("Name should not change on creation", tRead.getName(), t.getName());
        assertEquals("Description should not change on creation", tRead.getDescription(), t.getDescription());
        assertEquals("Creation date should not change on creation", tRead.getCreationTime(), t.getCreationTime());

        //  Reverting changes
        repository.delete(t);
    }

    @Test
    public void testDataChangeOnUpdate() throws Exception {

        int ID;
        String targetName = "This is my name";
        String targetDescription = "This is the new description";
        {
            Task t = new Task();
            repository.create(t);
            ID = t.getTaskId();
            t.setName(targetName);
            t.setDescription(targetDescription);
            repository.update(t);
        }
        {
            Task t = repository.getByTaskId(ID);

            assertNotSame(t.getDescription(), Task.DEFAULT_DESCRIPTION);
            assertNotSame(t.getName(), Task.DEFAULT_NAME);
            assertEquals(t.getName(), targetName);
            assertEquals(t.getDescription(), targetDescription);

            repository.delete(t);
        }

    }


    @AfterAll
    public void testClosure() throws Exception {
        repository.close();
    }
}