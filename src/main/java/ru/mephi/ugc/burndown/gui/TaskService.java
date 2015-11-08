package ru.mephi.ugc.burndown.gui;

import ru.mephi.ugc.burndown.model.Task;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Stateless
public class TaskService {

    private final static String[] statuses;

    @PersistenceContext(unitName = "Postgres")
    private EntityManager em;

    static {
        statuses = new String[2];
        statuses[0] = "Запланировано";
        statuses[1] = "Выполнено";
    }

    public List<Task> createCars(int size) {
        List<Task> list = new ArrayList<Task>();
        for (int i = 0; i < size; i++) {
            list.add(new Task(getRandomId(), "", i, "Запланировано"));
        }

        return list;
    }

    private int getRandomId() {
        return (int) Math.random() * 100;
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

    public void addTask(String name, int salary, String status) {
        Task emp = new Task(name, salary, status);
        em.merge(emp);
    }

    public void deleteTask(int taskId) {
        try {
            em.remove(getTask(taskId));
            em.flush();
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
