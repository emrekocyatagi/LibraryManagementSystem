package dev.emre.librarymanagementsystem.models.filters;

import dev.emre.librarymanagementsystem.logic.LoanService;

import java.math.BigDecimal;
import java.util.Map;

public class PersonFilterBuilder {
    private Integer minBooks;
    private Integer maxBooks;
    private BigDecimal minFees;
    private LoanService loanService;

    public PersonFilterBuilder(LoanService loanService) {
        this.loanService = loanService;
    }

    public PersonFilterBuilder minBooks(Integer minBooks) {
        this.minBooks = minBooks;
        return this;
    }

    public PersonFilterBuilder maxBooks(Integer maxBooks) {
        this.maxBooks = maxBooks;
        return this;
    }
    public PersonFilterBuilder minFees(BigDecimal minFees) {
        this.minFees = minFees;
        return this;
    }
    public PersonFilterBuilder minFees(double minFees) {
        this.minFees = BigDecimal.valueOf(minFees);
        return this;
    }

    public PersonFilter build() {
        Map<String, Long> loanCounts = loanService.getActiveLoanCountsByPerson();
        return new PersonFilter(minBooks, maxBooks, minFees, loanCounts);
    }
}
