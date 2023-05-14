package repositories;

import model.Employee;

import java.util.List;

public interface EmployeeRepository {

//    create, update, getById, getAll, deleteById
    boolean create(Employee employee);
    boolean update(Employee employee);
    Employee getById(Long id);
    List<Employee> getAll();
    boolean deleteById(Long id);
}
