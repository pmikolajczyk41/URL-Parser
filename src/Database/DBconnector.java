package Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBconnector {
    private static final String Driver = "org.sqlite.JDBC";
    private static final String DB_URL = "jdbc:sqlite:visited_links.db";

    private static Connection connection;
    private static Statement statement;

    public static void connect() {
        try {
            Class.forName(DBconnector.Driver);
        } catch (ClassNotFoundException e) {
            System.out.println("DB: No driver for JDBC");
            //e.printStackTrace();
            return;
        }

        try {
            connection = DriverManager.getConnection(DB_URL);
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println("DB: Connection error");
            //e.printStackTrace();
            return;
        }

        createTable();
    }

    private static void createTable() {
        String createVisitedLinks = "CREATE TABLE IF NOT EXISTS visited_links (link varchar NOT NULL);";

        try {
            statement.execute(createVisitedLinks);
        } catch (SQLException e) {
            System.out.println("DB: Error during creating table");
            //e.printStackTrace();
        }
    }

    public static void insertLink(String link) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO visited_links VALUES (?)");
            preparedStatement.setString(1, link);
            preparedStatement.execute();

        } catch (SQLException e) {
            System.out.println("DB: Insert error");
            //e.printStackTrace();
        }
    }

    public static void clear() {
        try {
            String deleteCommand = "DELETE FROM 'visited_links'";
            statement.execute(deleteCommand);
        } catch (SQLException e) {
            System.out.println("DB: Clearing error");
            // /e.printStackTrace();
        }
    }

    public static List<VisitedLink> VisitedLinks() {
        List<VisitedLink> Links = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM visited_links");
            while (resultSet.next())
                Links.add(new VisitedLink(resultSet.getString("link")));
        } catch (SQLException e) {
            System.out.println("DB: Select error");
            //e.printStackTrace();
            return null;
        }
        return Links;
    }

    public static void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("DB: Closing error");
            //e.printStackTrace();
        }
    }
}
