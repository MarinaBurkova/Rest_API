package com.example.rest_api;


import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class App_DB {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/database_postgres";
    private static final String USER = "postgres_user";
    private static final String PASSWORD = "postgres_password";
    private static final String table1 = "user_data";
    private static final String table2 = "registration";

    // Выполнение запроса select по login из двух таблиц
    static public User selectByLogin(String inLogin) throws SQLException {
        Connection connection = null;
        try {
            // Устанавливаем соединение с базой данных
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);

            // Создаем и выполняем SQL-запрос
            Statement statement = connection.createStatement();
            String sqlQuery = "SELECT * FROM user_data JOIN registration ON user_data.login = registration.login WHERE user_data.login = '" + inLogin + "'";
            ResultSet resultSet = statement.executeQuery(sqlQuery);

            // Обрабатываем результаты запроса
            if (resultSet.next()) {
                String foundLogin = resultSet.getString("login");
                String foundPassword = resultSet.getString("password");
                java.sql.Date foundDate = resultSet.getDate("date");
                String foundEmail = resultSet.getString("email");

                User user = new User(foundLogin, foundPassword, foundDate, foundEmail);
                return user;
            }
            else {
                throw new UserNotFoundException("No user with login \"" + inLogin + "\" found");
            }
        } catch (SQLException | UserNotFoundException e) {
            throw e;
        } finally {
            // Закрываем ресурсы
            if (connection != null) { connection.close(); }
        }
    }

    // Выполнение запроса - вставка данных в обе таблицы


    static public String insertData(User user) throws Exception{
        String query = "INSERT INTO user_data (login, password, date) VALUES (?, ?, ?); \n" +
                "INSERT INTO registration (login, email) VALUES (?, ?);";
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        if (user.login == null || user.email == null || user.password == null){
            throw new com.example.rest_api.UserNotFoundException("Data required");
        }
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        PreparedStatement preparedStatement = connection.prepareStatement(query)){

            preparedStatement.setString(1, user.login);
            preparedStatement.setString(2, user.password);
            preparedStatement.setDate(3, Date.valueOf(date.format(formatter)));
            preparedStatement.setString(4, user.login);
            preparedStatement.setString(5, user.email);
            int row = preparedStatement.executeUpdate();

            return new String ("{'status':'ok, 'updatedRows':" + row + ", 'date':'" + LocalDate.now() + "'}");
        } catch (SQLException e) {
            System.out.println("Problem!");
            System.out.println(e.getMessage());
            throw new SQLException(e.getMessage());
        }
    }
}
