import model.Employee;
import repositories.impl.EmployeeRepositoryImpl;

import java.util.List;

public class TestEmployeeRepository {
    public static void main(String[] args) {
        EmployeeRepositoryImpl employeeRepository = new EmployeeRepositoryImpl();
        Employee employee1 = new Employee(1L, "Иванов", "Иван", "Сергеевич"
                , "Разработчик", 1L, "qawse@gmail.com", "Status");
        Employee employee2 = new Employee(2L, "Иванов1", "", "Сергеевич1"
                , "Разработчик", 2L, "qawse1@gmail.com", "Status1");
        Employee employee3 = new Employee(3L, "Иванов2", "Иван2", ""
                , "Разработчик2", 3L, "qawse2@gmail.com", "Status2");
        Employee employee4 = new Employee(4L);

        //create
        employeeRepository.create(employee1);
        employeeRepository.create(employee2);
        employeeRepository.create(employee3);
        employeeRepository.create(employee4);

        //1: getById
        Employee employee5 = employeeRepository.getById(1L);
        System.out.println(employee5);
        System.out.println(employee5.equals(employee1));

        //2: deleteById
        employeeRepository.deleteById(2L);
        Employee employee6 = employeeRepository.getById(2L);
        System.out.println(employee6 == null);

        //3: update
        Employee employee7 = employeeRepository.getById(3L);
        employee7.setEmail(null);
        employeeRepository.update(employee7);
        System.out.println(employee7.equals(employeeRepository.getById(3L)));

        //4: getAll
        List<Employee> employeeList = employeeRepository.getAll();
        System.out.println(employeeList.size() == 3);
    }
}
