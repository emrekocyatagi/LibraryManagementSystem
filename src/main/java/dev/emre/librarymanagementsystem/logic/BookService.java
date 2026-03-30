package dev.emre.librarymanagementsystem.logic;
import dev.emre.librarymanagementsystem.models.Book;
import dev.emre.librarymanagementsystem.models.enums.BookCondition;
import dev.emre.librarymanagementsystem.models.enums.Genre;

import java.util.*;

/*
* Das Verwalten von Büchern (Hinzufügen, Bearbeiten und Löschen von Büchern).
 */
public class BookService {
    private final Map<String, Book> books = new HashMap<>();


   public BookService(){}

    /**
     * Retrieves a list of all books currently managed by the service.
     *
     * @return a list of Book objects representing all the books in the collection
     */
   public List<Book> getAllBooks(){
       return new ArrayList<>(books.values());
   }

    /**
     * Adds a new book to the collection.
     *
     * @param book the {@code Book} object to be added. Must not be {@code null}.
     *             The book must have a unique ID and should not already exist
     *             in the collection.
     * @throws IllegalArgumentException if the provided book is {@code null}.
     * @throws IllegalArgumentException if a book with the same ID already exists in the collection.
     */
   public void addBook(Book book){
       if(book == null){
           throw new IllegalArgumentException("Book cannot be null");
       }
       if(books.containsKey(book.getId())){
           throw new IllegalArgumentException("Book already exists");
       }

       books.put(book.getId(), book);
   }

    /**
     * Updates an existing book in the collection by replacing it with the provided updated book.
     *
     * @param id the unique identifier of the book to be updated. Must not be null, blank, or mismatched with the ID of the provided updated book.
     * @param updatedbook the updated {@code Book} object containing new details. Must not be null and must have the same ID as the specified book ID.
     * @throws IllegalArgumentException if the id is null, blank, does not match the ID of the provided updated book,
     *                                  or if updatedbook is null, or if no book is found with the given ID.
     */
   public void updateBook(String id, Book updatedbook){
       if(updatedbook == null){
           throw new IllegalArgumentException("Book cannot be null");
       }
       if(id == null || id.isBlank()){
           throw new IllegalArgumentException("Book id cannot be null");
       }
       if(!id.equals(updatedbook.getId())){
           throw new IllegalArgumentException("Book id does not match");
       }
       if(!books.containsKey(id)){
           throw new IllegalArgumentException("Book not found");
       }

       books.put(id, updatedbook);
   }

    /**
     * Finds and retrieves a book with the specified ID.
     *
     * @param id the unique identifier of the book to be retrieved. Must not be null or blank.
     * @return the {@code Book} object corresponding to the specified ID.
     * @throws IllegalArgumentException if the provided ID is null, blank, or if no book is found with the given ID.
     */
   public Book findBookById(String id){
       if(id == null || id.isBlank()){
           throw new IllegalArgumentException("Book id cannot be null");
       }
       Book book = books.get(id);
       if(book == null){
           throw new IllegalArgumentException("Book not found");
       }
       return book;
   }

    /**
     * Deletes a book from the collection based on the provided unique identifier.
     *
     * @param id the unique identifier of the book to be deleted. Must not be null
     *           and the book must exist in the collection.
     * @throws IllegalArgumentException if the provided ID is null or if no book
     *                                  is found with the given ID.
     */
   public void deleteBook(String id){
       if(id == null){
           throw new IllegalArgumentException("Book id cannot be null");
       }
       if(!books.containsKey(id)){
           throw new IllegalArgumentException("Book not found");
       }
       books.remove(id);
   }

}
