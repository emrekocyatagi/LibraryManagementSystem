package dev.emre.librarymanagementsystem.logic;

import dev.emre.librarymanagementsystem.models.Book;
import dev.emre.librarymanagementsystem.models.Person;
import dev.emre.librarymanagementsystem.models.filters.BookFilter;
import dev.emre.librarymanagementsystem.models.filters.PersonFilter;

import java.util.List;

public class LibrarySystem {
    private final BookService bookService;
    private final PersonService personService;
    private final LoanService loanService;
    private final FeeService feeService;

    public LibrarySystem() {
    this.bookService = new BookService();
    this.feeService = new FeeService();
    this.personService = new PersonService();
    this.loanService = new LoanService(feeService, bookService, personService);
    }
    public BookService getBookService() {
        return bookService;
    }
    public PersonService getPersonService() {
        return personService;
    }
    public LoanService getLoanService() {
        return loanService;
    }
    public FeeService getFeeService() {
        return feeService;
    }

    public void addBook(Book book){
        bookService.addBook(book);
    }
    public void registerPerson(Person person){
        personService.addPerson(person);
    }
    public void borrowBook(String bookId, String personId){
        loanService.issueLoan(bookId, personId);
    }
    public void returnBook(String bookId, boolean isDamaged){
        loanService.returnBook(bookId, isDamaged);
    }

    public List<Book> searchBooks(BookFilter filter){
        return bookService.getAllBooks().stream()
                .filter(filter.buildPredicate())
                .sorted()
                .toList();
    }
    public List<Person> searchPersons(PersonFilter filter){
        return personService.getAllPersonsList().stream()
                .filter(filter.buildPredicate())
                .sorted()
                .toList();
    }

}
