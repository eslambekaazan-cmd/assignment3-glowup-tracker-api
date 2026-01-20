package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import exception.DatabaseOperationException;

public class DatabaseConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/selfcare_db";

    private static final String USER = "postgres";

    private static final String PASSWORD = "1234";

    public static Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");

            return DriverManager.getConnection(URL, USER, PASSWORD);

        } catch (ClassNotFoundException e) {

            throw new DatabaseOperationException("PostgreSQL Driver not found.", e);

        } catch (SQLException e) {

            throw new DatabaseOperationException(
                    "Cannot connect to DB. Check URL/USER/PASSWORD and that PostgreSQL is running.",
                    e
            );
        }
    }
}
