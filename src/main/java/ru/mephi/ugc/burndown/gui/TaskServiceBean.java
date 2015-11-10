package ru.mephi.ugc.burndown.gui;

import ru.mephi.ugc.burndown.interfaces.TaskService;
import ru.mephi.ugc.burndown.model.Task;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;

@Stateless
@Local(TaskService.class)
public class TaskServiceBean implements TaskService {

    private final static String[] statuses;

    private List<Task> tasks;

    @PersistenceContext(unitName = "Postgres")
    private EntityManager em;


    static {
        statuses = new String[2];
        statuses[0] = "Запланировано";
        statuses[1] = "Выполнено";

    }

    public List<String> getStatuses() {
        return Arrays.asList(statuses);
    }

    public List<Task> getTasksFromDB() {
        em.flush();
        return em.createNamedQuery("Task.getAll").getResultList();
    }

    public void editTask(Task task) {
        em.merge(task);
        em.flush();
    }

    public void addTask(String name, int complexity, String status) {
        Task task = new Task(name, complexity, status);
        em.merge(task);
    }

    public void deleteTask(Task task) {
        try {
            em.remove(getTask(task.getId()));
            //em.flush();
        } catch (Exception e) {
            System.out.println("Ошибка удаления");
        }
    }

    public Task getTask(int taskId) {
        try {
            em.flush();
            return em.find(Task.class, taskId);
        } catch (IllegalArgumentException e) {
            System.out.println("Не найден");
            return null;
        }
    }
}
