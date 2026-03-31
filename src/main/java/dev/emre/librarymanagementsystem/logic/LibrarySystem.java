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
        BookService bs = new BookService();
        PersonService ps = new PersonService();
        FeeService fs = new FeeService();
        this(bs, ps, new LoanService(fs, bs, ps), fs);
    }

    /**
     * Adds a book to the library system.
     *
     * @param book the {@code Book} object to be added. The book must not be null
     *             and must have a unique ID. It should not already exist in the system.
     * @throws IllegalArgumentException if the provided book is null.
     * @throws IllegalArgumentException if a book with the same ID already exists.
     */
    public void addBook(Book book){
        bookService.addBook(book);
    }

    /**
     * Borrows a book for a person on a specific date. The method ensures the book is not
     * already borrowed before issuing the loan.
     *
     * @param bookId the unique identifier of the book to be borrowed; must not be null or blank
     * @param personId the unique identifier of the person borrowing the book; must not be null or blank
     * @param borrowDate the date on which the book is borrowed; must not be null
     * @throws IllegalArgumentException if the book is already borrowed or any of the parameters are invalid
     */
    public void borrowBook(String bookId, String personId, LocalDate borrowDate){
        loanService.issueLoan(bookId, personId, borrowDate);
    }

    /**
     * Handles the return of a book by delegating the operation to the loan service.
     *
     * @param bookId the unique identifier of the book being returned; must not be null or blank
     * @param isDamaged a boolean indicating whether the book is returned in a damaged condition
     * @param returnDate the date on which the book is returned; must not be null
     * @throws IllegalArgumentException if no loan is found for the given bookId
     */
    public void returnBook(String bookId, boolean isDamaged, LocalDate returnDate){
        loanService.returnBook(bookId, isDamaged,returnDate);
    }

    /**
     * Updates the details of an existing book in the system.
     *
     * @param id the unique identifier of the book to be updated. Must not be null, blank, or differ from the ID of the provided updated book.
     * @param updatedBook the {@code Book} object containing updated details. Must not be null and must have the same ID as the specified book ID.
     * @throws IllegalArgumentException if the id is null, blank, does not match the ID of the provided updated book,
     *                                  or if updatedBook is null, or if no book is found with the given ID in the system.
     */
    public void updateBook(String id, Book updatedBook){
        bookService.updateBook(id, updatedBook);
    }

    /**
     * Searches for books in the collection based on the criteria specified in the given filter.
     * The method retrieves all books, applies the filter to find matches, and sorts the result.
     *
     * @param filter the {@code BookFilter} object containing the search criteria; must not be null.
     * @return a sorted {@code List} of {@code Book} objects that match the specified filter criteria.
     */
    public List<Book> searchBooks(BookFilter filter){
        return bookService.getAllBooks().stream()
                .filter(filter.buildPredicate())
                .sorted()
                .toList();
    }

    /**
     * Retrieves a book with the specified unique identifier.
     *
     * @param id the unique identifier of the book to be retrieved. Must not be null or blank.
     * @return the {@code Book} object corresponding to the specified ID.
     * @throws IllegalArgumentException if the provided ID is null, blank, or if no book is found with the given ID.
     */
    public Book findBookById(String id){
        return bookService.findBookById(id);
    }

    /**
     * Retrieves a {@code Person} object with the specified unique identifier.
     *
     * @param id the unique identifier of the person to be retrieved; must not be null or blank.
     * @return the {@code Person} object corresponding to the specified ID, or {@code null} if no person is found with the given ID.
     * @throws IllegalArgumentException if the provided ID is null or blank.
     */
    public Person findPersonById(String id){
        return personService.findPersonById(id);
    }

    /**
     * Searches for persons in the collection based on the criteria specified in the given filter.
     * The method retrieves all persons, applies the filter to find matches, and sorts the result.
     *
     * @param filter the {@code PersonFilter} object containing the search criteria; must not be null.
     * @return a sorted {@code List} of {@code Person} objects that match the specified filter criteria.
     */
    public List<Person> searchPersons(PersonFilter filter){
        return personService.getAllPersonsList().stream()
                .filter(filter.buildPredicate())
                .sorted()
                .toList();
    }

    /**
     * Updates the details of an existing person in the system.
     *
     * @param id the unique identifier of the person to be updated. Must not be null, blank,
     *           or differ from the ID of the provided updated person.
     * @param updatedPerson the {@code Person} object containing the updated details. Must not
     *                      be null and must have the same ID as the specified person ID.
     * @throws IllegalArgumentException if the id is null, blank, does not match the ID of
     *                                  the provided updated person, if updatedPerson is null,
     *                                  or if no person is found with the given ID in the system.
     */
    public void updatePerson(String id, Person updatedPerson){
        personService.updatePerson(id, updatedPerson);
    }

    /**
     * Registers a new person in the system by adding the provided {@code Person} object to the person service.
     *
     * @param person the {@code Person} object to be registered; must not be null or already exist in the system.
     * @throws IllegalArgumentException if the provided person is null or if a person with the same ID already exists in the system.
     */
    public void registerPerson(Person person){
        personService.addPerson(person);
    }

    /**
     * Deletes a book from the library system based on its unique identifier.
     * The book can only be deleted if it is not currently borrowed.
     *
     * @param id the unique identifier of the book to be deleted. Must not be null or blank.
     * @throws IllegalArgumentException if the book is currently borrowed or if the provided ID is invalid.
     */
    public void deleteBook(String id){
        if(loanService.isBookBorrowed(id)){
            throw new IllegalArgumentException("Book cannot be deleted because it is borrowed");
        }
        bookService.deleteBook(id);
    }

    /**
     * Retrieves a list of all books available in the library system.
     *
     * @return a {@code List} of {@code Book} objects representing all books in the library.
     */
    public List<Book> getAllBooks(){
        return bookService.getAllBooks();
    }

    /**
     * Deletes a person from the system if they meet the necessary conditions.
     * A person can only be deleted if they have no active loans and no outstanding fees.
     *
     * @param id the unique identifier of the person to be deleted; must not be null or blank
     * @throws IllegalArgumentException if the person has any active loans, outstanding fees, or if the provided ID is invalid
     */
    public void deletePerson(String id){
        if(loanService.hasActiveLoan(id)){
            throw new IllegalArgumentException("Person cannot be deleted because they have an active loan");
        }
        if(!personService.findPersonById(id).getFees().isEmpty()){
            throw new IllegalArgumentException("Person cannot be deleted because they have fees");
        }
        personService.deletePerson(id);
    }

    /**
     * Retrieves a list of all persons registered in the system.
     *
     * @return a {@code List} of {@code Person} objects representing all persons in the system.
     */
    public List<Person> getAllPersons(){
        return personService.getAllPersonsList();
    }

    /**
     * Retrieves a list of currently active loans in the library system.
     *
     * @return a {@code List} of {@code Loan} objects representing all active loans.
     */
    public List<Loan> getActiveLoans(){
        return loanService.getActiveLoans();
    }

    /**
     * Retrieves a list of all loans in the library system.
     *
     * @return a {@code List} of {@code Loan} objects representing all loans,
     *         including both active and retired loans.
     */
    public List<Loan> getAllLoans(){
        return loanService.getAllLoans();
    }

    /**
     * Processes the payment of a specific fee for a particular person.
     * The method retrieves the person using their unique identifier and
     * delegates the fee payment operation to the relevant person entity.
     *
     * @param personId the unique identifier of the person making the payment; must not be null or blank
     * @param feeId the unique identifier of the fee to be paid; must not be null or blank
     * @throws IllegalArgumentException if the provided personId or feeId is null, blank, or invalid
     */
    public void payPersonFee(String personId, String feeId){
        Person person = personService.findPersonById(personId);
        person.payFee(feeId);
    }

    /**
     * Retrieves a list of overdue loans from the library system. A loan is considered overdue
     * if its due date is before the current date.
     *
     * @return a {@code List} of {@code Loan} objects representing all overdue loans.
     */
    public List<Loan> getOverdueLoans(){
        return loanService.getActiveLoans().stream()
                .filter(loan -> loan.getDueDate().isBefore(LocalDate.now()))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a list of books currently borrowed by a specific person.
     *
     * @param personId the unique identifier of the person whose borrowed books are to be retrieved; must not be null or blank.
     * @return a {@code List} of {@code Book} objects representing the books currently borrowed by the person.
     * @throws IllegalArgumentException if the provided personId is null or blank.
     */
    public List<Book> getBooksBorrowedByPerson(String personId){
        return loanService.getBorrowedBookIdsByPerson(personId).stream()
                .map(bookService::findBookById)
                .toList();

    }


}
