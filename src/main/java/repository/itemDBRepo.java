package repository;

import domain.BaseEntity;
import domain.ClothingItem;
import domain.validators.Validator;

import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.Optional;

public class itemDBRepo implements Repository<Long, ClothingItem> {

    private static final String URL = "jdbc:postgresql://localhost:5432/Lab_5";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "12041999";

    private Validator<ClothingItem> validator;

    public itemDBRepo(Validator<ClothingItem> v) {
        this.validator = v;
    }

    @Override
    public Optional<ClothingItem> findOne(Long id) {

        String sql = "select * from \"ClothingItems\" where \"ID\"=?";

        ClothingItem item = null;
        try {
            Class.forName("org.postgresql.Driver");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        try{
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement statement = ((Connection) connection).prepareStatement(sql);
            ResultSet resultSet = ((PreparedStatement) statement).executeQuery();

            while (((ResultSet) resultSet).next()) {
                java.lang.Long idTest = java.lang.Long.parseLong(String.valueOf(resultSet.getInt("ID")));
                if (idTest == id) {
                    String name = resultSet.getString("Name");
                    int price = resultSet.getInt("Price");
                    String designer = resultSet.getString("Designer");

                    item = new ClothingItem(name, designer, price);
                    item.setId(idTest);
                } else
                    continue;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(item);
    }


    @Override
    public Iterable<ClothingItem> findAll() {
        List<ClothingItem> clients = new ArrayList<>();
        String sql = "select * from \"ClothingItems\"";
        try {
            Class.forName("org.postgresql.Driver");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        try{
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement statement = ((Connection) connection).prepareStatement(sql);
            ResultSet resultSet = ((PreparedStatement) statement).executeQuery();

            while (((ResultSet) resultSet).next()) {
                java.lang.Long id = java.lang.Long.parseLong(String.valueOf(resultSet.getInt("ID")));
                String name = resultSet.getString("Name");
                int price = resultSet.getInt("Price");
                String designer = resultSet.getString("Designer");

                ClothingItem item = new ClothingItem(name, designer, price);
                item.setId(id);

                clients.add(item);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return (Iterable<ClothingItem>) clients;
    }

    @Override
    public Optional<ClothingItem> save(ClothingItem i) {
        String sql = "insert into \"ClothingItems\"(\"ID\", \"Name\", \"Designer\", \"Price\") values(?,?,?,?)";
        try {
            Class.forName("org.postgresql.Driver");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        this.validator.validate(i);
        try {

            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement statement = ((Connection) connection).prepareStatement(sql);

            statement.setInt(1, Integer.parseInt(String.valueOf(i.getId())));
            statement.setString(2, i.getName());
            statement.setString(3, i.getDesigner());
            statement.setInt(4, i.getPrice());

            //ResultSet resultSet = ((PreparedStatement) statement).executeQuery();

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(i);

    }

    @Override
    public Optional<ClothingItem> update(ClothingItem item) {
        String sql = "update \"ClothingItems\" set \"Name\"=?, \"Designer\"=?, \"Price\"=? where \"ID\"=?";

        try {
            Class.forName("org.postgresql.Driver");
        }
        catch(Exception e){
            e.printStackTrace();
        }

        this.validator.validate(item);
        try (Connection connection = DriverManager.getConnection(URL, USERNAME,
                PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, item.getName());
            statement.setString(2, item.getDesigner());
            statement.setInt(3, item.getPrice());
            statement.setLong(4, Integer.parseInt(String.valueOf(item.getId())));

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(item);
    }

    @Override
    public Optional<ClothingItem> delete(Long id) {
        String sql = "delete from \"ClothingItems\" where \"ID\"=?";
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
