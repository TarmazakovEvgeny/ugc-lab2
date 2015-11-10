package ru.mephi.ugc.burndown.gui;

import org.primefaces.event.RowEditEvent;
import ru.mephi.ugc.burndown.model.Task;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;

@SessionScoped
@ManagedBean(name = "dtEditView")
public class TaskViewBean implements Serializable {

    private String name;

    private int complexity;

    private String status;

    private List<Task> tasks;

    private boolean edit;

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
        return service.getTasksFromDB();
    }

    public List<String> getStatuses() {
        return service.getStatuses();
    }

    public void setService(TaskServiceBean service) {
        this.service = service;
    }

    public void onRowEdit(RowEditEvent event) {
        service.editTask(new Task(name, complexity, status));
        FacesMessage msg = new FacesMessage("Task Edited", String.valueOf(((Task) event.getObject()).getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
        edit = true;
        //tasks = service.getTasksFromDB();
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", String.valueOf(((Task) event.getObject()).getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
        edit = false;
        //tasks = service.getTasksFromDB();
    }

    public void onRowDelete(Task task) {
        service.deleteTask(task.getId());
        FacesMessage msg = new FacesMessage("Task Deleted", String.valueOf(task.getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
        tasks = service.getTasksFromDB();
    }

    public void onRowAdd() {
        service.addTask(this.name, this.complexity, this.status);
        FacesMessage msg = new FacesMessage("Task Added", this.name);
        FacesContext.getCurrentInstance().addMessage(null, msg);
        this.complexity = 0;
        this.name = "";
        this.status = "";
        //tasks = service.getTasksFromDB();
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

    public boolean isEdit() {
        return edit;
    }
}
