import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Repository {

    private static final Logger log = LoggerFactory.getLogger(Repository.class);

    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_CONNECTION = "jdbc:h2:./target/test.db";
    private static final String DB_USER = "";
    private static final String DB_PASSWORD = "";

    public static void createDatabase() {
        createTables();
        insertUsers();
        insertAccountData();
    }

    private static void createTables() {
        executeSql("CREATE TABLE IF NOT EXISTS LOGIN(id int primary key, username varchar(255), password varchar(255))");
        executeSql("CREATE TABLE IF NOT EXISTS ACCOUNT(id int primary key, username varchar(255), amount int)");
        executeSql("CREATE TABLE IF NOT EXISTS TODO_ITEM(id int primary key, user varchar(255), todo varchar(255))");
        executeSql("CREATE SEQUENCE IF NOT EXISTS TODO_ITEM_SEQ");
    }

    private static void insertUsers() {
        executeSql(
                "MERGE INTO LOGIN(id, username, password) VALUES(1, 'per', 'per')",
                "MERGE INTO LOGIN(id, username, password) VALUES(2, 'pål', 'pål')",
                "MERGE INTO LOGIN(id, username, password) VALUES(3, 'espen', 'espen')");

    }

    private static void insertAccountData() {
        executeSql(
                "MERGE INTO ACCOUNT(id, username, amount) VALUES(1, 'per', 10)",
                "MERGE INTO ACCOUNT(id, username, amount) VALUES(2, 'pål', 20)",
                "MERGE INTO ACCOUNT(id, username, amount) VALUES(3, 'espen', 100)");
    }

    private static void executeSql(String... sqls) {
        Connection connection = getDBConnection();
        Statement stmt = null;
        try {
            connection.setAutoCommit(false);
            stmt = connection.createStatement();
            for (String s : sqls) {
                stmt.execute(s);
            }
            stmt.close();
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    public static boolean login(String username, String password) {
        String sql = "select * from LOGIN where username = '" + username + "' and password='" + password + "'";
        // String sql = "select * from LOGIN where username = ? and password=?";

        log.info("Executing sql: " + sql);

        Connection connection = getDBConnection();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // PreparedStatement ps = connection.prepareStatement(sql);
            // ps.setString(1, username);
            // ps.setString(2, password);
            // ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    private static Connection getDBConnection() {
        try {
            Class.forName(DB_DRIVER);
            return DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void transferMoney(String from, String to, String amount) {
        // TODO
    }

    public static String getAmount(String username) {
        String sql = "select amount from ACCOUNT where username = ?";

        Connection connection = getDBConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString(1);
            } else {
                return "";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    public static TodoItem getTodoItem(String id) {
        String sql = "select id, user, todo from TODO_ITEM where id = ?";

        Connection connection = getDBConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String dbId = rs.getString(1);
                String dbUser = rs.getString(2);
                String dbTodo = rs.getString(3);
                return new TodoItem(dbId, dbUser, dbTodo);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    public static List<TodoItem> getTodoItems(String user) {
        String sql = "select id, user, todo from TODO_ITEM where user = ?";

        Connection connection = getDBConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, user);
            ResultSet rs = ps.executeQuery();

            List<TodoItem> todoItems = new ArrayList<>();
            while (rs.next()) {
                String dbId = rs.getString(1);
                String dbUser = rs.getString(2);
                String dbTodo = rs.getString(3);
                todoItems.add(new TodoItem(dbId, dbUser, dbTodo));
            }
            return todoItems;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    public static void saveTodoItem(TodoItem todoItem) {
        String sql = "insert into TODO_ITEM(id, user, todo) VALUES(TODO_ITEM_SEQ.nextval,?,?)";

        Connection connection = getDBConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, todoItem.getUser());
            ps.setString(2, todoItem.getTodo());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    public static void updateTodoItem(TodoItem todoItem) {
        String sql = "update TODO_ITEM set todo = ? where id = ? and user = ?";

        Connection connection = getDBConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, todoItem.getTodo());
            ps.setString(2, todoItem.getId());
            ps.setString(3, todoItem.getUser());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // ignore
            }
        }

    }

    public static void deleteTodoItem(String id) {
        String sql = "delete from TODO_ITEM where id = ?";

        Connection connection = getDBConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // ignore
            }
        }
    }

}
