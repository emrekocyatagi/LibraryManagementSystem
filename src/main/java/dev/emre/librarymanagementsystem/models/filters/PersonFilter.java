package dev.emre.librarymanagementsystem.models.filters;

import dev.emre.librarymanagementsystem.models.Person;

import java.math.BigDecimal;
import java.util.Map;
import java.util.function.Predicate;

public class PersonFilter implements IFilterObject<Person>{
    private final Integer minBooks;
    private final Integer maxBooks;
    private final BigDecimal minFees;
    private final Map<String, Long> loanCountsByPersonId;
    protected PersonFilter(Integer minBooks, Integer maxBooks, BigDecimal minFees, Map<String, Long> loanCountsByPersonId) {
        this.minBooks = minBooks;
        this.maxBooks = maxBooks;
        this.minFees = minFees;
        this.loanCountsByPersonId = loanCountsByPersonId;
    }

    @Override
    public Predicate<Person> buildPredicate() {
        return person -> {
            long count = loanCountsByPersonId.getOrDefault(person.getId(), 0L);
            boolean matchesCount = (minBooks == null || count >= minBooks)
                    && (maxBooks == null || count <= maxBooks);

            BigDecimal totalFees = person.calculateTotalFees();
            boolean matchesFees = (minFees == null || totalFees.compareTo(minFees) >= 0);

            return matchesCount && matchesFees;
        };
    }
}
