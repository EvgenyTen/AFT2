package redmine.db;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import lombok.SneakyThrows;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static redmine.Property.getIntegerProperty;
import static redmine.Property.getStringProperty;

public class DataBaseConnection {
    private String dbHost;
    private Integer dbPort;
    private String dbUser;
    private String dbPass;
    private String dbName;
    private Connection connection;

    public DataBaseConnection() {
        initVariables();
        connect();
    }

    private void initVariables() {
        dbHost = getStringProperty("dbHost");
        dbPort = getIntegerProperty("dbPort");
        dbUser = getStringProperty("dbUser");
        dbPass = getStringProperty("dbPass");
        dbName = getStringProperty("dbName");
    }


    @Step("Подключение к БД")
    private void connect() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String url = String.format("jdbc:postgresql://%s:%d/%s?user=%s&password=%s", dbHost, dbPort, dbName, dbUser, dbPass);
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @SneakyThrows
    @Step("Выполнение SQL запроса")
    public synchronized List<Map<String, Object>> executeQuery(String query) {
        Allure.addAttachment("query", query);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        int count = resultSet.getMetaData().getColumnCount();
        List<String> columnNames = new ArrayList<>();
        List<Map<String, Object>> result = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            String columnName = resultSet.getMetaData().getColumnName(i);
            columnNames.add(columnName);
        }
        while (resultSet.next()) {
            Map<String, Object> columnData = new TreeMap<>();
            for (String columnName : columnNames) {
                Object value = resultSet.getObject(columnName);
                columnData.put(columnName, value);
            }
            result.add(columnData);
        }
        Allure.addAttachment("response", result.toString());
        return result;
    }

    @SneakyThrows
    @Step("Выполнение SQL запроса")
    public synchronized List<Map<String, Object>> executePreparedQuery(String query, Object... parameters) {
        PreparedStatement statement = connection.prepareStatement(query);
        int index = 1;
        for (Object object : parameters) {
            statement.setObject(index++, object);
        }
        Allure.addAttachment("query", statement.toString());
        ResultSet resultSet = statement.executeQuery();
        int count = resultSet.getMetaData().getColumnCount();
        List<String> columnNames = new ArrayList<>();
        List<Map<String, Object>> result = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            String columnName = resultSet.getMetaData().getColumnName(i);
            columnNames.add(columnName);
        }
        while (resultSet.next()) {
            Map<String, Object> columnData = new TreeMap<>();
            for (String columnName : columnNames) {
                Object value = resultSet.getObject(columnName);
                columnData.put(columnName, value);
            }
            result.add(columnData);
        }
        Allure.addAttachment("response", result.toString());
        return result;
    }
}
