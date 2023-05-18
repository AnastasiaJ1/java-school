package repositories.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Employee;

import repositories.EmployeeRepository;

import java.io.*;
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
            return employeeMap.get(id);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    @Override
    public List<Employee> getAll() {
        try {
            Map<UUID, Employee> employeeMap = load();
            return new ArrayList<>(employeeMap.values());
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

    private synchronized void save(Map<UUID, Employee> map) throws IOException {
        Properties properties = new Properties();

        for (Map.Entry<UUID, Employee> entry : map.entrySet()) {
            Employee employee = entry.getValue();
            StringWriter writer = new StringWriter();
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(writer, employee);
            properties.put(entry.getKey().toString(), writer.toString());
        }
        FileOutputStream file = new FileOutputStream("persistence/src/main/resources/data_properties");
        properties.store(file, null);
        file.close();
    }

    private synchronized Map<UUID, Employee> load() throws IOException {
        Map<UUID, Employee> map = new HashMap<>();
        Properties properties = new Properties();
        FileInputStream file = new FileInputStream("persistence/src/main/resources/data_properties");
        properties.load(file);
        file.close();

        for (String key : properties.stringPropertyNames()) {
            String employeeString = properties.get(key).toString();
            ObjectMapper mapper = new ObjectMapper();
            Employee employee = mapper.readValue(employeeString, Employee.class);
            map.put(UUID.fromString(key), employee);
        }
        return map;
    }
}
