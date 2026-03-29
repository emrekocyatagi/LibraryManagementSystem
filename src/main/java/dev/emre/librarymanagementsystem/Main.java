package dev.emre.librarymanagementsystem;


import dev.emre.librarymanagementsystem.logic.*;
import dev.emre.librarymanagementsystem.models.Address;
import dev.emre.librarymanagementsystem.models.Book;

import dev.emre.librarymanagementsystem.models.Person;
import dev.emre.librarymanagementsystem.models.enums.Genre;
import dev.emre.librarymanagementsystem.models.filters.BookFilter;
import dev.emre.librarymanagementsystem.models.filters.BookFilterBuilder;
import dev.emre.librarymanagementsystem.models.filters.PersonFilter;
import dev.emre.librarymanagementsystem.models.filters.PersonFilterBuilder;

import java.time.LocalDate;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        // 1. Sistemi Başlat
        LibrarySystem system = new LibrarySystem();

        // 2. BookBuilder ile Kitap Oluştur ve Ekle
        Book b1 = new Book.BookBuilder()
                .title("Effective Java")
                .author("Joshua Bloch")
                .genre(Genre.PROGRAMMING)
                .build();

        Book b2 = new Book.BookBuilder()
                .title("The Hobbit")
                .author("J.R.R. Tolkien")
                .genre(Genre.FANTASY)
                .build();

        system.addBook(b1);
        system.addBook(b2);

        // 3. PersonBuilder ile Kişi Oluştur ve Kaydet
        Address addressFromP1= new Address("Erfurt","dresdeneray","34567");
        Person p1 = new Person.PersonBuilder()
                .name("Emre")
                .surname("Soyadi")
                .birthdate(LocalDate.of(1990, 1, 1))
                .address(addressFromP1)
                .build();

        system.registerPerson(p1);

        // 4. İşlem Yap (Ödünç Verme)
        system.borrowBook(b1.getId(), p1.getId());

        // 5. Gelişmiş Filtreleme Testi (BookFilter + Builder)
        System.out.println("--- Arama Sonucu (Java Kitapları) ---");
        BookFilter programmingFilter = new BookFilterBuilder()
                .withTitle("")
                .withGenre(Genre.PROGRAMMING)
                .build();

        List<Book> results = system.searchBooks(programmingFilter);
        results.forEach(book -> System.out.println("Bulunan: " + book.getTitle()));

        // 6. Gelişmiş Filtreleme Testi (PersonFilter + Builder)
        System.out.println("\n--- Arama Sonucu (Borçlu veya Kitaplı Üyeler) ---");
        // Not: LibrarySystem içinde getLoanService() metodun olmalı
        PersonFilter borrowerFilter = new PersonFilterBuilder(system.getLoanService())
                .minBooks(1)
                .build();

        List<Person> activeUsers = system.searchPersons(borrowerFilter);
        activeUsers.forEach(user -> System.out.println("Aktif Üye: " + user.getName()));
    }
}