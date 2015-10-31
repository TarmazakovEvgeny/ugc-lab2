package ru.mephi.ugc.burndown.model;

import javax.persistence.*;

@Entity
@Table(name = "employee")
@NamedQueries({@NamedQuery(name = "Employee.getAll", query = "SELECT e FROM Employee e order by e.id")})
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;

    @Column(name = "salary")
    private double salary;

    @Column(name = "position")
    private String position;

    public Employee(int id, String name, double salary, String position) {
        super();
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.position = position;
    }

    public Employee(String name, double salary, String position) {
        super();
        this.name = name;
        this.salary = salary;
        this.position = position;
    }

    public Employee() {
        super();
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

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Employee [id=" + id + ", name=" + name + ", salary=" + salary + ", position=" + position + "]";
    }
}
