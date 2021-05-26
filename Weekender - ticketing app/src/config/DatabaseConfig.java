package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/weekendermain";
    private static final String USER = "root";
    private static final String PASSWORD = "Weekender@tickets1234";

    private static Connection dbConnection;
    private DatabaseConfig(){}

    /// definim metodele prin care se obtine conexiunea la baza de date
    public static Connection getDbConnection(){
        try {
            //daca nu exista sau este inchis vreau sa obtin o conexiune
            if (dbConnection == null || dbConnection.isClosed())
                 // administreaza incarcarea driverelor
                 dbConnection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
        }catch(SQLException throwables){
            throwables.printStackTrace();
        }
        return dbConnection;
    }
    public static void closedDbConnection(){
        try {
            if (dbConnection != null && !dbConnection.isClosed())
                dbConnection.close();
        }catch(SQLException throwables){
            throwables.printStackTrace();
        }
    }
}
