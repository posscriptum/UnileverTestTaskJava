import org.sqlite.JDBC;

import java.sql.*;
import java.util.*;

public class DbHandler {
    private static final String CON_STR = "jdbc:sqlite:identifier.sqlite";

    private static DbHandler instance = null;

    public static synchronized DbHandler getInstance() throws SQLException {
        if (instance == null)
            instance = new DbHandler();
        return instance;
    }

    private Connection connection;

    private DbHandler() throws SQLException {
        // Register driver
        DriverManager.registerDriver(new JDBC());
        // connect to Db
        this.connection = DriverManager.getConnection(CON_STR);
    }

    // Add new Db if applicable
    public void createDb() {
        try (Statement statement = this.connection.createStatement()){
            // SQL statement for creating a new table
            String sql = "CREATE TABLE IF NOT EXISTS unileverTable (\n"
                    + "	id integer PRIMARY KEY,\n"
                    + "	years integer NOT NULL,\n"
                    + "	months integer NOT NULL,\n"
                    + "	days integer NOT NULL,\n"
                    + "	hours integer NOT NULL,\n"
                    + "	minutes integer NOT NULL,\n"
                    + "	seconds integer NOT NULL,\n"
                    + "	random_value integer NOT NULL\n"
                    + ");";
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //get all entries
    public List<UnileverData> getAllEntries() {

        try (Statement statement = this.connection.createStatement()) {
            List<UnileverData> unileverListItems = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery("SELECT years, months, days, hours, minutes," +
                                                             " seconds, random_value FROM unileverTable");

            while (resultSet.next()) {
                Calendar calendar = GregorianCalendar.getInstance();
                calendar.set(
                        resultSet.getInt("years"),
                        resultSet.getInt("months"),
                        resultSet.getInt("days"),
                        resultSet.getInt("hours"),
                        resultSet.getInt("minutes"),
                        resultSet.getInt("seconds"));
                UnileverData tempItem = new UnileverData(calendar);
                tempItem.setRandomValue(resultSet.getInt("random_value"));
                unileverListItems.add(tempItem);
            }

            return unileverListItems;

        } catch (SQLException e) {
            e.printStackTrace();
            // return empty collection if error
            return Collections.emptyList();
        }
    }

    // add row to Db
    public void addItemToTableUnilever(UnileverData ul) {

        try (PreparedStatement statement = this.connection.prepareStatement(
                "INSERT INTO unileverTable('years', 'months', 'days', 'hours', 'minutes', 'seconds', 'random_value')" +
                        " VALUES(?, ?, ?, ?, ?, ?, ?)")) {
            statement.setObject(1, ul.getYear());
            statement.setObject(2, ul.getMonth());
            statement.setObject(3, ul.getDay());
            statement.setObject(4, ul.getHour());
            statement.setObject(5, ul.getMinute());
            statement.setObject(6, ul.getSecond());
            statement.setObject(7, ul.getRandomValue());

            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
