package dev.emre.librarymanagementsystem;

import dev.emre.librarymanagementsystem.logic.BookService;
import dev.emre.librarymanagementsystem.logic.LoanService;
import dev.emre.librarymanagementsystem.logic.PersonService;
import dev.emre.librarymanagementsystem.models.Address;
import dev.emre.librarymanagementsystem.models.Book;
import dev.emre.librarymanagementsystem.models.Loan;
import dev.emre.librarymanagementsystem.models.Person;
import dev.emre.librarymanagementsystem.models.enums.BookCondition;
import dev.emre.librarymanagementsystem.models.enums.Genre;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        PersonService personService = new PersonService();
        BookService bookService = new BookService();
        LoanService loanService = new LoanService(bookService, personService);

        Person p1 = personService.createPerson(
                "Emre", "Kocyatagi",
                LocalDate.of(1999, 1, 1),
                new Address("Erfurt", "Straße 1", "99084")
        );
        Person p2 = personService.createPerson(
                "Anna", "Schmidt",
                LocalDate.of(1995, 5, 10),
                new Address("Berlin", "Allee 2", "10115")
        );
        Person p3 = personService.createPerson(
                "John", "Doe",
                LocalDate.of(1990, 3, 20),
                new Address("Hamburg", "Weg 3", "20095")
        );

        Book b1 = bookService.createBook(
                "Clean Code", "Robert C. Martin",
                Genre.NONFICTION, 3
        );
        Book b2 = bookService.createBook(
                "Harry Potter", "J. K. Rowling",
                Genre.FANTASY,  2
        );

        loanService.createLoan(b1.getId(), p1.getId(), LocalDate.now().minusDays(5));
        loanService.createLoan(b2.getId(), p1.getId(), LocalDate.now().minusDays(1));
        loanService.createLoan(b1.getId(), p2.getId(), LocalDate.now().minusDays(2));

        System.out.println("Mit aktiven Loans: " + personService.findPersonsWithActiveLoans(loanService));
        System.out.println("Ohne aktive Loans: " + personService.findPersonsWithoutActiveLoans(loanService));
        System.out.println("1–2 aktive Loans: " + personService.findByActiveLoanCountRange(1, 2, loanService));
    }


}
