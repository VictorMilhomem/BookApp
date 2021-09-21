package com.views;

import com.connection.DBConnection;
import com.models.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppPage extends JFrame implements ActionListener {

    private JPanel mainPanel;
    private JPanel BorderPanel;
    private JPanel AppPanel;
    private final User userInApp;
    private JButton bookButton;
    private JButton myAccountButton;
    private JPanel myAccountPanel;
    private JPanel bookPanel;
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JTextField usernameTextField;
    private JTextField emailTextField;
    private JButton editButton;
    private JButton cancelChangesButton;
    private JButton confirmChangesButton;
    private JButton deleteAccountButton;
    private JLabel passwordLabel;
    private JLabel confirmPasswordLabel;
    private JPasswordField confirmPasswordField;
    private JPasswordField passwordField;
    private JButton exitButton;
    private final DBConnection database;

    public AppPage(String title, User user, int WIDTH, int HEIGHT){
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        this.setSize(WIDTH, HEIGHT);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setResizable(false);
        this.userInApp = user;

        database = new DBConnection("AppConnection");

        //---- Buttons --------------------------//
        myAccountButton.addActionListener(this);
        bookButton.addActionListener(this);
        editButton.addActionListener(this);
        deleteAccountButton.addActionListener(this);
        confirmChangesButton.addActionListener(this);
        cancelChangesButton.addActionListener(this);
        exitButton.addActionListener(this);
        //---- End Buttons ----------------------//

        //---- Account -------------------------//
        firstNameTextField.setText(this.userInApp.getFirstName());
        lastNameTextField.setText(this.userInApp.getLastName());
        usernameTextField.setText(this.userInApp.getUserName());
        emailTextField.setText(this.userInApp.getEmail());
        //---- End Account ---------------------//

    }

    public void updateUser(User user){
        user.setEmail(emailTextField.getText());
        user.setUserName(usernameTextField.getText());
        user.setLastName(lastNameTextField.getText());
        user.setFirstName(firstNameTextField.getText());
        if (String.valueOf(passwordField.getPassword()).equals(String.valueOf(confirmPasswordField.getPassword()))) {
            user.setPassword(String.valueOf(passwordField.getPassword()));
        }

    }

    public void edit(boolean visible, boolean editable) {
        // this function set the visibility of the editButton and deleteAccountButton
        // and do the opposite for the  confirmChangesButton and cancelChangesButton
        editButton.setVisible(visible);
        deleteAccountButton.setVisible(visible);
        // password field and label visibility
        passwordField.setVisible(!visible);
        passwordLabel.setVisible(!visible);
        confirmPasswordLabel.setVisible(!visible);
        confirmPasswordField.setVisible(!visible);

        // buttons visibility
        confirmChangesButton.setVisible(!visible);
        cancelChangesButton.setVisible(!visible);

        // fields Editable
        firstNameTextField.setEditable(editable);
        lastNameTextField.setEditable(editable);
        usernameTextField.setEditable(editable);
        emailTextField.setEditable(editable);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (myAccountButton.equals(e.getSource())){
            bookPanel.setVisible(false);
            myAccountPanel.setVisible(true);
        }
        if (bookButton.equals(e.getSource())){
            myAccountPanel.setVisible(false);
            bookPanel.setVisible(true);
        }
        if (editButton.equals(e.getSource())){
            edit(false, true);
        }

        if (confirmChangesButton.equals(e.getSource())){
            updateUser(this.userInApp);
            database.updateDBUser(this.userInApp);
            database.closeConnection();
            edit(true, false);
        }

        if (cancelChangesButton.equals(e.getSource())){
            edit(true, false);
        }

        if (deleteAccountButton.equals(e.getSource())){
            database.deleteUser(this.userInApp);
            database.closeConnection();
            this.dispose();
            JFrame frame = new LoginPage("Login");
            frame.setSize(this.getWidth(), this.getHeight());
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            frame.setResizable(false);
        }

        if (exitButton.equals(e.getSource())){
            this.dispose();
            JFrame frame = new LoginPage("Login");
            frame.setSize(this.getWidth(), this.getHeight());
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            frame.setResizable(false);
        }

    }

}
