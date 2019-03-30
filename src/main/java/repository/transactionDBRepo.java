package repository;

import domain.BaseEntity;
import domain.Client;
import domain.ClothingItem;
import domain.Transaction;
import domain.validators.ClientValidator;
import domain.validators.Validator;
import domain.validators.ValidatorException;

import java.sql.DriverManager;
import java.util.*;
import java.sql.*;
import java.util.stream.StreamSupport;

public class transactionDBRepo implements Repository<Long, Transaction> {

    private static final String URL = "jdbc:postgresql://localhost:5432/Lab_5";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "12041999";

    private Validator<Transaction> validator;

    public transactionDBRepo(Validator<Transaction> v) {
        this.validator = v;
    }

    @Override
    public Optional<Transaction> findOne(Long id) {

        String sql = "select * from \"Transactions\" where \"ID\"=?";

        Transaction t = null;
        try {
            Class.forName("org.postgresql.Driver");
        }
        catch(Exception e){
            e.printStackTrace();
        }

        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement statement = ((Connection) connection).prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(String.valueOf(id)));
            ResultSet resultSet = ((PreparedStatement) statement).executeQuery();

            while (((ResultSet) resultSet).next()) {
                java.lang.Long idTest = java.lang.Long.parseLong(String.valueOf(resultSet.getInt("ID")));
                if (idTest == id) {
                    String date = resultSet.getString("Date");
                    int clientId = resultSet.getInt("ClientId");
                    int total = resultSet.getInt("Total");
                    java.sql.Array items = resultSet.getArray("ItemList");

                    Client c = new Client();
                    c.setId(java.lang.Long.parseLong(String.valueOf(clientId)));
                    t = new Transaction(c, date);
                    t.setId(idTest);
                    t.setTotal(total);
                    Integer[] it = (Integer[])items.getArray();
                    t.setItemsBought(it);
                } else
                    continue;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(t);
    }

    @Override
    public Iterable<Transaction> findAll() {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "select * from \"Transactions\"";
        try {
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement statement = ((Connection) connection).prepareStatement(sql);
            ResultSet resultSet = ((PreparedStatement) statement).executeQuery();


            while (((ResultSet) resultSet).next()) {
                java.lang.Long id = java.lang.Long.parseLong(String.valueOf(resultSet.getInt("ID")));
                //statement.setInt(0, Integer.parseInt(String.valueOf(id)));
                String date = resultSet.getString("Date");
                int clientId = resultSet.getInt("ClientId");
                int total = resultSet.getInt("Total");
                Array items = resultSet.getArray("ItemList");

                Client c = new Client();
                c.setId(java.lang.Long.parseLong(String.valueOf(clientId)));
                Transaction t = new Transaction(c, date);
                t.setId(id);
                t.setTotal(total);
                Integer[] it =(Integer[])items.getArray();
                t.setItemsBought(it);
                transactions.add(t);


            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return (Iterable<Transaction>) transactions;

    }

        @Override
    public Optional<Transaction> save(Transaction t) {
        String sql = "insert into \"Transactions\"(\"ID\", \"ClientId\", \"ItemList\", \"Date\", \"Total\") values(?,?,?,?,?)";
        try {
            Class.forName("org.postgresql.Driver");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        this.validator.validate(t);
        try {

            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement statement = ((Connection) connection).prepareStatement(sql);

            statement.setInt(1, Integer.parseInt(String.valueOf(t.getId())));
            statement.setInt(2, Integer.parseInt(String.valueOf(t.getClient().getId())));
            statement.setString(4, t.getDate());
            statement.setInt(5, t.getTotalPrice());

//            Integer items[] = new Integer[0];
//
//            for(int i = 0 ; i <= t.getItems().size(); i++){
//                //items = Math.toIntExact(t.getItems().get(i).getId());
//                items[i] = Integer.parseInt(String.valueOf(t.getItems().get(i).getId()));
//
//            }

            ArrayList<Integer> myArray = new ArrayList<Integer>();

            for(ClothingItem item : t.getItems())
            {
                myArray.add(Math.toIntExact(item.getId()));
            }
            Array items = connection.createArrayOf("integer", myArray.toArray());
            statement.setArray(3, items);

            //ResultSet resultSet = ((PreparedStatement) statement).executeQuery();

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(t);

    }

    @Override
    public Optional<Transaction> update(Transaction t) {
        String sql = "update \"Transactions\" set \"ItemList\"=?, \"Date\"=? where \"ID\"=?";

        try {
            Class.forName("org.postgresql.Driver");
        }
        catch(Exception e){
            e.printStackTrace();
        }

        this.validator.validate(t);

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(2, t.getDate());
            statement.setLong(3, Integer.parseInt(String.valueOf(t.getId())));

            ArrayList<Integer> myArray = new ArrayList<Integer>();

            for(ClothingItem item : t.getItems())
            {
                myArray.add(Math.toIntExact(item.getId()));
            }
            Array items = connection.createArrayOf("integer", myArray.toArray());
            statement.setArray(1, items);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(t);
    }

    @Override
    public Optional<Transaction> delete(Long id) {
        String sql = "delete from \"Transactions\" where \"ID\"=?";
        try {
            Class.forName("org.postgresql.Driver");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, Integer.parseInt(String.valueOf(id)));

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
