package repositories.impl;

import model.Task;

import java.sql.*;

public class TaskRepositoryImplJDBCPart {
    private final String url;
    private final String user;
    private final String password;

    public TaskRepositoryImplJDBCPart(String url, String user, String password) {
        this.url = url;
        this.password = password;
        this.user = user;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public void create(Task task) {
        try (Connection connection = getConnection()) {
            String createQuery = "insert into public.\"Tasks\"(id, \"name\", executor, author, laborcostshours, deadline) values(?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(createQuery);
            preparedStatement.setObject(1, task.getId());
            preparedStatement.setString(2, task.getName());
            preparedStatement.setObject(3, task.getExecutor());
            preparedStatement.setObject(4, task.getAuthor());
            preparedStatement.setLong(5, task.getLaborCostsHours());
            preparedStatement.setDate(6, (Date) task.getDeadline());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
            throw new RuntimeException();
        }

    }

    public void deleteAll() {
        try (Connection connection = getConnection()) {
            String deleteByIdQuery = "DELETE FROM public.\"Tasks\"";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteByIdQuery);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(System.out);
            throw new RuntimeException();
        }
    }
}
