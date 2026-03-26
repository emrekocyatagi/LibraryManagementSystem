package dev.emre.librarymanagementsystem.logic;

import dev.emre.librarymanagementsystem.models.Book;
import dev.emre.librarymanagementsystem.models.Loan;
import dev.emre.librarymanagementsystem.models.Person;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
/*
 Wichtiges Muster
Optional<T> → erst isEmpty()/isPresent() prüfen

Danach mit .get() den Wert holen

Dann mit dem „normalen“ Objekt weiterarbeiten
 * */
public class LoanService {
    LocalDate today = LocalDate.now();
    private long nextId = 1;
    private final List<Loan> loans = new ArrayList<>();
    private final BookService bookService;
    private final PersonService personService;

    public LoanService(BookService bookService, PersonService personService)
    {
        this.bookService = bookService;
        this.personService = personService;

    }

    /**
     * Creates a loan for a book and a person.
     * @param bookid    an id of a book
     * @param personId  an id of a person
     * @param loanDate  date of loan
     * @return an Optional containing the created loan, or empty if the book or person does not exist
     */
    public Optional<Loan> createLoan(long bookid, long personId, LocalDate loanDate) {
        Optional<Book> bookOpt = bookService.getBookById(bookid);
        Optional<Person> personOpt = personService.getPersonById(personId);
        if(bookOpt.isEmpty() || personOpt.isEmpty()) {
            return Optional.empty();
        }
        Book book = bookOpt.get();
        if(book.getAvailableCopies() <= 0)return Optional.empty();
        book.decreaseCopies(1);
        bookOpt.get().setAvailableCopies(bookOpt.get().getAvailableCopies() - 1);
        long id = nextId++;
        Loan loan = new Loan(id,personOpt.get(), bookOpt.get(), loanDate);
        loans.add(loan);
        return Optional.of(loan);
    }

    /**
     * Returns all loans.
     * @return a list of all loans
     */
    public List<Loan> getAllLoans(){
        return new ArrayList<>(loans);
    }

    /**
     * Returns a loan with the given id.
     * @param id an id of a loan
     * @return an Optional containing the loan with the given id, or empty if no loan with the given id exists
     */
    public Optional<Loan> getLoanById(long id){
        if(id <= 0)return Optional.empty();
        return loans.stream().filter(loan -> loan.getId() == id).findFirst();
    }

    /**
     * Calculate and Returns a fine for a book after returning it.
     * @param loanId        an id of a loan
     * @param returnDate    Date of return
     * @param damaged       Condition of a book after return
     * @return an Optional containing the fine, or empty if the loan does not exist
     */
    public Optional<BigDecimal> returnBook(long loanId, LocalDate returnDate, boolean damaged){
    Optional<Loan> loanOpt = getLoanById(loanId);
    if(loanOpt.isEmpty())return Optional.empty();

    Loan loan = loanOpt.get();

    if(!loan.markAsReturned(returnDate)){
        return Optional.empty();
    }
    BigDecimal fee = loan.calculateFine(damaged);
    loan.getPerson().addFees(fee);

    Book book = loan.getBook();
    if(damaged){
        book.markedAsDamaged();
    }
    book.increaseCopies(1);
    return Optional.of(fee);
    }

    /**
     * Returns all loans for a person.
     * @param personId an id of a person
     * @return a list of loans for the given person
     */
    public List<Loan> getLoansByPersonId(long personId){
        return loans.stream().filter(loan -> loan.getPerson().getId() == personId).toList();
    }

    /**
     * Counts the number of active loans for a person.
     * @param personId an id of a person
     * @return the number of active loans
     */
    public long countActiveLoansForPerson(long personId){
        if (personId <= 0) throw new IllegalArgumentException("Person ID must be greater than 0!");
        return getLoansByPersonId(personId).stream()
                .filter(loan -> !loan.isReturned())
                .count();
    }

    /**
     * Checks if a person has active loans.
     * @param personId an id of a person
     * @return true if the person has active loans, false otherwise
     */
    public boolean hasActiveLoans(long personId){
        return countActiveLoansForPerson(personId) > 0;
    }

}
