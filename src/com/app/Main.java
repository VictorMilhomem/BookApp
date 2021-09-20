package com.app;

import com.connection.DBConnection;
import com.views.LoginPage;
import com.views.SignUp;

import javax.swing.*;

public class Main {
    private static final int  WIDTH = 800;
    private static final int HEIGHT = 600;
    public static void main (String[] args)
    {
        JFrame frame = new LoginPage("Login");
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
    }

}
