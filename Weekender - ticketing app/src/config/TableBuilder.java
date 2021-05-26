package config;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TableBuilder {
    public static void createTable()   {

        String createTableSql = "CREATE TABLE IF NOT EXISTS tickets" +
                "(id int(6) PRIMARY KEY, section int(6), row varchar(2), seatNo int(6), refundPolicy varchar(50), event_id int(6), type varchar(2), status varchar(20)," +
                "foreign key(event_id) references events(id) on delete cascade );";

        Connection connection = DatabaseConfig.getDbConnection();
        try {
            Statement statement = connection.createStatement();
            statement.execute(createTableSql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
