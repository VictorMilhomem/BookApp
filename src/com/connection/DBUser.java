package com.connection;

import com.models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUser extends DBConnection{

    public DBUser(String title){
        super(title);
    }

    public User createNewUser(String firstName, String lastName, String username, String email, String password){
        final String INSERT_USERS_SQL = "INSERT INTO public.users" +
                " (id, first_name, last_name, username, email, password) VALUES " +
                " (default, ?, ?, ?, ?, crypt(?, gen_salt('bf')) );";
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
        final String SELECT_USER_SQL = "SELECT * FROM public.users" +
                " WHERE username = ?" +
                " and password = crypt(?, password);";
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
        final String UPDATE_USER_FIRSTNAME = "UPDATE public.users" +
                " SET first_name = ?" + " WHERE id=?;";
        final String UPDATE_USER_LASTNAME = "UPDATE public.users" + " SET last_name = ?" + " WHERE id=?;";
        final String UPDATE_USERNAME = "UPDATE public.users" + " SET username = ?" + " WHERE id=?;";
        final String UPDATE_USER_EMAIL = "UPDATE public.users" + " SET email = ?" + " WHERE id=?;";
        final String UPDATE_USER_PASSWORD = "UPDATE public.users" + " SET password = crypt(?, gen_salt('bf'))"
                + " WHERE id=?;";

        try {

            PreparedStatement firstNameStatement = connection.prepareStatement(UPDATE_USER_FIRSTNAME);
            PreparedStatement lastNameStatement = connection.prepareStatement(UPDATE_USER_LASTNAME);
            PreparedStatement userNameStatement = connection.prepareStatement(UPDATE_USERNAME);
            PreparedStatement passwordStatement = connection.prepareStatement(UPDATE_USER_PASSWORD);
            PreparedStatement emailStatement = connection.prepareStatement(UPDATE_USER_EMAIL);

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
        final String DELETE_USER_SQL = "DELETE FROM public.users" +
                " WHERE id= ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_SQL);
            preparedStatement.setInt(1, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
