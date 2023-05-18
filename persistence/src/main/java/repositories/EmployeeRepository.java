package repositories;

import model.Employee;

import java.util.List;
import java.util.UUID;

public interface EmployeeRepository {

    void create(Employee employee);

    void update(Employee employee);

    Employee getById(UUID id);

    List<Employee> getAll();

    boolean deleteById(UUID id);
}
