package dev.emre.librarymanagementsystem;


import dev.emre.librarymanagementsystem.models.Address;
import dev.emre.librarymanagementsystem.models.Book;

import dev.emre.librarymanagementsystem.models.Person;
import dev.emre.librarymanagementsystem.models.enums.Genre;

import java.time.LocalDate;


public class Main {
    public static void main(String[] args) {
    Book book = Book.builder().title("The Hobbit").author("A").genre(Genre.FICTION).build();
    Book book2 = Book.builder().title("The Hobbit").author("B").genre(Genre.FICTION).build();
    System.out.println("Equals"+book.equals(book2));
    System.out.println("Compare "+book.compareTo(book2));
    System.out.println(book.toString());


    Person person = Person.builder().name("Emre").surname("Kaya").birthdate(LocalDate.of(1999,1,4)).address(new Address("Erfurt","Street","1234")).build();
        System.out.println(person.toString());
    }

}
