import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {

    private static final String url = "jdbc:mysql://192.168.0.103:3306/test";
    private static final String user = "root";
    private static final String password = "123";

    private static final DataSource instance = new DataSource();
    private Connection connection = null;

    private DataSource() {
    }

    public static DataSource getInstance() {
        return instance;
    }

    public void connect() throws SQLException {
        System.out.println(String.format("Connect to \"%s\"...", url));
        connection = DriverManager.getConnection(url, user, password);
        System.out.println("Connected to database");
    }

    public boolean isConnected() throws SQLException {
        return connection != null && !connection.isClosed();
    }

    public Connection getConnection() throws SQLException {
        if (!isConnected()) connect();
        return connection;
    }

}
