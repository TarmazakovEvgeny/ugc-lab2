package ru.mephi.ugc.burndown.gui;

import ru.mephi.ugc.burndown.model.Task;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by evgeny on 09.11.15.
 */
@Local
public interface TaskService {
    List<Task> getTasksFromDB();

    void addTask(String name, int complexity, String status);

    void editTask(Task task);

    void deleteTask(int taskId);

    Task getTask(int taskId);

    List<String> getStatuses();
}
