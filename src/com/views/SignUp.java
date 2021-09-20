package com.views;

import com.connection.DBConnection;
import com.models.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUp extends JFrame implements ActionListener {

    private JPanel mainPanel;
    private JPanel borderPanel;
    private JPanel signUpPanel;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField useNameField;
    private JTextField emailField;
    private JLabel firstNameLabel;
    private JLabel lastNameLabel;
    private JLabel userNameLabel;
    private JLabel emailLabel;
    private JLabel passwordLabel;
    private JLabel confirmPasswordLabel;
    private JButton signUpButton;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private final DBConnection dataBase;
    protected User user;
    private int id = 1;

    public SignUp(String title, int WIDTH, int HEIGHT){
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        this.setSize(WIDTH, HEIGHT);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setResizable(false);
        dataBase = new DBConnection("SignUp");

        //----- Buttons-----//
        signUpButton.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (signUpButton.equals(e.getSource())){

            int userId = id;
            String userFirstName = firstNameField.getText();
            String userLastName = lastNameField.getText();
            String username = useNameField.getText();
            String email = emailField.getText();
            String password = String.valueOf(confirmPasswordField.getPassword());

            if (password.equals(String.valueOf(passwordField.getPassword()))){
                user = new User(userId, userFirstName, userLastName, username, email, password);
                dataBase.createNewUser(user);
                dataBase.closeConnection();
                // TODO: after signing up the user go's to the app home page
                id++;
            }else {
                JOptionPane.showMessageDialog(null, "As senhas não combinam");
            }

        }
    }
}
