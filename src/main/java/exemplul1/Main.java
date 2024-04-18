package exemplul1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
class Main {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/Lab8";
        String sql
                = "select * from persoane";
        Connection connection = DriverManager.getConnection(url, "root", "root1234");
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next())
            System.out.println("id=" + rs.getInt("Id") + ", nume= "
                    + rs.getString("nume") + ",varsta=" + rs.getInt("varsta"));
        connection.close();
        statement.close();
        rs.close();
    }
}