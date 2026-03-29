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


   public void BookService(){

   }
   public void addBook(Book book){
       if(book == null){
           throw new IllegalArgumentException("Book cannot be null");
       }
       if(books.containsKey(book.getId())){
           throw new IllegalArgumentException("Book already exists");
       }

       books.put(book.getId(), book);
   }
   public void removeBook(Book book){
       if(!books.containsKey(book.getId())){
           throw new IllegalArgumentException("Book not found");
       }
       books.remove(book.getId());
   }
   public void updateBook(String id, Book updatedbook){
       if(updatedbook == null){
           throw new IllegalArgumentException("Book cannot be null");
       }
       if(id == null){
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
