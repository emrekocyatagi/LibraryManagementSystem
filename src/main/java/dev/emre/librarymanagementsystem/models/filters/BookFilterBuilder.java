package dev.emre.librarymanagementsystem.models.filters;

import dev.emre.librarymanagementsystem.models.enums.Genre;

public class BookFilterBuilder {
    private String title;
    private String author;
    private Genre genre;

    public BookFilterBuilder withTitle(String title) {
        this.title = title;
        return this;
    }
    public BookFilterBuilder withAuthor(String author) {
        this.author = author;
        return this;
    }
    public BookFilterBuilder withGenre(Genre genre) {
        this.genre = genre;
        return this;
    }
    public BookFilter build() {
        return new BookFilter(title, author, genre);
    }

}
