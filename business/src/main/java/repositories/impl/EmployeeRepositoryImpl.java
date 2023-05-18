package repositories.impl;

import model.Employee;
import repositories.EmployeeRepository;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class EmployeeRepositoryImpl implements EmployeeRepository {
    @Override
    public void create(Employee employee) {
        try {
            Map<UUID, Employee> employeeMap = load();
            employeeMap.put(employee.getId(), employee);
            save(employeeMap);
        } catch (IOException e) {
            e.printStackTrace(System.out);

        }

    }

    @Override
    public void update(Employee employee) {
        try {
            Map<UUID, Employee> employeeMap = load();
            employeeMap.remove(employee.getId());
            employeeMap.put(employee.getId(), employee);
            save(employeeMap);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }

    }

    @Override
    public Employee getById(UUID id) {
        try {
            Map<UUID, Employee> employeeMap = load();
            Employee employee = employeeMap.get(id);
            return employee;
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    @Override
    public List<Employee> getAll() {
        try {
            Map<UUID, Employee> employeeMap = load();
            return new ArrayList<Employee>(employeeMap.values());
        } catch (IOException e) {
            e.printStackTrace(System.out);
            return null;
        }
    }

    @Override
    public boolean deleteById(UUID id) {
        try {
            Map<UUID, Employee> employeeMap = load();
            if (employeeMap.containsKey(id)) {
                employeeMap.remove(id);
                save(employeeMap);
                return true;
            }
            return false;
        } catch (IOException e) {
            e.printStackTrace(System.out);
            return false;
        }
    }

    private void save(Map<UUID, Employee> map) throws IOException {
        Properties properties = new Properties();

        for (Map.Entry<UUID, Employee> entry : map.entrySet()) {
            Employee employee = entry.getValue();
            properties.put(entry.getKey().toString(), employee.toString());
        }

        properties.store(new FileOutputStream("/Users/anastasia/IdeaProjects/digdes/java-school/data_properties"), null);
    }

    private Map<UUID, Employee> load() throws IOException {
        Map<UUID, Employee> map = new HashMap<>();
        Properties properties = new Properties();
        properties.load(new FileInputStream("/Users/anastasia/IdeaProjects/digdes/java-school/data_properties"));

        for (String key : properties.stringPropertyNames()) {
            String employeeString = properties.get(key).toString();
            Employee employee = new Employee();
            employee.fromString(employeeString);
            map.put(UUID.fromString(key), employee);
        }
        return map;
    }
}
