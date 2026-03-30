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
    public boolean isBookBorrowed(String bookId){
        return loans.values().stream()
                .anyMatch(loan ->loan.getBookId().equals(bookId)
                && loan.getReturnDate() == null);
    }
    public boolean hasActiveLoan(String personId){
        return loans.values().stream()
                .anyMatch(loan ->loan.getPersonId().equals(personId)
                && loan.getReturnDate() == null);
    }
    public List<Loan> getAllLoans(){
        return new ArrayList<>(loans.values());
    }
    public List<Loan> getActiveLoans(){
        return loans.values().stream()
                .filter(loan -> loan.getReturnDate() == null)
                .collect(Collectors.toList());
    }
    public Map<String, Long> getActiveLoanCountsByPerson(){
        return loans.values().stream()
                .filter(loan -> loan.getReturnDate() == null)
                .collect(Collectors.groupingBy(
                        Loan::getPersonId,
                        Collectors.counting()
                ));
    }

}
