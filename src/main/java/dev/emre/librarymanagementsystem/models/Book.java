package dev.emre.librarymanagementsystem.models;

import dev.emre.librarymanagementsystem.models.enums.BookCondition;
import dev.emre.librarymanagementsystem.models.enums.Genre;

import java.util.Comparator;
import java.util.Objects;
import java.util.UUID;

public class Book implements Comparable<Book> {
    private final String title;
    private final String author;
    private final Genre genre;
    private final String id;
    private BookCondition bookCondition;
    private boolean isBorrowed;

    private Book(BookBuilder builder)
     {
        this.title = builder.title;
        this.author = builder.author;
        this.genre = builder.genre;
        this.id = builder.id;
        this.bookCondition = builder.bookCondition;
        this.isBorrowed = builder.isBorrowed;
    }
    public static BookBuilder builder() {
        return new BookBuilder();
    }
    public BookCondition getBookCondition() {
        return bookCondition;
    }
    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public Genre getGenre() {
        return genre;
    }
    public String getId() {
        return id;
    }
    public boolean isBorrowed() {
        return isBorrowed;
    }
    public void setBorrowed(boolean borrowed) {
        isBorrowed = borrowed;
    }
    public void setBookCondition(BookCondition bookCondition) {
        this.bookCondition = bookCondition;
    }
    public BookBuilder toBuilder(){
        return new BookBuilder()
                .id(id)
                .title(title)
                .author(author)
                .genre(genre)
                .bookCondition(bookCondition)
                .borrowed(isBorrowed);
    }


// ********* BOOK BUILDER ***********
public static class BookBuilder {
    private String title;
    private String author;
    private Genre genre;
    private String id;
    private BookCondition bookCondition = BookCondition.NEW;
    private boolean isBorrowed;




    public BookBuilder title(String title) {
        this.title = title;
        return this;
    }
    public BookBuilder borrowed(boolean borrowed){
        this.isBorrowed = borrowed;
        return this;
    }
    public BookBuilder id(String id){
        this.id = id;
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
    public BookBuilder bookCondition(BookCondition bookCondition){
        this.bookCondition = bookCondition;
        return this;
    }
    public Book build(){
        if(id == null){
            this.id = UUID.randomUUID().toString();
        }

        validate();

        return new Book(this);
    }
    private void validate(){
        if(title == null || title.isEmpty()){
            throw new IllegalArgumentException("Book title cannot be null or empty");
        }
        if(author == null || author.isEmpty()){
            throw new IllegalArgumentException("Book author cannot be null or empty");
        }
        if(genre == null){
            throw new IllegalArgumentException("Book genre cannot be null");
        }
        if(bookCondition == null){
            throw new IllegalArgumentException("Book condition cannot be null");
        }

    }
}
    @Override
    public String toString() {
        return String.format("\nTitle: %s, Author: %s Genre: %s ", title, author, genre);
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
                genre.equals(book.genre) &&
                id.equals(book.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(title, author, genre,id);
    }
}
