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


    public Loan(String bookId, String personId){
        this.loanId = UUID.randomUUID().toString();
        this.bookId= bookId;
        this.personId=personId;
        this.borrowDate= LocalDate.now();
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


    @Override
    public String toString() {
        return String.format("Loan{loanId=%s, personId=%s, bookId=%s, borrowDate=%s, dueDate=%s, returnDate=%s}", loanId, personId, bookId, borrowDate, dueDate, returnDate);
    }
}
