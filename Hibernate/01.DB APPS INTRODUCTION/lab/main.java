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
    private static final String DATABASE_NAME = "minions_db?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    private static Connection connection;
    private static String query ;
    private static PreparedStatement statement;
    private static BufferedReader reader;

    public static void main(String[] args) throws SQLException, IOException {

        reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter username default (root): ");
        String user = reader.readLine();
        user = user.equals("") ? "root" : user;
        System.out.println();

        System.out.print("Enter password default (empty):");
        String password = reader.readLine().trim();
        System.out.println();

        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);


        connection = DriverManager
                .getConnection(CONNECT_STRING  + DATABASE_NAME, props);

        //02. Get Villains’ Names
//        getVillainsNamesAndCountOfMinions();

        //03. Get Minion Names
//        getMinionNamesEx();

        //05. Change Town Names Casing
        changeTownNamesCasing();


    }

    private static void changeTownNamesCasing() throws IOException, SQLException {
        System.out.println("Enter a country:");
        String countryToUpdate = reader.readLine();

        statement = connection.prepareStatement("UPDATE towns SET name = UPPER(name) WHERE country = ? AND name NOT LIKE BINARY UPPER(name) ");
        statement.setString(1, countryToUpdate);

        int affectedRows = statement.executeUpdate();

        if(affectedRows > 0){
            statement = connection.prepareStatement("SELECT name FROM towns WHERE country = ?");
            statement.setString(1, countryToUpdate);
            ResultSet updatedCountriesRS = statement.executeQuery();

            String result = "[";
            while(updatedCountriesRS.next()){
                result += updatedCountriesRS.getString("name") + ", ";
            }
            result = result.substring(0, result.length() - 2);
            result += "]";

            System.out.println(affectedRows + " town names were affected.");
            System.out.println(result);
        }
        else{
            System.out.println("No town names were affected");
        }

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
