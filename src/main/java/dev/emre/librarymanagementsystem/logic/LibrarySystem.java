package dev.emre.librarymanagementsystem.logic;

import dev.emre.librarymanagementsystem.models.Book;
import dev.emre.librarymanagementsystem.models.Person;
import dev.emre.librarymanagementsystem.models.filters.BookFilter;
import dev.emre.librarymanagementsystem.models.filters.PersonFilter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class LibrarySystem {
    private final BookService bookService;
    private final PersonService personService;
    private final LoanService loanService;
    private final FeeService feeService;

    public LibrarySystem(BookService bookService, PersonService personService, LoanService loanService, FeeService feeService) {
        this.bookService = bookService;
        this.personService = personService;
        this.loanService = loanService;
        this.feeService = feeService;
    }
    public LibrarySystem() {
        this(new BookService(), new PersonService(), new LoanService(new FeeService(), new BookService(), new PersonService()), new FeeService());
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
    public void borrowBook(String bookId, String personId, LocalDate borrowDate){
        loanService.issueLoan(bookId, personId, borrowDate);
    }
    public void returnBook(String bookId, boolean isDamaged, LocalDate returnDate){
        loanService.returnBook(bookId, isDamaged,returnDate);
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
    public void deleteBook(String id){
        if(loanService.isBookBorrowed(id)){
            throw new IllegalArgumentException("Book cannot be deleted because it is borrowed");
        }
        bookService.deleteBook(id);
    }
    public void deletePerson(String id){
        if(loanService.hasActiveLoan(id)){
            throw new IllegalArgumentException("Person cannot be deleted because they have an active loan");
        }
        if(!personService.findPersonById(id).getFees().isEmpty()){
            throw new IllegalArgumentException("Person cannot be deleted because they have fees");
        }
        personService.deletePerson(id);
    }


}
