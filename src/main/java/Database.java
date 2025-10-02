import java.sql.*;
import javafx.scene.control.Label;

public class Database {

    private static final String URL = "jdbc:mysql://localhost:3306/javafx_mariadb";
    private static final String USER = "root";
    private static final String PASSWORD = "Test12";

    public static void saveTemperature(Double celsius, Double fahrenheit, Double kelvin, String source, Label statusLabel) {
        String sql = "INSERT INTO temperature_log (celsius, fahrenheit, kelvin, source) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (celsius != null) {
                stmt.setDouble(1, celsius);
            } else {
                stmt.setNull(1, java.sql.Types.DOUBLE);
            }

            if (fahrenheit != null) {
                stmt.setDouble(2, fahrenheit);
            } else {
                stmt.setNull(2, java.sql.Types.DOUBLE);
            }

            if (kelvin != null) {
                stmt.setDouble(3, kelvin);
            } else {
                stmt.setNull(3, java.sql.Types.DOUBLE);
            }

            stmt.setString(4, source);
            stmt.executeUpdate();
            statusLabel.setText("Saved to database!");

        } catch (SQLException e) {
            statusLabel.setText("DB Error: " + e.getMessage());
            System.out.println(e.getMessage());
        }
    }
}
