package ru.mephi.ugc.burndown.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

@Entity
@Table(name = "tasks")
@NamedQueries({@NamedQuery(name = "Task.getAll", query = "SELECT t FROM Task t order by t.id")})
public class Task implements Serializable, Comparator<Task> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer complexity;
    private String status;
    private Date startDate;
    private Date finishDate;

    public Task(String item, Integer complexity, String status) {
        this.name = item;
        this.complexity = complexity;
        this.status = status;
    }

    public Task() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String item) {
        this.name = item;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getComplexity() {
        return complexity;
    }

    public void setComplexity(Integer complexity) {
        this.complexity = complexity;
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

    public int compare(Task o1, Task o2) {
        return o1.getFinishDate().compareTo(o2.getFinishDate());
    }
}

