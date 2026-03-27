package dev.emre.librarymanagementsystem.models;

import dev.emre.librarymanagementsystem.models.enums.BookCondition;
import dev.emre.librarymanagementsystem.models.enums.Genre;

import java.util.Comparator;
import java.util.Objects;

public class Book implements Comparable<Book> {
    private final String title;
    private final String author;
    private final Genre genre;
    private BookCondition bookCondition = BookCondition.NEW;

    private Book(BookBuilder builder)
     {
        this.title = builder.title;
        this.author = builder.author;
        this.genre = builder.genre;
    }
    public static BookBuilder builder() {
        return new BookBuilder();
    }

    public BookCondition getBookCondition() {
        return bookCondition;
    }
    public void setBookCondition(BookCondition bookCondition) {
        this.bookCondition = bookCondition;
    }
    public void markedAsDamaged() {
        bookCondition = BookCondition.DAMAGED;
    }




public static class BookBuilder {
    private String title;
    private String author;
    private Genre genre;


    public BookBuilder title(String title) {
        this.title = title;
        return this;
    }
    public BookBuilder author(String author){
        this.author = author;
        return this;
    }
    public BookBuilder genre(Genre genre){
        this.genre = genre;
        return this;
    }
    public Book build(){
        return new Book(this);
    }
}
    @Override
    public String toString() {
        return String.format("\nTitel: %s, Author: %s Genre: %s ", title, author, genre);
    }

    /**
     * Compare 2 books bei their title.
     * @param  other 2nd book to compare
     * @return 1 if this is greater than other, 0 if they are equal, -1 if this is less than other.
     */
    @Override
    public int compareTo(Book other) {
        if(other == null){
            return 1;
        }
        return this.title.compareTo(other.title);
    }

    /**
     * Checks if 2 books are equal.
     * @param obj   the reference object with which to compare.
     * @return true if this object is the same as the obj argument; false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        Book book = (Book) obj;
        return title.equals(book.title) &&
                author.equals(book.author) &&
                genre.equals(book.genre);
    }
    @Override
    public int hashCode() {
        return Objects.hash(title, author, genre);
    }
}
