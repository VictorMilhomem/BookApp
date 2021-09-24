package com.connection;

import com.models.Book;
import com.models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBBook extends DBConnection{

    public DBBook(String title){
        super(title);
    }

    public Book createBook(User user, String bookName, String bookSnippet, String bookAuthor, double bookReview){
        final String INSERT_BOOKS_SQL = "INSERT INTO public.books" +
                " (id, bookname, author, username, snippet, review) VALUES " +
                " (default, ?, ?, ?, ?, ? );";
        Book book = new Book(bookName, bookSnippet, bookAuthor, bookReview);

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_BOOKS_SQL);
            preparedStatement.setString(1, bookName);
            preparedStatement.setString(2, bookAuthor);
            preparedStatement.setString(3, user.getUserName());
            preparedStatement.setString(4, bookSnippet);
            preparedStatement.setDouble(5, bookReview);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }

    public void updateBook(Book book, User user){
        final String UPDATE_BOOKNAME = "UPDATE public.books" + " SET bookname=? " + " WHERE username=?;";
        final String UPDATE_AUTHOR = "UPDATE public.books" + " SET author=? " + " WHERE username=?;";
        final String UPDATE_SNIPPET = "UPDATE public.books" + " SET snippet=? " + " WHERE username=?;";
        final String UPDATE_REVIEW = "UPDATE public.books" + " SET review=? " + " WHERE username=?;";
        try {
            PreparedStatement bookNameUpdate = connection.prepareStatement(UPDATE_BOOKNAME);
            PreparedStatement authorUpdate = connection.prepareStatement(UPDATE_AUTHOR);
            PreparedStatement snippetUpdate = connection.prepareStatement(UPDATE_SNIPPET);
            PreparedStatement reviewUpdate = connection.prepareStatement(UPDATE_REVIEW);

            bookNameUpdate.setString(1, book.getBookName());
            bookNameUpdate.setString(2, user.getUserName());
            authorUpdate.setString(1, book.getBookAuthor());
            authorUpdate.setString(2, user.getUserName());
            snippetUpdate.setString(1, book.getBookSnippet());
            snippetUpdate.setString(2, user.getUserName());
            reviewUpdate.setDouble(1, book.getBookReview());
            reviewUpdate.setString(2, user.getUserName());

            bookNameUpdate.executeUpdate();
            authorUpdate.executeUpdate();
            snippetUpdate.executeUpdate();
            reviewUpdate.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteBook(Book book, User user){
        final String DELETE_BOOK_SQL = "DELETE FROM public.books" +
                " WHERE username= ? AND bookname= ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BOOK_SQL);
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, book.getBookName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
