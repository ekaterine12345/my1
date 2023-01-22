package connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCUtil {
    private static final String Db_URL = "jdbc:mysql://localhost:3306/database1";
    private static final String Db_USER = "myuser1";
    private static final String Db_PASSWORD="password";
    private static Connection connection;
    private static Statement statement;
    public JDBCUtil() {
    }

    static {
        try {
            connection = DriverManager.getConnection(Db_URL, Db_USER, Db_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static Connection getConnection() {
        return connection;
    }

    public static Statement getStatement(){
        if (statement == null){
            try{
                statement = connection.createStatement();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return statement;
    }

}
