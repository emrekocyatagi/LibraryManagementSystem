package dev.emre.librarymanagementsystem.models;

import dev.emre.librarymanagementsystem.models.enums.BookCondition;
import dev.emre.librarymanagementsystem.models.enums.Genre;

public class Book {
    private long id;
    private String title;
    private String author;
    private Genre genre;
    private int totalCopies;
    private int availableCopies;
    private BookCondition bookCondition = BookCondition.NEW;

    public Book(
            long id,
            String title,
            String author,
            Genre genre,
            int totalCopies
    ) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        if(totalCopies < 0) throw new IllegalArgumentException("Total copies cannot be negative.");
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies;

    }



    public BookCondition getBookCondition() {
        return bookCondition;
    }
    public void setBookCondition(BookCondition bookCondition) {
        this.bookCondition = bookCondition;
    }

    /**
     * Decreases the number of copies of the book and available copies.
     * @param numberOfCopies    how many copies to decrease
     */
    public void decreaseCopies(int numberOfCopies) {
        if(numberOfCopies <= 0) throw new IllegalArgumentException("NUmber of Copies cannot be negative.");
        if(availableCopies < numberOfCopies) throw new IllegalArgumentException("Not enough copies.");
        availableCopies -= numberOfCopies;
    }

    /**
     * Increases the number of copies of the book and available copies.
     * @param numberOfCopies    how many copies to increase
     */
    public void increaseCopies(int numberOfCopies) {
        if(numberOfCopies <= 0) throw new IllegalArgumentException("NUmber of Copies cannot be negative.");
        if(availableCopies + numberOfCopies > totalCopies) throw new IllegalArgumentException("Not enough room in the library.");
        availableCopies += numberOfCopies;
    }


    public void markedAsDamaged() {
        bookCondition = BookCondition.DAMAGED;
    }

    /**
     * Adds copies to the total number of copies and available copies.
     * @param numberOfCopies    how many copies to add
     */
    public void addCopies(int numberOfCopies) {
        if(numberOfCopies <= 0) throw new IllegalArgumentException("NUmber of Copies cannot be negative.");
        totalCopies += numberOfCopies;
        availableCopies += numberOfCopies;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title =  title;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    public Genre getGenre() {
        return genre;
    }
    public void setGenre(Genre genre) {
        this.genre = genre;
    }
    public int getTotalCopies() {
        return totalCopies;
    }
    public int getAvailableCopies() {
        return availableCopies;
    }
    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }
    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("\nTitel: %s, Author: %s Genre: %s ", title, author, genre);
    }
}
