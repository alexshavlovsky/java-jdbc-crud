import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    private final DataSource dataSource;

    public EmployeeDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Employee adaptResultSet(ResultSet resultSet) throws SQLException {
        return new Employee(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("department"),
                resultSet.getBoolean("working")
        );
    }

    private void setColumns(PreparedStatement statement, Employee employee, boolean isUpdate) throws SQLException {
        statement.setString(1, employee.getName());
        statement.setString(2, employee.getDepartment());
        statement.setBoolean(3, employee.isWorking());
        if (isUpdate) statement.setLong(4, employee.getId());
    }

    public List<Employee> getAll() throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM employees");
            List<Employee> result = new ArrayList<>();
            while (resultSet.next()) result.add(adaptResultSet(resultSet));
            return result;
        }
    }

    public Employee save(Employee employee) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement statement =
                    conn.prepareStatement("INSERT INTO employees (name, department, working) VALUES (?, ?, ?)",
                            Statement.RETURN_GENERATED_KEYS);
            setColumns(statement, employee, false);
            if (statement.executeUpdate() != 1) throw new SQLException("Saving employee failed, no rows affected.");
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) employee.setId(generatedKeys.getLong(1));
                else throw new SQLException("Saving employee failed, no ID obtained.");
            }
        }
        return employee;
    }

    public boolean update(Employee employee) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement statement =
                    conn.prepareStatement("UPDATE employees SET name=?, department=?, working=? WHERE id=?");
            setColumns(statement, employee, true);
            return (statement.executeUpdate() != 1);
        }
    }

    public boolean delete(Employee employee) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement statement =
                    conn.prepareStatement("DELETE FROM employees WHERE id=?");
            statement.setLong(1, employee.getId());
            return statement.executeUpdate() == 1;
        }
    }

}
