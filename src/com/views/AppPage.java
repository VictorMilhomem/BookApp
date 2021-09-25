package com.views;

import com.connection.DBBook;
import com.connection.DBUser;
import com.models.Book;
import com.models.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
    private JLabel bookNameLabel;
    private JButton nextBookButton;
    private JButton addBookButton;
    private JTextField bookAuthorField;
    private JTextField bookReviewField;
    private JTextArea bookSnippetArea;
    private JButton cancelAddBookButton;
    private JButton confirmAddBookButton;
    private JTextField bookNameField;
    private JLabel bookNameEditLabel;
    private final DBUser database;
    private final DBBook bookDatabase;
    private ArrayList<Book> books;

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

        database = new DBUser("AppConnection");
        bookDatabase = new DBBook("Books");

        // TODO: when initializing the app verify if the user already have a list of books and display it
        books = new ArrayList<>();


        //---- Buttons --------------------------//
        myAccountButton.addActionListener(this);
        bookButton.addActionListener(this);
        editButton.addActionListener(this);
        deleteAccountButton.addActionListener(this);
        confirmChangesButton.addActionListener(this);
        cancelChangesButton.addActionListener(this);
        exitButton.addActionListener(this);
        addBookButton.addActionListener(this);
        confirmAddBookButton.addActionListener(this);
        cancelAddBookButton.addActionListener(this);
        nextBookButton.addActionListener(this);
        //---- End Buttons ----------------------//

        //---- Account -------------------------//
        firstNameTextField.setText(this.userInApp.getFirstName());
        lastNameTextField.setText(this.userInApp.getLastName());
        usernameTextField.setText(this.userInApp.getUserName());
        emailTextField.setText(this.userInApp.getEmail());
        //---- End Account ---------------------//

    }

    public void setBookPanel(ArrayList<Book> books, int i){
        Book book = books.get(i);
        bookNameLabel.setText(book.getBookName());
        bookAuthorField.setText(book.getBookAuthor());
        bookReviewField.setText(String.valueOf(book.getBookReview()));
        bookSnippetArea.setText(book.getBookSnippet());
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

    public void editUserVisible(boolean visible, boolean editable) {
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

    public void editBookVisible(boolean visible, boolean editable){
        bookAuthorField.setEditable(editable);
        bookReviewField.setEditable(editable);
        bookSnippetArea.setEditable(editable);
        bookNameField.setEditable(editable);

        bookNameField.setVisible(visible);
        bookNameEditLabel.setVisible(visible);
        cancelAddBookButton.setVisible(visible);
        confirmAddBookButton.setVisible(visible);
        addBookButton.setVisible(!visible);
        nextBookButton.setVisible(!visible);
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
            editUserVisible(false, true);
        }

        if (confirmChangesButton.equals(e.getSource())){
            updateUser(this.userInApp);
            database.updateDBUser(this.userInApp);
            database.closeConnection();
            editUserVisible(true, false);
        }

        if (cancelChangesButton.equals(e.getSource())){
            editUserVisible(true, false);
        }

        if (deleteAccountButton.equals(e.getSource())){
            database.deleteUser(this.userInApp);
            database.closeConnection();
            JOptionPane.showMessageDialog(null, "Conta excluída com sucesso");
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

        // Book Panel
        if (addBookButton.equals(e.getSource())){
            editBookVisible(true, true);
        }

        if (cancelAddBookButton.equals(e.getSource())){
            editBookVisible(false, false);
        }

        if (confirmAddBookButton.equals(e.getSource())){

            String bookName = bookNameField.getText();
            String authors =  bookAuthorField.getText();
            int review = Integer.parseInt(bookReviewField.getText());
            String snippet = bookSnippetArea.getText();

            if (bookName.equals("") || authors.equals("") || snippet.equals("") || bookReviewField.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Preencha todos os campos");
            }
            else {
                books = bookDatabase.createBook(books, bookName, snippet, authors, review, userInApp);
                editBookVisible(false, false);
                setBookPanel(books, 0);
            }
        }

    }

}
