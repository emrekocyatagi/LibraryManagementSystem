package dev.emre.librarymanagementsystem.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Represents a single book loan between a person and a book.
 * including the loan date, due date, return date, etc.
 */
public class Loan {
    private static final int MAX_LOAN_DAYS = 30;
    private final long id;
    private Person person;
    private Book book;
    private LocalDate loanDate;
    private LocalDate returnDate;
    private boolean isReturned;
    public Loan(long id, Person person, Book book, LocalDate loanDate) {
        this.id = id;
        this.person = person;
        this.book = book;
        this.loanDate = loanDate;
    }

    public long getId() {
        return id;
    }
    public Person getPerson() {
        return person;
    }
    public LocalDate getLoanDate() {
        return loanDate;
    }

    public LocalDate getDueDate() {
        return loanDate.plusDays(MAX_LOAN_DAYS );
    }
    public Book getBook() {
        return book;
    }
    public LocalDate getReturnDate() {
        return returnDate;
    }
    public boolean isReturned() {
        return isReturned;
    }

    /**
     * Checks if the loan is overdue.
     * @param today     date to check
     * @return true if the loan is overdue, false otherwise
     */
    public boolean isOverdue(LocalDate today) {
        return !isReturned && today.isAfter(getDueDate());
    }

    /**
     * Marks the loan as returned.
     * @param returnDate    date of return
     * @return true if the loan was marked as returned, false otherwise
     */
    public boolean markAsReturned(LocalDate returnDate) {
        if(returnDate == null) return false;
        if(returnDate.isBefore(loanDate)) return false;
        this.returnDate = returnDate;
        isReturned = true;
        return true;
    }

    /**
     * Calculates the fine for this loan based on delay and book condition.
     * @param damaged   condition of the book after return
     * @return a BigDecimal representing the fine, or 0 if the loan is not returned yet
     */
    public BigDecimal calculateFine(boolean damaged) {
        if(!isReturned || returnDate == null) return BigDecimal.ZERO;
        BigDecimal fee = BigDecimal.ZERO;
        long dayslate = getDaysOverdue(returnDate);
        if(dayslate > 0){
            long weekLate = (dayslate + 6) / 7;
            fee = fee.add(BigDecimal.valueOf(weekLate * 2));
        }
        if(damaged){
            fee = fee.add(BigDecimal.valueOf(10));
        }
        return fee;
    }

    /**
     * Returns the number of days the loan is overdue.
     * @param date  date to check
     * @return the number of days the loan is overdue, or 0 if the loan is not overdue
     */
    public long getDaysOverdue(LocalDate date) {
        if (!isOverdue(date)) return 0;
        return java.time.temporal.ChronoUnit.DAYS.between(getDueDate(), date);
    }

    @Override
    public String toString() {
        return "Loan{id=" + id +
                ", person=" + person.toString() +
                ", book=" + book.toString() +
                ", loanDate=" + loanDate +
                ", dueDate=" + getDueDate() +
                ", returned=" + isReturned +
                '}';
    }
}
