package dev.emre.librarymanagementsystem.logic;

import dev.emre.librarymanagementsystem.models.Book;
import dev.emre.librarymanagementsystem.models.Loan;
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
    public void addBook(Book book){
        bookService.addBook(book);
    }
    public void borrowBook(String bookId, String personId, LocalDate borrowDate){
        loanService.issueLoan(bookId, personId, borrowDate);
    }
    public void returnBook(String bookId, boolean isDamaged, LocalDate returnDate){
        loanService.returnBook(bookId, isDamaged,returnDate);
    }
    public void updateBook(String id, Book updatedBook){
        bookService.updateBook(id, updatedBook);
    }

    public List<Book> searchBooks(BookFilter filter){
        return bookService.getAllBooks().stream()
                .filter(filter.buildPredicate())
                .sorted()
                .toList();
    }
    public Book findBookById(String id){
        return bookService.findBookById(id);
    }
    public Person findPersonById(String id){
        return personService.findPersonById(id);
    }
    public List<Person> searchPersons(PersonFilter filter){
        return personService.getAllPersonsList().stream()
                .filter(filter.buildPredicate())
                .sorted()
                .toList();
    }
    public void updatePerson(String id, Person updatedPerson){
        personService.updatePerson(id, updatedPerson);
    }
    public void registerPerson(Person person){
        personService.addPerson(person);
    }

    public void deleteBook(String id){
        if(loanService.isBookBorrowed(id)){
            throw new IllegalArgumentException("Book cannot be deleted because it is borrowed");
        }
        bookService.deleteBook(id);
    }
    public List<Book> getAllBooks(){
        return bookService.getAllBooks();
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

    public List<Person> getAllPersons(){
        return personService.getAllPersonsList();
    }
    public List<Loan> getActiveLoans(){
        return loanService.getActiveLoans();
    }
    public List<Loan> getAllLoans(){
        return loanService.getAllLoans();
    }
    public void payPersonFee(String personId, String feeId){
        Person person = personService.findPersonById(personId);
        person.payFee(feeId);
    }
    public List<Loan> getOverdueLoans(){
        return loanService.getActiveLoans().stream()
                .filter(loan -> loan.getDueDate().isBefore(LocalDate.now()))
                .collect(Collectors.toList());
    }
    public List<Book> getBooksBorrowedByPerson(String personId){
        return loanService.getBorrowedBookIdsByPerson(personId).stream()
                .map(bookService::findBookById)
                .toList();

    }


}
