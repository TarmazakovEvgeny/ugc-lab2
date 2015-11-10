package ru.mephi.ugc.burndown.gui;

import ru.mephi.ugc.burndown.interfaces.TaskService;
import ru.mephi.ugc.burndown.model.Task;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@ManagedBean
@SessionScoped
public class SimpleCrudBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Task> list;
    private Task item = new Task();
    private Task beforeEditItem = null;
    private boolean edit;

    @EJB
    private TaskService service;

    @PostConstruct
    public void init() {
        list = service.getTasksFromDB();
    }

    public void add() {
        // DAO save the add
        item.setId(list.isEmpty() ? 1 : list.get(list.size() - 1).getId() + 1);
        list.add(item);
        item = new Task();
    }

    public void resetAdd() {
        item = new Task();
    }

    public void edit(Task item) {
        beforeEditItem = item.clone();
        this.item = item;
        edit = true;
    }

    public void cancelEdit() {
        this.item.restore(beforeEditItem);
        this.item = new Task();
        edit = false;
    }

    public void saveEdit() {
        // DAO save the edit
        this.item = new Task();
        edit = false;
    }

    public void delete(Task item) throws IOException {
        // DAO save the delete
        list.remove(item);
    }

    public List<Task> getList() {
        return list;
    }

    public Task getItem() {
        return this.item;
    }

    public boolean isEdit() {
        return this.edit;
    }

}