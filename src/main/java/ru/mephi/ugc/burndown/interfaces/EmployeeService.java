package ru.mephi.ugc.burndown.interfaces;

import ru.mephi.ugc.burndown.model.Employee;

import javax.ejb.Local;
import java.util.List;

@Local
public interface EmployeeService {
    List<Employee> getEmployee();

    void addEmployee(String name, double salary, String position);

    void editEmployee(Employee employee);

    void deleteEmployee(int employeeId);

    Employee getEmployee(int employeeId);
}
