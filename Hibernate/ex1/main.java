package demo;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.sql.*;
        import java.util.Properties;
        import java.util.Scanner;

public class Main {
    private static final String CONNECT_STRING = "jdbc:mysql://localhost:3306/";
    private static final String DATABASE_NAME = "minions_db?serverTimezone=UTC";

    private static Connection connection;
    private static String query ;
    private static PreparedStatement statement;
    private static BufferedReader reader;

    public static void main(String[] args) throws SQLException, IOException {

        reader = new BufferedReader(new InputStreamReader(System.in));

        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password", "roo7");


        connection = DriverManager
                .getConnection(CONNECT_STRING  + DATABASE_NAME, props);

        //02. Get Villains’ Names
        getVillainsNamesAndCountOfMinions();

        //03. Get Minion Names
        getMinionNamesEx(); 




    }

    private static void getMinionNamesEx() throws IOException, SQLException {
        System.out.println("Enter villain id:");
        int villainID = Integer.parseInt(reader.readLine());
        if(!checkIfEntityExist(villainID,"villains")){
            System.out.printf("No villain with ID %d exists in the database.",villainID);
            return;
        }

        System.out.printf("Villain: %s%n",getEntityNameById(villainID,"villains"));
        getMinionsAndAgeByVillainId(villainID);
    }

    private static void getMinionsAndAgeByVillainId(int villainID) throws SQLException {
        query = "SELECT m.name,m.age FROM minions AS m\n" +
        "JOIN minions_villains mv on m.id = mv.minion_id\n" +
        "WHERE mv.villain_id = ?";

        statement = connection.prepareStatement(query);
        statement.setInt(1,villainID);

        ResultSet rs = statement.executeQuery();

        int minionNumber = 0;
        while(rs.next()){
            System.out.printf("%d. %s %d%n",++minionNumber,
                    rs.getString("name"),
                    rs.getInt(2));
        }
    }

    private static String getEntityNameById(int entityId,String tableName) throws SQLException {
        query = "SELECT name FROM " + tableName + " WHERE id = ?";
        statement = connection.prepareStatement(query);
        statement.setInt(1,entityId);
        ResultSet rs = statement.executeQuery();
        return rs.next() ? rs.getString("name") : null;
    }

    private static boolean checkIfEntityExist(int villainId, String villains) throws SQLException {
        query = "SELECT * FROM " + villains + " WHERE id = ?";
        statement = connection.prepareStatement(query);
        statement.setInt(1,villainId);
        ResultSet rs = statement.executeQuery();
        return rs.next();
    }

    private static void getVillainsNamesAndCountOfMinions() throws SQLException {
        query = "SELECT v.name,COUNT(mv.minion_id) AS `count` FROM villains AS v\n" +
                "JOIN minions_villains mv on v.id = mv.villain_id\n" +
                "GROUP BY v.name\n" +
                "HAVING `count` > ?\n" +
                "ORDER BY `count` DESC";

        statement = connection.prepareStatement(query);
        statement.setInt(1,15);

        ResultSet rs = statement.executeQuery();
        while(rs.next()){
            System.out.printf("%s %d",rs.getString("name"),rs.getInt("count")).println();
        }
    }
}
