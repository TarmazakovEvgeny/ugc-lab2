package ru.mephi.ugc.burndown.gui;

import org.primefaces.event.RowEditEvent;
import ru.mephi.ugc.burndown.interfaces.TaskService;
import ru.mephi.ugc.burndown.model.Task;
import ru.mephi.ugc.burndown.util.CommonUtils;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ManagedBean(name = "item")
@ApplicationScoped
public class TaskView implements Serializable {

    private static final long serialVersionUID = 1L;
    //TASK
    @NotNull(message = "Введите имя задачи")
    private String name;
    @NotNull(message = "Введите сложность")
    private Integer complexity;

    private Date startDate;
    private Date finishDate;
    //------------------

    @NotNull
    private Date startSprintDay;

    @NotNull
    private Date finishSprintDay;

    private List<Task> taskList;

    @EJB
    private TaskService service;

    @ManagedProperty(value = "#{chartView}")
    private ChartView chartView;

    @ManagedProperty(value = "#{commonUtils}")
    private CommonUtils utils;

    @PostConstruct
    public void init() {
        taskList = service.getTasksFromDB();
    }

    public List<String> getStatuses() {
        return service.getStatuses();
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void addAction() {
        service.addTask(this.name, this.complexity, "Planned");
        utils.redirectWithGet();
        FacesMessage msg = new FacesMessage("Task Added", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        taskList = service.getTasksFromDB();
    }

    public void onEdit(RowEditEvent event) {
        Task task = (Task) event.getObject();
        if (task.getStartDate() != null) {
            if (task.getFinishDate() == null) {
                task.setStatus("In Progress");
            } else {
                if (task.getStartDate().after(task.getFinishDate())) {
                    task.setFinishDate(task.getStartDate());
                }
                task.setStatus("Done");
            }
        } else {
            task.setStatus("Planned");
            task.setFinishDate(null);
        }

        service.editTask(task);
        Integer id = ((Task) event.getObject()).getId();
        FacesMessage msg = new FacesMessage("Task " + id + " edited", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onCancel(RowEditEvent event) {
        service.deleteTask((Task) event.getObject());
        taskList.remove(event.getObject());
        Integer id = ((Task) event.getObject()).getId();
        FacesMessage msg = new FacesMessage("Task " + id + " deleted", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);

        taskList = service.getTasksFromDB();
    }

    public Integer getComplexity() {
        return complexity;
    }

    public void setComplexity(Integer complexity) {
        this.complexity = complexity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUtils(CommonUtils utils) {
        this.utils = utils;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public Date getStartSprintDay() {
        return startSprintDay;
    }

    public void setStartSprintDay(Date startSprintDay) {
        this.startSprintDay = startSprintDay;
    }

    public Date getFinishSprintDay() {
        return finishSprintDay;
    }

    public void setFinishSprintDay(Date finishSprintDay) {
        this.finishSprintDay = finishSprintDay;
    }

    public ChartView getChartView() {
        return chartView;
    }

    public void setChartView(ChartView chartView) {
        this.chartView = chartView;
    }
}