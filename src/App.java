import java.sql.SQLException;

public class App {

    public static void main(String[] args) {
        EmployeeDAO employeeDAO = new EmployeeDAO(DataSource.getInstance());
        try {
            Employee employee = new Employee(0, "Alex", "Emergency", true);
            employeeDAO.save(employee);
            System.out.println(employeeDAO.getAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
