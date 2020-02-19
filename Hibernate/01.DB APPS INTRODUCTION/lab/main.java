package demo;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.sql.*;
import java.util.*;

public class Main {
    private static final String CONNECT_STRING = "jdbc:mysql://localhost:3306/";
    private static final String DATABASE_NAME = "minions_db?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    private static Connection connection;
    private static String query ;
    private static PreparedStatement statement;
    private static BufferedReader reader;
    private static Scanner sc;

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

        //04. Add Minion
//          addMinionEx4();

        //05. Change Town Names Casing
//        changeTownNamesCasing();

        //06. Remove Villain
//        removeVillain();

        //07. Print All Minion Names
//          printAllMinionNames();

        //08. Increase Minions Age
//        increaseMinionsAge();

        //09. Increase Age Stored Procedure
          increaseAgeStoredProcedure();

    }

    private static void increaseAgeStoredProcedure() {
    }

    private static void printAllMinionNames() throws SQLException {
        sc = new Scanner(System.in);

        List<Minion> minions = new ArrayList<Minion>();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT name FROM minions");
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            minions.add(new Minion(resultSet.getString("name")));
        }

        for (int i = 0; i < minions.size() / 2; i++) {
            System.out.println(minions.get(i).name);
            System.out.println(minions.get(minions.size() - 1 - i).name);
        }


        if((double)minions.size() % 2 != 0){
            System.out.println(minions.get((int)Math.ceil(minions.size() / 2)).name);
        }
    }

    private static void addMinionEx4() throws SQLException {

        sc = new Scanner(System.in);
        System.out.println("Enter a minion name, age and town (String Int String):");
        String[] minionInfoInput = sc.nextLine().split("\\s+");
        System.out.println("Enter villain name (String): ");
        String villainNameInput = sc.nextLine();
        if(!isTownValid(connection, minionInfoInput[2])){
            insertTown(connection, minionInfoInput[2]);
        }
        if(!isVillainValid(connection, villainNameInput)){
            insertVillain(connection, villainNameInput);
        }
        insertMinion(connection, minionInfoInput[0], Integer.parseInt(minionInfoInput[1]), minionInfoInput[2], villainNameInput);

    }

    private static boolean isTownValid(Connection connection, String town) throws SQLException {
        statement = connection.prepareStatement("SELECT name FROM towns WHERE name = ?");

        statement.setString(1, town);

        ResultSet resultSet = statement.executeQuery();

        if(resultSet.next()){
            return true;
        }
        else{
            return false;
        }
    }

    private static boolean isVillainValid(Connection connection, String villainName) throws SQLException {
        statement = connection.prepareStatement("SELECT name FROM villains WHERE name = ?;");

        statement.setString(1,villainName);

        ResultSet resultSet = statement.executeQuery();

        return resultSet.next();
    }

    private static void insertTown(Connection connection, String townName){
        try{
            statement = connection.prepareStatement("INSERT INTO towns(name) VALUES (?);");
            statement.setString(1, townName);
            statement.executeUpdate();
            System.out.println("Town " + townName + " was added to the database.");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static void insertVillain(Connection connection, String villainName){
        try{
            statement = connection.prepareStatement("INSERT INTO villains(name, evilness_factor) VALUES(?, ?);");
            statement.setString(1, villainName);
            statement.setString(2, "evil");
            statement.executeUpdate();
            System.out.println("Villain " + villainName + " was added to the database");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    private static void insertMinion(Connection connection, String minionName, int minionAge, String minionTown, String villainName){
        try{
            final String sqlMinion = "INSERT into minions(name, age, town_id) VALUES(?, ?, (SELECT t.id FROM towns t WHERE t.name = ?)); ";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlMinion, Statement.RETURN_GENERATED_KEYS);


            preparedStatement.setString(1, minionName);
            preparedStatement.setInt(2, minionAge);
            preparedStatement.setString(3, minionTown);

            preparedStatement.executeUpdate();
            ResultSet resultSetMinionId = preparedStatement.getGeneratedKeys();
            int lastMinionId = 0;
            while(resultSetMinionId.next()){
                lastMinionId = resultSetMinionId.getInt(1);
                break;
            }

            statement = connection.prepareStatement("INSERT INTO minions_villains(minion_id, villain_id) VALUES (?, (SELECT v.id FROM villains v WHERE v.name = ?));");
            statement.setInt(1, lastMinionId);
            statement.setString(2, villainName);
            statement.executeUpdate();

            System.out.println("Successfully added " + minionName + " to be minion of " + villainName + ".");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

    private static void increaseMinionsAge() throws SQLException {
        sc = new Scanner(System.in);
        System.out.println("Enter minions ID's: ");

        int[] minions_ids = Arrays.stream(sc.nextLine().split("\\s+")).mapToInt(Integer::parseInt).toArray();
        statement = connection.prepareStatement("UPDATE minions AS m\n" +
                "SET m.age = m.age+1 , m.name = LOWER(m.name)\n" +
                "WHERE m.id = ?");

        for (int i = 0; i < minions_ids.length; i++) {
            statement.setInt(1,minions_ids[i]);
            statement.executeUpdate();
        }

        statement = connection.prepareStatement("SELECT name, age FROM minions");

        ResultSet minionsRS = statement.executeQuery();

        while(minionsRS.next()){
            System.out.println(minionsRS.getString("name") + " " + minionsRS.getString("age"));
        }
    }

    private static void removeVillain() throws SQLException {

        sc = new Scanner(System.in);
        System.out.println("Villain id: ");
        int villain_id = sc.nextInt();
        int releasedMinions = 0;


        PreparedStatement preparedStatement = connection.prepareStatement("SELECT name FROM villains WHERE id = ?");
        preparedStatement.setInt(1, villain_id);
        ResultSet villainNameRS = preparedStatement.executeQuery();
        villainNameRS.next();

        String name = villainNameRS.getString("name");
        if(name.equals("")){
            System.out.println("No such villain was found");
        }
        else{
            preparedStatement = connection.prepareStatement("DELETE FROM minions_villains WHERE villain_id = ?");
            preparedStatement.setInt(1, villain_id);
            releasedMinions = preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("DELETE FROM villains WHERE id = ?");
            preparedStatement.setInt(1, villain_id);
            preparedStatement.executeUpdate();

            connection.commit();

            System.out.println(name + " was deleted");
            System.out.println(releasedMinions + " minions released");
        }
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
