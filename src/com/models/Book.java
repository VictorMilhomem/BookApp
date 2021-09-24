package com.models;

public class Book {
    private String bookName;
    private String bookSnippet;
    private String bookAuthor;
    private double bookReview;

    public Book(){

    }

    public Book (String bookName, String bookSnippet, String bookAuthor, double bookReview){
        this.bookName = bookName;
        this.bookSnippet = bookSnippet;
        this.bookAuthor = bookAuthor;
        this.bookReview = bookReview;
    }

    public String getBookSnippet() {
        return bookSnippet;
    }

    public void setBookSnippet(String bookSnippet) {
        this.bookSnippet = bookSnippet;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }


    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public double getBookReview() {
        return bookReview;
    }

    public void setBookReview(double bookReview) {
        this.bookReview = bookReview;
    }
}
