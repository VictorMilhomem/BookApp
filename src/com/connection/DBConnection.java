package com.connection;


import com.models.User;

import java.sql.*;

import static com.connection.Env.*;

public abstract class DBConnection {
    private String title;
    protected Connection connection;

    public DBConnection(String title){
        this.title = title;
        try {
            connection = DriverManager.getConnection(host, username, password);
            System.out.println(this.title + " Connceted");

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


}
