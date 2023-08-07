package com.example.rest_api;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public class App_DB {

    public String login;
    public String password;
    public String date;
    public String email;
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/database_postgres";
    private static final String USER = "postgres_user";
    private static final String PASSWORD = "postgres_password";

    public class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }

    // Выполнение запроса select по login из двух таблиц
    public void selectByLogin(String inLogin) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Устанавливаем соединение с базой данных
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);

            // Создаем и выполняем SQL-запрос
            statement = connection.createStatement();
            String sqlQuery = "SELECT * FROM user_data JOIN registration ON user_data.login = registration.login WHERE user_data.login = '" + inLogin + "'";
            resultSet = statement.executeQuery(sqlQuery);

            // Обрабатываем результаты запроса
            if (resultSet.next()) {
                String foundLogin = resultSet.getString("login");
                String foundPassword = resultSet.getString("password");
                java.sql.Date foundDate = resultSet.getDate("date");
                String foundEmail = resultSet.getString("email");

                login = foundLogin;
                password = foundPassword;
                date = foundDate.toString();
                email = foundEmail;
            }
            else {
                throw new SQLException("No user with login \"" + inLogin + " found");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            // Закрываем ресурсы
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Выполнение запроса - вставка данных в обе таблицы
    public void insertData() {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            // Подготавливаем SQL-запрос для вставки данных в таблицу user_data
            String userDataInsertQuery = "INSERT INTO user_data (login, password, date) VALUES (?, ?, ?)";
            PreparedStatement userDataPreparedStatement = connection.prepareStatement(userDataInsertQuery);
            userDataPreparedStatement.setString(1, login);
            userDataPreparedStatement.setString(2, password);
            Date parsedDate = Date.valueOf(date);
            userDataPreparedStatement.setDate(3, parsedDate);

            // Выполняем запрос для вставки данных в user_data
            userDataPreparedStatement.executeUpdate();

            // Подготавливаем SQL-запрос для вставки данных в таблицу registration
            String registrationInsertQuery = "INSERT INTO registration (login, email) VALUES (?, ?)";
            PreparedStatement registrationPreparedStatement = connection.prepareStatement(registrationInsertQuery);
            registrationPreparedStatement.setString(1, login);
            registrationPreparedStatement.setString(2, email);

            // Выполняем запрос для вставки данных в registration
            registrationPreparedStatement.executeUpdate();

            System.out.println("Data inserted successfully into both tables.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
