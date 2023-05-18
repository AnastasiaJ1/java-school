import filters.SearchFilter;
import model.Employee;
import model.Task;
import repositories.impl.EmployeeRepositoryImplJDBC;
import repositories.impl.TaskRepositoryImplJDBCPart;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

public class TestEmployeeRepositoryJDBC {
    public static void main(String[] args) {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        UUID id3 = UUID.randomUUID();
        UUID id4 = UUID.randomUUID();
        UUID id5 = UUID.randomUUID();
        EmployeeRepositoryImplJDBC employeeRepository = new EmployeeRepositoryImplJDBC("jdbc:postgresql://localhost:5432/postgres",
                "postgres", "password");
        TaskRepositoryImplJDBCPart taskRepository = new TaskRepositoryImplJDBCPart("jdbc:postgresql://localhost:5432/postgres",
                "postgres", "password");
        taskRepository.deleteAll();
        employeeRepository.deleteAll();
        Employee employee1 = new Employee(id1, "Иванов", "Иван", "Сергеевич"
                , "Разработчик", 1L, "qawse@gmail.com", "Status");
        Employee employee2 = new Employee(id2, "Иванов1", "", "Сергеевич1"
                , "Разработчик", 2L, "qawse1@gmail.com", "Status1");
        Employee employee3 = new Employee(id3, "Иванов2", "Иван2", ""
                , "Разработчик2", 3L, "qawse2@gmail.com", "Status2");
        Employee employee4 = Employee.builder().id(id4)
                .account(4L).status("Status3")
                .lastname("Иванов4").firstname("Иван4").build();

        //create
        employeeRepository.create(employee1);
        employeeRepository.create(employee2);
        employeeRepository.create(employee3);
        employeeRepository.create(employee4);

        //1: getById
        Employee employee5 = employeeRepository.getById(id1);
        System.out.println(employee5);
        System.out.println(employee5.equals(employee1));

        //2: deleteById
        employeeRepository.deleteById(id2);
        Employee employee6 = employeeRepository.getById(id2);
        System.out.println(employee6 == null);

        //3: update
        Employee employee7 = employeeRepository.getById(id3);
        employee7.setEmail(null);
        employeeRepository.update(employee7);
        System.out.println(employee7.equals(employeeRepository.getById(id3)));

        //4: getAll
        List<Employee> employeeList = employeeRepository.getAll();
        System.out.println(employeeList.size() == 3);
        System.out.println(employeeList.size());

        //5: search
        Task task = Task.builder().id(id5).name("Task1").executor(id1)
                .laborCostsHours(40).deadline(new Date(1)).Author(id1).build();


        taskRepository.create(task);
        SearchFilter searchFilter = new SearchFilter("Иванов", "Иван", "Сергеевич", "Task1");
        List<Task> taskList = employeeRepository.search(searchFilter);
        System.out.println(taskList.size() == 1);

    }
}
