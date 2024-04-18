package lucrare_ex2;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/*
Adăugarea unei excursii în tabela excursii. Înainte de a efectua adăugarea se va
verifica dacă persoana căreia îi aparține excursia a fost introdusă în tabela
persoane. Dacă nu a fost introdusă se va afișa un mesaj corespunzător. Datele
excursiei se vor prelua de la tastatură
 */
class Main{
    public static void afisare_tabela_excursi(Statement statement, String mesaj) {
        String sql
                ="select * from excursii";
        System.out.println("\n---"
                +mesaj
                +"---");
        try(ResultSet rs
                    =statement.executeQuery(sql)) {
            while (rs.next())
                System.out.println("id_persoana=" + rs.getInt(1) + ", id_exc=" + rs.getInt(2) + ",destinatie="
                        + rs.getString(3) + ", an=" + rs.getInt(4));
        } catch (SQLException
                e) {
            e.printStackTrace();
        }
    }

    public static void adaugare_excursie(Connection connection, int id_pers,  String destinatie, int an) {
        String sql="insert into excursii (id_persoana, destinatie, an) values (?,?,?)";
        try(PreparedStatement ps=connection.prepareStatement(sql)) {
            ps.setInt(1, id_pers);
            ps.setString(2, destinatie);
            ps.setInt(3, an);
            int nr_randuri=ps.executeUpdate();
            System.out.println("\nNumar randuri afectate de adaugare="+nr_randuri);
        } catch (SQLException e) {
            System.out.println(sql);
            e.printStackTrace();
        }
    }
//    public static void actualizare(Connection connection,int id,int varsta){
//        String sql="update persoane set varsta=? where id=?";
//        try(PreparedStatement ps=connection.prepareStatement(sql)) {
//            ps.setInt(1, varsta);
//            ps.setInt(2, id);
//            int nr_randuri=ps.executeUpdate();
//            System.out.println("\nNumar randuri afectate de modificare="+nr_randuri);
//        } catch (SQLException e) {
//            System.out.println(sql);
//            e.printStackTrace();
//        }
//    }

    public static void stergere_excursie(Connection connection,int id){
        String sql="delete from excursii where id_exc=?";
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
            afisare_tabela_excursi(statement,"Continut initial");

//            System.out.println("Dati numele: \n");
//            nume= scanner.nextLine();
//
//            System.out.println("Dati varsta: \n");
//            varsta = scanner.nextInt();


            adaugare_excursie(connection, 3, "Barcelona", 2026);
            adaugare_excursie(connection, 1, "Dubai", 2022);
            afisare_tabela_excursi(statement,"Dupa adaugare");
            //actualizare(connection,3,67);
            //stergere_excursie(connection,2);
           afisare_tabela_excursi(statement,"Dupa stergere");
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}