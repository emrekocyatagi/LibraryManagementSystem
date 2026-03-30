package dev.emre.librarymanagementsystem.logic;

import dev.emre.librarymanagementsystem.models.Book;
import dev.emre.librarymanagementsystem.models.Fee;
import dev.emre.librarymanagementsystem.models.Loan;
import dev.emre.librarymanagementsystem.models.Person;
import dev.emre.librarymanagementsystem.models.enums.BookCondition;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class LoanService {
    private final Map<String, Loan> loans = new HashMap<>();
    private final List<Loan> loanHistory = new ArrayList<>();
    private final FeeService feeService;
    private final BookService bookService;
    private final PersonService personService;

    public LoanService(FeeService feeService, BookService bookService, PersonService personService) {
        this.feeService = feeService;
        this.bookService = bookService;
        this.personService = personService;
    }

    /**
     * Issues a loan for a specific book to a person on a given borrow date.
     * Checks if the book is already borrowed before issuing the loan.
     * If the book is already borrowed, an exception is thrown.
     *
     * @param bookId the unique identifier of the book to be loaned; must not be null or blank
     * @param personId the unique identifier of the person borrowing the book; must not be null or blank
     * @param borrowDate the date on which the book is borrowed; must not be null
     * @throws IllegalArgumentException if the book is already borrowed or any of the parameters are invalid
     */
    public void issueLoan(String bookId, String personId, LocalDate borrowDate){
        //Transaction - Rollback could be implemented here
        Person person = personService.findPersonById(personId);
        Book book = bookService.findBookById(bookId);
        if(book.isBorrowed()){
            throw new IllegalArgumentException("Book is already borrowed");
        }
        book.setBorrowed(true);
        Loan loan = new Loan(bookId, personId, borrowDate);
        loans.put(loan.getLoanId(), loan);
    }
    public Loan getLoan(String loanId){
        if(loanId == null || loanId.isBlank()){
            throw new IllegalArgumentException("Loan id cannot be null");
        }
        Loan loan = loans.get(loanId);
        if(loan == null){
            throw new IllegalArgumentException("Loan not found");
        }
        return loan;
    }

    /**
     * Processes the return of a book by updating its loan status, calculating and assigning
     * any applicable fees for damage or late return, and updating records accordingly.
     *
     * @param bookId the unique identifier of the book being returned; must not be null or blank
     * @param isDamaged a boolean indicating whether the book is returned in a damaged condition
     * @param returnDate the date on which the book is returned; must not be null
     * @throws IllegalArgumentException if no loan is found for the given bookId
     */
    public void returnBook(String bookId, boolean isDamaged, LocalDate returnDate){
        Loan loan = findLoanByBookId(bookId);

        Fee damagedFee = feeService.calculateDamagedFee(isDamaged, returnDate);
        Fee lateFee = feeService.calculateLateFee(loan.getDueDate(), returnDate);
        Person person = personService.findPersonById(loan.getPersonId());

        if(damagedFee != null){
            person.addFee(damagedFee);
        }
        if(lateFee != null){
            person.addFee(lateFee);
        }
        Book book = bookService.findBookById(loan.getBookId());
        book.setBorrowed(false);
        loan.setReturnDate(returnDate);
        if(isDamaged){
            book.setBookCondition(BookCondition.DAMAGED);
        }
        loanHistory.add(loan);
        loans.remove(loan.getLoanId());

    }

    /**
     * Counts the number of active loans associated with a specific person.
     *
     * @param personId the unique identifier of the person for whom active loans are counted; must not be null or blank
     * @return the count of active loans for the specified person as a long value
     */
    public long countActiveLoansByPerson(String personId){
        return loans.values().stream()
                .filter(loan ->loan.getPersonId().equals(personId))
                .count();
    }

    /**
     * Retrieves a list of book IDs borrowed by a specific person.
     *
     * @param personId the unique identifier of the person whose borrowed books are to be retrieved; must not be null or blank
     * @return a list of book IDs currently borrowed by the specified person
     */
    public List<String> getBorrowedBookIdsByPerson(String personId){
        return loans.values().stream()
                .filter(loan ->loan.getPersonId().equals(personId))
                .map(Loan::getBookId)
                .toList();
    }

    /**
     * Retrieves a loan associated with a specific book ID.
     * Searches through the collection of loans and returns the first matching loan.
     * If no loan is found for the given book ID, an exception is thrown.
     *
     * @param bookId the unique identifier of the book whose loan is to be retrieved; must not be null or blank
     * @return the {@code Loan} object associated with the specified book ID
     * @throws IllegalArgumentException if no loan is found for the specified book ID
     */
    private Loan findLoanByBookId(String bookId){
        return loans.values().stream()
                .filter(loan ->loan.getBookId().equals(bookId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Loan not found"));
    }

    /**
     * Checks if a book with the specified ID is currently borrowed.
     * A book is considered borrowed if there is an active loan for it
     * and the return date for that loan has not been recorded.
     *
     * @param bookId the unique identifier of the book to check; must not be null or blank
     * @return true if the book is currently borrowed, false otherwise
     */
    public boolean isBookBorrowed(String bookId){
        return loans.values().stream()
                .anyMatch(loan ->loan.getBookId().equals(bookId)
                && loan.getReturnDate() == null);
    }

    /**
     * Checks if a person with the specified ID has any active loans.
     * A loan is considered active if its return date has not been recorded.
     *
     * @param personId the unique identifier of the person to check; must not be null or blank
     * @return true if the person has at least one active loan, false otherwise
     */
    public boolean hasActiveLoan(String personId){
        return loans.values().stream()
                .anyMatch(loan ->loan.getPersonId().equals(personId)
                && loan.getReturnDate() == null);
    }

    /**
     * Retrieves a list of all loans currently stored in the system.
     * This includes both active and returned loans.
     *
     * @return a list containing all loans stored in the system
     */
    public List<Loan> getAllLoans(){
        return new ArrayList<>(loans.values());
    }

    /**
     * Retrieves a list of all active loans in the system.
     * A loan is considered active if its return date has not been recorded.
     *
     * @return a {@code List} of {@code Loan} objects representing the active loans
     */
    public List<Loan> getActiveLoans(){
        return loans.values().stream()
                .filter(loan -> loan.getReturnDate() == null)
                .collect(Collectors.toList());
    }

    /**
     * Computes and returns the number of active loans for each person.
     * A loan is considered active if its return date has not been recorded (i.e., it is null).
     *
     * @return a map where the keys are person IDs and the values are the counts of active loans associated with each person
     */
    public Map<String, Long> getActiveLoanCountsByPerson(){
        return loans.values().stream()
                .filter(loan -> loan.getReturnDate() == null)
                .collect(Collectors.groupingBy(
                        Loan::getPersonId,
                        Collectors.counting()
                ));
    }

}
