package ru.mephi.ugc.burndown.model;

import javax.persistence.*;

@Entity
@Table(name = "tasks")
@NamedQueries({@NamedQuery(name = "Task.getAll", query = "SELECT t FROM Task t order by t.id")})
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;
    @Column(name = "complexity")
    private int complexity;
    @Column(name = "status")
    private String status;

    public Task(int id, String name, int complexity, String status) {
        super();
        this.id = id;
        this.name = name;
        this.complexity = complexity;
        this.status = status;
    }

    public Task(String name, int salary, String status) {
        this.name = name;
        this.complexity = complexity;
        this.status = status;
    }

    public Task() {
    }

    @Override
    public Task clone() {
        return new Task(id, name, complexity, status);
    }

    public void restore(Task task) {
        this.id = task.getId();
        this.name = task.getName();
        this.complexity = task.getComplexity();
        this.status = task.getStatus();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", complexity=" + complexity +
                ", status='" + status + '\'' +
                '}';
    }
}
