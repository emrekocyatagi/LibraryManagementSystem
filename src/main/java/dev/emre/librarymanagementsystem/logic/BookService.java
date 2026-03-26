package dev.emre.librarymanagementsystem.logic;
import dev.emre.librarymanagementsystem.models.Book;
import dev.emre.librarymanagementsystem.models.enums.BookCondition;
import dev.emre.librarymanagementsystem.models.enums.Genre;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class BookService {
    private final List<Book> books = new ArrayList<>();
    private long nextId = 1;

    /**
     * Updates a book in the list of books.
     * @param updated   new values for the book
     * @return true if the book was updated, false otherwise
     */
    public boolean updateBook(Book updated) {
        if(updated == null)return false;
        long id = updated.getId();

        for(Book book : books) {
            if(book.getId() == id) {
                book.setTitle(updated.getTitle());
                book.setAuthor(updated.getAuthor());
                book.setGenre(updated.getGenre());
                book.addCopies( updated.getTotalCopies() - book.getTotalCopies());
                book.setAvailableCopies(updated.getAvailableCopies());
                book.setBookCondition(updated.getBookCondition());
                return true;
            }
        }
        return false;
    }

    /**
     * Returns all books in the list.
     * @return a list of all books
     */
    public List<Book> getAllBooks(){
        return new ArrayList<>(books);
    }

    /**
     * Returns a book with the given id.
     * @param id    an id of a book
     * @return an Optional containing the book with the given id, or empty if no book with the given id exists
     */
    public Optional<Book> getBookById(long id){
        if(id <= 0)return Optional.empty();
        return books.stream().filter(book -> book.getId() == id).findFirst();
    }

    /**
     * Creates a new book and adds it to the list of books.
     * @param title         title of the book
     * @param author        author of the book
     * @param genre         genre of the book
     * @param totalCopies   total number of copies of the book
     * @return the created book
     */
    public Book createBook(String title, String author, Genre genre, int totalCopies){
        if(title == null || author == null || genre == null)throw new IllegalArgumentException("Null values are not allowed!");
        long id = nextId++;
        Book book = new Book(id,title,author,genre,totalCopies);
        books.add(book);
        return book;

    }

    /**
     * Deletes a book from the list of books.
     * @param id an id of a book
     * @return true if the book was deleted, false otherwise
     */
    public boolean deleteBook(long id){
    return books.removeIf(book -> book.getId() == id);
    }

    /**
     * Returns books with the given condition.
     * @param genre genre of the books
     * @return a list of books with the given genre
     */
    public List<Book> findByGenre(Genre genre){
        if(genre == null)throw new IllegalArgumentException("Null values are not allowed!");
        if(books.isEmpty())return new ArrayList<>();
        return books.stream().filter(book -> book.getGenre() == genre).toList();
    }

    /**
     * Returns books with the given author.
     * @param author author of the books
     * @return a list of books with the given author
     */
    public List<Book> findByAuthor(String author){
        if(author == null)throw new IllegalArgumentException("Null values are not allowed!");
        if(books.isEmpty())return new ArrayList<>();
        return books.stream().filter(book -> book.getAuthor().equalsIgnoreCase(author)).toList();
    }

    /**
     * Returns books with the given title.
     * @param title title of the books
     * @return a list of books with the given title
     */
    public List<Book> findByTitle(String title){
        if(title == null)throw new IllegalArgumentException("Null values are not allowed!");
        if(books.isEmpty())return new ArrayList<>();
        String needle = title.toLowerCase();
        return books.stream().filter(book -> book.getTitle() != null && book.getTitle().toLowerCase().contains(needle)).toList();
    }
}
