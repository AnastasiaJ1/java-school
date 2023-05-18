package repositories.impl;

import filters.SearchFilter;
import model.Employee;
import model.Task;
import repositories.EmployeeRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EmployeeRepositoryImplJDBC implements EmployeeRepository {
    private final String url;
    private final String user;
    private final String password;

    public EmployeeRepositoryImplJDBC(String url, String user, String password) {
        this.url = url;
        this.password = password;
        this.user = user;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    @Override
    public void create(Employee employee) {
        try (Connection connection = getConnection()) {
            String createQuery = "insert into public.\"Employees\"(id, lastname, firstname, surname, jobtitle, account, email, status) values(?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(createQuery);
            preparedStatement.setObject(1, employee.getId());
            preparedStatement.setString(2, employee.getLastname());
            preparedStatement.setString(3, employee.getFirstname());
            preparedStatement.setString(4, employee.getSurname());
            preparedStatement.setString(5, employee.getJobTitle());
            preparedStatement.setLong(6, employee.getAccount());
            preparedStatement.setString(7, employee.getEmail());
            preparedStatement.setString(8, employee.getStatus());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
            throw new RuntimeException();
        }

    }

    @Override
    public void update(Employee employee) {
        try (Connection connection = getConnection()) {
            String updateQuery = "update public.\"Employees\" set  lastname = ?, firstname = ?, surname = ?, jobtitle = ?, account = ?, email = ?, status = ? where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setObject(8, employee.getId());
            preparedStatement.setString(1, employee.getLastname());
            preparedStatement.setString(2, employee.getFirstname());
            preparedStatement.setString(3, employee.getSurname());
            preparedStatement.setString(4, employee.getJobTitle());
            preparedStatement.setLong(5, employee.getAccount());
            preparedStatement.setString(6, employee.getEmail());
            preparedStatement.setString(7, employee.getStatus());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
            throw new RuntimeException();
        }
    }

    @Override
    public Employee getById(UUID id) {

        try (Connection connection = getConnection()) {
            String getByIdQuery = "SELECT * FROM public.\"Employees\" WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(getByIdQuery);
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Employee employee = new Employee();
                employee.setId((UUID) resultSet.getObject("id"));
                employee.setLastname(resultSet.getString("lastname"));
                employee.setFirstname(resultSet.getString("firstname"));
                employee.setSurname(resultSet.getString("surname"));
                employee.setJobTitle(resultSet.getString("jobtitle"));
                employee.setAccount(resultSet.getLong("account"));
                employee.setEmail(resultSet.getString("email"));
                employee.setStatus(resultSet.getString("status"));
                return employee;
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return null;
    }

    @Override
    public List<Employee> getAll() {
        try (Connection connection = getConnection()) {
            String getAllQuery = "SELECT * FROM public.\"Employees\"";
            PreparedStatement preparedStatement = connection.prepareStatement(getAllQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Employee> employeeList = new ArrayList<>();
            while (resultSet.next()) {
                Employee employee = new Employee();
                employee.setId((UUID) resultSet.getObject("id"));
                employee.setLastname(resultSet.getString("lastname"));
                employee.setFirstname(resultSet.getString("firstname"));
                employee.setSurname(resultSet.getString("surname"));
                employee.setJobTitle(resultSet.getString("jobtitle"));
                employee.setAccount(resultSet.getLong("account"));
                employee.setEmail(resultSet.getString("email"));
                employee.setStatus(resultSet.getString("status"));
                employeeList.add(employee);
            }
            return employeeList;
        } catch (SQLException e) {
            e.printStackTrace(System.out);
            throw new RuntimeException();
        }
    }

    @Override
    public boolean deleteById(UUID id) {
        try (Connection connection = getConnection()) {
            String deleteByIdQuery = "DELETE FROM public.\"Employees\" WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteByIdQuery);
            preparedStatement.setObject(1, id);
            int row = preparedStatement.executeUpdate();

            if (row > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
            throw new RuntimeException();
        }
        return false;
    }

    public List<Task> search(SearchFilter searchFilter) {
        try (Connection connection = getConnection()) {
            String searchQuery = "SELECT t.* FROM public.\"Employees\" " +
                    "              left join public.\"Tasks\" as t on public.\"Employees\".id = t.author " +
                    "where public.\"Employees\".lastname = ? and public.\"Employees\".firstname = ? " +
                    "and public.\"Employees\".surname = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(searchQuery);
            preparedStatement.setString(1, searchFilter.getFirstname());
            preparedStatement.setObject(2, searchFilter.getLastname());
            preparedStatement.setObject(3, searchFilter.getSurname());
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Task> taskList = new ArrayList<>();
            while (resultSet.next()) {
                Task task = new Task();
                task.setId((UUID) resultSet.getObject("id"));
                task.setName(resultSet.getString("name"));
                task.setDescription(resultSet.getString("description"));
                task.setExecutor((UUID) resultSet.getObject("executor"));
                task.setLaborCostsHours((int) resultSet.getLong("LaborCostsHours"));
                task.setDeadline(resultSet.getDate("deadline"));
                task.setStatus(resultSet.getString("status"));
                task.setAuthor((UUID) resultSet.getObject("author"));
                task.setCreationDate(resultSet.getDate("CreationDate"));
                task.setChangeDate(resultSet.getDate("ChangeDate"));
                taskList.add(task);
            }
            return taskList;
        } catch (SQLException e) {
            e.printStackTrace(System.out);
            throw new RuntimeException();
        }


    }

    public void deleteAll() {
        try (Connection connection = getConnection()) {
            String deleteByIdQuery = "DELETE FROM public.\"Employees\"";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteByIdQuery);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(System.out);
            throw new RuntimeException();
        }
    }
}
