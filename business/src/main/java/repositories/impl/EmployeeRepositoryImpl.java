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
    public boolean create(Employee employee) {
        try{
            Map<Long, Employee> employeeMap = load();
            employeeMap.put(employee.getId(), employee);
            save(employeeMap);
        } catch (IOException e){
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean update(Employee employee) {
        try{
            Map<Long, Employee> employeeMap = load();
            employeeMap.remove(employee.getId());
            employeeMap.put(employee.getId(), employee);
            save(employeeMap);
        } catch (IOException e){
            return false;
        }

        return true;
    }

    @Override
    public Employee getById(Long id) {
        try{
            Map<Long, Employee> employeeMap = load();
            Employee employee = employeeMap.get(id);
            return employee;
        } catch (IOException e){
            return null;
        }

    }

    @Override
    public List<Employee> getAll() {
        try{
            Map<Long, Employee> employeeMap = load();
            return new ArrayList<Employee>(employeeMap.values());
        } catch (IOException e){
            return null;
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try{
            Map<Long, Employee> employeeMap = load();
            if(employeeMap.containsKey(id)) {
                employeeMap.remove(id);
                save(employeeMap);
                return true;
            }
            return false;
        } catch (IOException e){
            return false;
        }
    }
    private void save(Map<Long, Employee> map) throws IOException {
        Properties properties = new Properties();

        for (Map.Entry<Long,Employee> entry : map.entrySet()) {
            Employee employee = entry.getValue();
            properties.put(entry.getKey().toString(), employee.toString());
        }

        properties.store(new FileOutputStream("/Users/anastasia/IdeaProjects/digdes/java-school/data_properties"), null);
    }

    private Map<Long, Employee> load() throws IOException {
        Map<Long, Employee> map = new HashMap<Long, Employee>();
        Properties properties = new Properties();
        properties.load(new FileInputStream("/Users/anastasia/IdeaProjects/digdes/java-school/data_properties"));

        for (String key : properties.stringPropertyNames()) {
            String employeeString = properties.get(key).toString();
            Employee employee = new Employee();
            employee.fromString(employeeString);
            map.put(Long.parseLong(key), employee);
        }
        return map;
    }
}
