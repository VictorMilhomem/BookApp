package com.views;

import com.connection.DBConnection;
import com.models.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage extends JFrame implements ActionListener {

    private JPanel mainPanel;
    private JPanel borderPanel;
    private JPanel loginPanel;
    private JTextField userNameField;
    private JPasswordField passwordField;
    private JLabel userNameLabel;
    private JLabel passwordLabel;
    private JButton loginButton;
    private JButton signUpButton;
    private final DBConnection database;
    protected User user;

    public LoginPage(String title){
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        database = new DBConnection("LoginConnection");

        //---- Buttons ---------------------------//

        loginButton.addActionListener(this);
        signUpButton.addActionListener(this);

        //---- End Buttons -----------------------//


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (loginButton.equals(e.getSource())){
            user = new User();
            user = database.checkUser(userNameField.getText(), String.valueOf(passwordField.getPassword()));
            if(user != null){
                database.closeConnection();
                this.dispose();
                new AppPage("AppHomePage", user, this.getWidth(), this.getHeight());
            }else{
                JOptionPane.showMessageDialog(null, "Senha ou usuário incorretos");
            }
        }
        if (signUpButton.equals(e.getSource())){
            this.dispose();
            new SignUp("Sign up", this.getWidth(), this.getHeight());
        }
    }
}
