package dev.emre.librarymanagementsystem.models.filters;

import dev.emre.librarymanagementsystem.models.Book;
import dev.emre.librarymanagementsystem.models.enums.Genre;

import java.util.function.Predicate;

public class BookFilter implements IFilterObject<Book>{
    private final String title;
    private final String author;
    private final Genre genre;

    protected BookFilter(String title, String author, Genre genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    @Override
    public Predicate<Book> buildPredicate() {
        return book -> {
            boolean matchesGenre = (genre == null || book.getGenre().equals(genre));
            boolean matchesAuthor = (author == null || book.getAuthor().equalsIgnoreCase(author));
            boolean matchesTitle = (title == null || book.getTitle().toLowerCase().contains(title.toLowerCase()));
            return matchesGenre && matchesAuthor && matchesTitle;
        };
    }
}
