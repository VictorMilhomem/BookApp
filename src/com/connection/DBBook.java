package com.connection;

import com.models.Book;
import com.models.User;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBBook extends DBConnection{

    public DBBook(String title){
        super(title);
    }

    public ArrayList<Book> createBook(ArrayList<Book> books,String bookName, String bookSnippet, String bookAuthor, double bookReview, User user){
        final String INSERT_BOOK_SQL = "INSERT INTO public.books VALUES (default, ?, ?, ?, ?, ?);";
        final String CHECK_USER = "SELECT * FROM public.books WHERE username=?;";
        final String DELETE_BOOK_LIST = "DELETE FROM public.books WHERE username=?;";
        String[] booknames = {""};
        String[] bookauthors = {""};
        String[] snippets = {""};
        Double[] reviews = {0.0};
        String username = user.getUserName();

        Book temp = new Book(bookName, bookSnippet, bookAuthor, bookReview);
        books.add(temp);

        int i = 0;
        for (Book b: books){
            booknames[i] = b.getBookName();
            bookauthors[i] = b.getBookAuthor();
            snippets[i] = b.getBookSnippet();
            reviews[i] = b.getBookReview();
            i++;
        }

        try {
            Array arrayBookName = connection.createArrayOf("text", booknames);
            Array arrayBookAuthors = connection.createArrayOf("text", bookauthors);
            Array arrayBookSnippets = connection.createArrayOf("text", snippets);
            Array arrayReviews = connection.createArrayOf("double precision", reviews);

            PreparedStatement checkUser = connection.prepareStatement(CHECK_USER);
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_BOOK_SQL);

            checkUser.setString(1, username);
            ResultSet rs = checkUser.executeQuery();
            if(rs.next()){
                PreparedStatement deleteCurrentBookList = connection.prepareStatement(DELETE_BOOK_LIST);
                deleteCurrentBookList.setString(1, username);
                deleteCurrentBookList.executeUpdate();
            }

            preparedStatement.setArray(1, arrayBookName);
            preparedStatement.setArray(2,arrayBookAuthors);
            preparedStatement.setString(3, username);
            preparedStatement.setArray(4, arrayBookSnippets);
            preparedStatement.setArray(5, arrayReviews);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    public ArrayList<Book> updateBooks(ArrayList<Book> books, User user){
        final String UPDATE_BOOKNAME = "UPDATE public.books SET bookname=? WHERE username=?";
        final String UPDATE_BOOKAUTHOR = "UPDATE public.books SET author=? WHERE username=?";
        final String UPDATE_SNIPPET = "UPDATE public.books SET snippet=? WHERE username=?";
        final String UPDATE_REVIEW = "UPDATE public.books SET review=? WHERE username=?";
        String[] booknames = {""};
        String[] bookauthors = {""};
        String[] snippets = {""};
        Double[] reviews = {0.0};
        String username = user.getUserName();

        int i = 0;
        for (Book b: books){
            booknames[i] = b.getBookName();
            bookauthors[i] = b.getBookAuthor();
            snippets[i] = b.getBookSnippet();
            reviews[i] = b.getBookReview();
            i++;
        }

        try {
            Array arrayBookName = connection.createArrayOf("text", booknames);
            Array arrayBookAuthors = connection.createArrayOf("text", bookauthors);
            Array arrayBookSnippets = connection.createArrayOf("text", snippets);
            Array arrayReviews = connection.createArrayOf("double precision", reviews);

            PreparedStatement updateBookName = connection.prepareStatement(UPDATE_BOOKNAME);
            PreparedStatement updateBookAuthors = connection.prepareStatement(UPDATE_BOOKAUTHOR);
            PreparedStatement updateSnippets = connection.prepareStatement(UPDATE_SNIPPET);
            PreparedStatement updateReview = connection.prepareStatement(UPDATE_REVIEW);

            updateBookName.setArray(1, arrayBookName);
            updateBookName.setString(2, username);
            updateBookAuthors.setArray(1, arrayBookAuthors);
            updateBookAuthors.setString(2, username);
            updateSnippets.setArray(1, arrayBookSnippets);
            updateSnippets.setString(2, username);
            updateReview.setArray(1, arrayReviews);
            updateReview.setString(2, username);

            updateBookName.executeUpdate();
            updateBookAuthors.executeUpdate();
            updateSnippets.executeUpdate();
            updateReview.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }
}
