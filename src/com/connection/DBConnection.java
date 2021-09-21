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
    private static final String DELETE_USER_SQL = "DELETE FROM public.users" +
            " WHERE id= ?;";

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

    public void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User createNewUser(String firstName, String lastName, String username, String email, String password){
        User user = new User(firstName, lastName, username, email, password);

        try {

            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getUserName());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getPassword());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
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

        }catch (SQLException e){
            e.printStackTrace();
        }

        return user;
    }

    public void updateDBUser(User user){
        final String updateFirstName = "UPDATE public.users" +
                " SET first_name = ?" + " WHERE id=?;";
        final String updateLastName = "UPDATE public.users" + " SET last_name = ?" + " WHERE id=?;";
        final String updateUserName = "UPDATE public.users" + " SET username = ?" + " WHERE id=?;";
        final String updateEmail = "UPDATE public.users" + " SET email = ?" + " WHERE id=?;";
        final String updatePassword = "UPDATE public.users" + " SET password = crypt(?, gen_salt('bf'))"
                + " WHERE id=?;";

        try {

            PreparedStatement firstNameStatement = connection.prepareStatement(updateFirstName);
            PreparedStatement lastNameStatement = connection.prepareStatement(updateLastName);
            PreparedStatement userNameStatement = connection.prepareStatement(updateUserName);
            PreparedStatement passwordStatement = connection.prepareStatement(updatePassword);
            PreparedStatement emailStatement = connection.prepareStatement(updateEmail);

            firstNameStatement.setString(1, user.getFirstName());
            firstNameStatement.setInt(2, user.getId());
            lastNameStatement.setString(1, user.getLastName());
            lastNameStatement.setInt(2, user.getId());
            userNameStatement.setString(1, user.getUserName());
            userNameStatement.setInt(2, user.getId());
            passwordStatement.setString(1, user.getPassword());
            passwordStatement.setInt(2, user.getId());
            emailStatement.setString(1, user.getEmail());
            emailStatement.setInt(2, user.getId());



            firstNameStatement.executeUpdate();
            lastNameStatement.executeUpdate();
            userNameStatement.executeUpdate();
            passwordStatement.executeUpdate();
            emailStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(User user){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_SQL);
            preparedStatement.setInt(1, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
