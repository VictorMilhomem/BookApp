package com.views;

import com.connection.DBConnection;
import com.connection.DBUser;
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
    private JLabel setTextFielValuesLabel;
    private final DBUser dataBase;
    protected User user;

    public SignUp(String title, int WIDTH, int HEIGHT) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        this.setSize(WIDTH, HEIGHT);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setResizable(false);
        dataBase = new DBUser("SignUp");

        //----- Buttons-----//
        signUpButton.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (signUpButton.equals(e.getSource())) {

            String userFirstName = firstNameField.getText().toLowerCase();
            String userLastName = lastNameField.getText().toLowerCase();
            String username = useNameField.getText();
            String email = emailField.getText().toLowerCase();
            String password = String.valueOf(confirmPasswordField.getPassword());

            if (userFirstName.equals("") || userLastName.equals("") || username.equals("") || email.equals("") || String.valueOf(passwordField.getPassword()).equals("")) {
                setTextFielValuesLabel.setVisible(true);

            } else if (password.equals(String.valueOf(passwordField.getPassword())) && !password.equals("")) {

                user = dataBase.createNewUser(userFirstName, userLastName, username, email, password);
                dataBase.closeConnection();
                this.dispose();
                new AppPage("AppHomePage", user, this.getWidth(), this.getHeight());

            } else {
                JOptionPane.showMessageDialog(null, "As senhas não combinam");
            }
        }

    }
}

