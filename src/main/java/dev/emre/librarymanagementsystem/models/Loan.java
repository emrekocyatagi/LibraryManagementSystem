package dev.emre.librarymanagementsystem.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

/**
 * Represents a single book loan between a person and a book.
 * including the loan date, due date, return date, etc.
 */
public class Loan {
    private static final int LOAN_PERIOD_MONTHS = 1;
    private final String loanId;
    private final String personId;
    private final String bookId;
    private final LocalDate borrowDate;
    private final LocalDate dueDate;
    private LocalDate returnDate;

    // For production - uses today
    public Loan(String bookId, String personId) {
        this(bookId, personId, LocalDate.now());
    }


    // For testing - custom date
    public Loan(String bookId, String personId, LocalDate borrowDate) {
        if(bookId == null || bookId.isBlank()){
            throw new IllegalArgumentException("Book id cannot be null");
        }
        if(personId == null || personId.isBlank()){
            throw new IllegalArgumentException("Person id cannot be null");
        }
        if(borrowDate == null){
            throw new IllegalArgumentException("Borrow date cannot be null");
        }

        this.loanId = UUID.randomUUID().toString();
        this.bookId= bookId;
        this.personId=personId;
        this.borrowDate= borrowDate;
        this.dueDate = this.borrowDate.plusMonths(LOAN_PERIOD_MONTHS);
    }

    public String getLoanId() {
        return loanId;
    }

    public String getPersonId() {
        return personId;
    }

    public String getBookId() {
        return bookId;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }
    public LocalDate getBorrowDate(){
        return borrowDate;
    }
    public LocalDate getReturnDate() {
        return returnDate;
    }
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return String.format("Loan{loanId=%s, personId=%s, bookId=%s, borrowDate=%s, dueDate=%s, returnDate=%s}", loanId, personId, bookId, borrowDate, dueDate, returnDate);
    }
}
