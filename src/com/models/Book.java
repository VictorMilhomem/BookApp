package com.models;

public class Book {
    private int id;
    private String bookName;
    private String bookPicture;
    private String bookAuthor;
    private String bookFile;
    private double bookReview;

    public Book (int id, String bookName, String bookPicture, String bookAuthor, String bookFile, double bookReview){
        this.id = id;
        this.bookName = bookName;
        this.bookPicture = bookPicture;
        this.bookAuthor = bookAuthor;
        this.bookFile = bookFile;
        this.bookReview = bookReview;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookPicture() {
        return bookPicture;
    }

    public void setBookPicture(String bookPicture) {
        this.bookPicture = bookPicture;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookFile() {
        return bookFile;
    }

    public void setBookFile(String bookFile) {
        this.bookFile = bookFile;
    }

    public double getBookReview() {
        return bookReview;
    }

    public void setBookReview(double bookReview) {
        this.bookReview = bookReview;
    }
}
