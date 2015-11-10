package ru.mephi.ugc.burndown.interfaces;

import ru.mephi.ugc.burndown.model.Task;

import javax.ejb.Local;
import java.util.List;

@Local
public interface TaskService {
    List<Task> getTasksFromDB();

    void addTask(String name, int complexity, String status);

    void editTask(Task task);

    void deleteTask(Task task);

    Task getTask(int taskId);

    List<String> getStatuses();
}
