package dev.emre.librarymanagementsystem.models.filters;

import java.util.function.Predicate;

public interface IFilterObject<T> {
    /**
     * Method to build Predicate using the Filter Object
     * @return Predicate of T
     */
    Predicate<T> buildPredicate();
}
