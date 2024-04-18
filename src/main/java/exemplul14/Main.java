package exemplul14;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
class MainApp {
    public static void afisare_tabela(Statement statement, String mesaj) {
        String sql
                ="select * from persoane";
        System.out.println("\n---"
                +mesaj
                +"---");
        try(ResultSet rs
                    =statement.executeQuery(sql)) {
            while (rs.next())
                System.out.println("id=" + rs.getInt(1) + ", nume=" + rs.getString(2) + ",varsta="
                        + rs.getInt(3));
        } catch (SQLException
                e) {
            e.printStackTrace();
        }
    }

    public static void adaugare(Connection connection, int id, String nume, int varsta) {
        String sql="insert into persoane values (?,?,?)";
        try(PreparedStatement ps=connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setString(2, nume);
            ps.setInt(3, varsta);
            int nr_randuri=ps.executeUpdate();
            System.out.println("\nNumar randuri afectate de adaugare="+nr_randuri);
        } catch (SQLException e) {
            System.out.println(sql);
            e.printStackTrace();
        }
    }
    public static void actualizare(Connection connection,int id,int varsta){
        String sql="update persoane set varsta=? where id=?";
        try(PreparedStatement ps=connection.prepareStatement(sql)) {
            ps.setInt(1, varsta);
            ps.setInt(2, id);
            int nr_randuri=ps.executeUpdate();
            System.out.println("\nNumar randuri afectate de modificare="+nr_randuri);
        } catch (SQLException e) {
            System.out.println(sql);
            e.printStackTrace();
        }
    }

    public static void stergere(Connection connection,int id){
        String sql="delete from persoane where id=?";
        try(PreparedStatement ps=connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            int nr_randuri=ps.executeUpdate();
            System.out.println("\nNumar randuri afectate de modificare="+nr_randuri);
        }
        catch (SQLException e) {
            System.out.println(sql);
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
        String url = "jdbc:mysql://localhost:3306/Lab8";
        Scanner scanner = new Scanner(System.in);
        String nume;
        int varsta;
        try {
            Connection connection = DriverManager.getConnection(url, "root", "root1234");
            Statement statement = connection.createStatement();
            afisare_tabela(statement,"Continut initial");

            System.out.println("Dati numele: \n");
            nume= scanner.nextLine();

            System.out.println("Dati varsta: \n");
            varsta = scanner.nextInt();


            adaugare(connection,4,nume,varsta);
            afisare_tabela(statement,"Dupa adaugare");
            actualizare(connection,3,67);
            afisare_tabela(statement,"Dupa modificare");
            stergere(connection,4);
           afisare_tabela(statement,"Dupa stergere");
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}