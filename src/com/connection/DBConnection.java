package com.connection;


import com.models.User;

import java.sql.*;

import static com.connection.Env.*;

public class DBConnection {
    private String title;
    private Connection connection;
    private static final String INSERT_USERS_SQL = "INSERT INTO public.users" +
            " (id, first_name, last_name, username, email, password) VALUES " +
            " (default, ?, ?, ?, ?, crypt(?, gen_salt('bf')) );";
    private static final String SELECT_USER_SQL = "SELECT * FROM public.users" +
            " WHERE username = ?" +
            " and password = crypt(?, password);";

    public DBConnection(String title){
        this.title = title;
        try {
            connection = DriverManager.getConnection(host, username, password);
            System.out.println("Connceted");

        } catch (SQLException e) {
            System.out.println("Error connecting to database");
            e.printStackTrace();
        }
    }

    public void createNewUser(User user){
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getUserName());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getPassword());

            preparedStatement.executeUpdate();
            System.out.println("Created new user");

            connection.close();
        } catch (SQLException e) {
            System.out.println("Error iserting a new user");
            e.printStackTrace();
        }
    }

    public User checkUser(String userEmail, String userPassword){
        User user = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_SQL);
            preparedStatement.setString(1, userEmail);
            preparedStatement.setString(2, userPassword);
            ResultSet result = preparedStatement.executeQuery();

            if(result.next()){
                user = new User();
                user.setId(result.getInt("id"));
                user.setFirstName(result.getString("first_name"));
                user.setLastName(result.getString("last_name"));
                user.setUserName(result.getString("username"));
                user.setEmail(result.getString("email"));

            }


            connection.close();

        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Error selecting a user");
        }

        return user;
    }

}
