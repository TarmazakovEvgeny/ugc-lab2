package ru.mephi.ugc.burndown;

import ru.mephi.ugc.burndown.interfaces.EmployeeService;
import ru.mephi.ugc.burndown.model.Employee;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
@Local(EmployeeService.class)
public class EmployeeServiceBean implements EmployeeService {

    @PersistenceContext(unitName = "Postgres")
    private EntityManager em;

    public List<Employee> getEmployee() {
        em.flush();
        return em.createNamedQuery("Employee.getAll").getResultList();
    }

    public void editEmployee(Employee employee) {
        em.merge(employee);
        em.flush();
    }

    public void addEmployee(String name, double salary, String position) {
        Employee emp = new Employee(name, salary, position);
        em.merge(emp);
    }

    public void deleteEmployee(int employeeId) {
        try {
            em.remove(getEmployee(employeeId));
            em.flush();
        } catch (Exception e) {
            System.out.println("Ошибка удаления");
        }
    }

    public Employee getEmployee(int employeeId) {
        try {
            em.flush();
            return em.find(Employee.class, employeeId);
        } catch (IllegalArgumentException e) {
            System.out.println("Не найден");
            return null;
        }
    }

}
