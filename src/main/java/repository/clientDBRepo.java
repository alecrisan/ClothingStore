package repository;

import domain.BaseEntity;
import domain.Client;
import domain.validators.Validator;

import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.Optional;

public class clientDBRepo implements Repository<Long, Client> {

    private static final String URL = "jdbc:postgresql://localhost:5432/Lab_5";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "12041999";

    private Validator<Client> validator;

    public clientDBRepo(Validator<Client> v) {
        this.validator = v;
    }

    @Override
    public Optional<Client> findOne(Long id) {

        String sql = "select * from \"Clients\" where \"ID\"=?";

        Client client = null;
        try {
            Class.forName("org.postgresql.Driver");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD); PreparedStatement statement = ((Connection) connection).prepareStatement(sql); ResultSet resultSet = ((PreparedStatement) statement).executeQuery()) {

            while (((ResultSet) resultSet).next()) {
                java.lang.Long idTest = java.lang.Long.parseLong(String.valueOf(resultSet.getInt("ID")));
                if (idTest == id) {
                    String name = resultSet.getString("Name");
                    int age = resultSet.getInt("Age");
                    String membership = resultSet.getString("MembershipType");

                    client = new Client(name, age, membership);
                    client.setId(idTest);
                } else
                    continue;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(client);
    }

    @Override
    public Iterable<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        String sql = "select * from \"Clients\"";
        try {
            Class.forName("org.postgresql.Driver");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD); PreparedStatement statement = ((Connection) connection).prepareStatement(sql); ResultSet resultSet = ((PreparedStatement) statement).executeQuery()) {

            while (((ResultSet) resultSet).next()) {
                java.lang.Long id = java.lang.Long.parseLong(String.valueOf(resultSet.getInt("ID")));
                String name = resultSet.getString("Name");
                int age = resultSet.getInt("Age");
                String membership = resultSet.getString("MembershipType");

                Client client = new Client(name, age, membership);
                client.setId(id);

                clients.add(client);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return (Iterable<Client>) clients;


    }

    @Override
    public Optional<Client> save(Client c) {
        String sql = "insert into \"Clients\"(\"Name\", \"ID\", \"Age\", \"MembershipType\") values(?,?,?,?)";
        try {
            Class.forName("org.postgresql.Driver");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        this.validator.validate(c);
        try {

            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement statement = ((Connection) connection).prepareStatement(sql);

            statement.setString(1, c.getName());
            statement.setInt(2, Integer.parseInt(String.valueOf(c.getId())));
            statement.setInt(3, c.getAge());
            statement.setString(4, c.getMembershipType());

            //ResultSet resultSet = ((PreparedStatement) statement).executeQuery();

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(c);

    }

    @Override
    public Optional<Client> update(Client client) {
        String sql = "update \"Clients\" set \"Name\"=?, \"Age\"=?, \"MembershipType\"=? where \"ID\"=?";

        try {
            Class.forName("org.postgresql.Driver");
        }
        catch(Exception e){
            e.printStackTrace();
        }

        this.validator.validate(client);
        try (Connection connection = DriverManager.getConnection(URL, USERNAME,
                PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, client.getName());
            statement.setInt(2, client.getAge());
            statement.setString(3, client.getMembershipType());
            statement.setLong(4, Integer.parseInt(String.valueOf(client.getId())));

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(client);
    }

    @Override
    public Optional<Client> delete(Long id) {
        String sql = "delete from \"Clients\" where \"ID\"=?";
        try {
            Class.forName("org.postgresql.Driver");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        try (Connection connection = DriverManager.getConnection(URL, USERNAME,
                PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, Integer.parseInt(String.valueOf(id)));

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
