package dev.emre.librarymanagementsystem.logic;

import dev.emre.librarymanagementsystem.models.Book;
import dev.emre.librarymanagementsystem.models.Fee;
import dev.emre.librarymanagementsystem.models.Loan;
import dev.emre.librarymanagementsystem.models.Person;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class LoanService {
    private final Map<String, Loan> loans = new HashMap<>();
    private final FeeService feeService;
    private final BookService bookService;
    private final PersonService personService;

    public LoanService(FeeService feeService, BookService bookService, PersonService personService) {
        this.feeService = feeService;
        this.bookService = bookService;
        this.personService = personService;
    }

    public void issueLoan(String bookId, String personId){
        //Transaction - Rollback could be implemented here
        Person person = personService.findPersonById(personId);
        Book book = bookService.findBookById(bookId);
        if(book.isBorrowed()){
            throw new IllegalArgumentException("Book is already borrowed");
        }
        book.setBorrowed(true);
        Loan loan = new Loan(bookId, personId);
        loans.put(loan.getLoanId(), loan);
    }

    public void returnBook(String bookId, boolean isDamaged){
        Loan loan = findLoanByBookId(bookId);

        Fee damagedFee = feeService.calculateDamagedFee(isDamaged);
        Fee lateFee = feeService.calculateLateFee(loan.getDueDate(), LocalDate.now());
        Person person = personService.findPersonById(loan.getPersonId());

        if(damagedFee != null){
            person.addFee(damagedFee);
        }
        if(lateFee != null){
            person.addFee(lateFee);
        }
        Book book = bookService.findBookById(loan.getBookId());
        book.setBorrowed(false);
        loans.remove(loan.getLoanId());

    }
    public long countActiveLoansByPerson(String personId){
        return loans.values().stream()
                .filter(loan ->loan.getPersonId().equals(personId))
                .count();
    }
    public List<String> getBorrowedBookIdsByPerson(String personId){
        return loans.values().stream()
                .filter(loan ->loan.getPersonId().equals(personId))
                .map(Loan::getBookId)
                .toList();
    }
    private Loan findLoanByBookId(String bookId){
        return loans.values().stream()
                .filter(loan ->loan.getBookId().equals(bookId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Loan not found"));
    }

}
