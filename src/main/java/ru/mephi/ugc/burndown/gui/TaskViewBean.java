package ru.mephi.ugc.burndown.gui;

import org.primefaces.event.RowEditEvent;
import ru.mephi.ugc.burndown.model.Task;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ViewScoped
@ManagedBean(name = "dtEditView")
public class TaskViewBean implements Serializable {

    private String name;

    private int complexity;

    private String status;

    private List<Task> tasks;

    //@ManagedProperty("#{taskService}")
    @EJB
    private TaskService service;

    @PostConstruct
    public void init() {
        tasks = service.getTasksFromDB();
        //tasks = new ArrayList<Task>();
        //tasks.add(new Task("2", "2", 22, "ddd"));
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public List<String> getStatuses() {
        return service.getStatuses();
    }

    public void setService(TaskService service) {
        this.service = service;
    }

    public void onRowEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Task Edited", String.valueOf(((Task) event.getObject()).getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", String.valueOf(((Task) event.getObject()).getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowDelete(Task task) {
        tasks.remove(task);
        FacesMessage msg = new FacesMessage("Task Deleted", String.valueOf(task.getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public String onRowAdd() {
        if (tasks == null) {
            tasks = new ArrayList<Task>();
        }
        Task task = new Task((int) Math.random() * 1000, this.name, this.complexity, this.status);
        tasks.add(task);
        FacesMessage msg = new FacesMessage("Task Added", task.getName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        this.complexity = 0;
        this.name = "";
        this.status = "";
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getComplexity() {
        return complexity;
    }

    public void setComplexity(int complexity) {
        this.complexity = complexity;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public TaskService getService() {
        return service;
    }

    public String getStatus() {
        return status;
    }
}
