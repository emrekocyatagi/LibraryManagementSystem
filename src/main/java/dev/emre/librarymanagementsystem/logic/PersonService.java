package dev.emre.librarymanagementsystem.logic;

import dev.emre.librarymanagementsystem.models.Address;
import dev.emre.librarymanagementsystem.models.Book;
import dev.emre.librarymanagementsystem.models.Loan;
import dev.emre.librarymanagementsystem.models.Person;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PersonService {
    private final List<Person> persons = new ArrayList<>();
    private long nextId = 1;

    /**
     * Updates a Person in the list of persons.
     * @param updated   a new Person with the updated values
     * @return true if the Person was updated, false otherwise
     */
    public boolean updatePerson(Person updated){
        if(updated == null)return false;
        if(updated.getId() == 0)return false;
        for (Person person : persons) {
            if(person.getId() == updated.getId()){
                person.setName(updated.getName());
                person.setSurname(updated.getSurname());
                person.setBirthDate(updated.getBirthDate());
                person.setAddress(updated.getAddress());
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a list of all persons.
     * @return a list of all persons
     */
    public List<Person> getAllPersons(){
        return new ArrayList<>(persons);
    }

    /**
     * Returns a Person with the given id.
     * @param id    an id of a Person
     * @return an Optional containing the Person with the given id, or empty if no Person with the given id exists
     */
    public Optional<Person> getPersonById(long id){
        if(id <= 0)return Optional.empty();
        return persons.stream().filter(person -> person.getId() == id).findFirst();
    }

    /**
     * Creates a new Person and adds it to the list of persons.
     * @param name      first name of the person, must not be null
     * @param surname   last name of the person, must not be null
     * @param birthDate birth date of the person, must not be null
     * @param address   address of the person, must not be null
     * @return the created Person with a generated id
     * @throws IllegalArgumentException if any of the parameters is null
     */
    public Person createPerson(String name, String surname, LocalDate birthDate, Address address){
        if(name == null || surname == null || birthDate == null || address == null)throw new IllegalArgumentException("Null values are not allowed!");
        long id = nextId++;
        Person person = new Person(id,name, surname, birthDate, address);
        persons.add(person);
        return person;
    }

    /**
     * Deletes a Person from the list of persons.
     * @param id  id of the Person to delete
     * @return true if the Person was deleted, false otherwise
     */
    public boolean deletePerson(long id){
        return persons.removeIf(person -> person.getId() == id);
    }

    /**
     * Returns a Person with the given name.
     * @param name  first name of the Person
     * @return an Optional containing the Person with the given name, or empty if no Person with the given name exists
     */
    public Optional<Person> getPersonByName(String name){
        if(name == null)return Optional.empty();
        return persons.stream().filter(person -> name.equalsIgnoreCase(person.getName())).findFirst();
    }

    /**
     * Returns a Person with the given surname.
     * @param surname  last name of the Person
     * @return an Optional containing the Person with the given surname, or empty if no Person with the given surname exists
     */
    public Optional<Person> getPersonBySurname(String surname){
        return persons.stream().filter(person -> person.getSurname().equals(surname)).findFirst();
    }

    /**
     * Returns a list of persons with fees between the given range.
     * @param from beginning of the range
     * @param to end of the range
     * @return a list of persons with fees between the given range
     */
    public List<Person> findByOpenFeesRange(BigDecimal from, BigDecimal to){
        return persons.stream().filter(person -> person.hasFeesInRange(from, to)).toList();
    }

    /**
     * Returns a list of persons with active loans in the given range.
     * @param from beginning of the range
     * @param to end of the range
     * @param loanService LoanService instance
     * @return a list of persons with active loans in the given range
     */
    public List<Person> findByActiveLoanCountRange(int from, int to, LoanService loanService) {
        return persons.stream()
                .filter(person -> {
                    long count = loanService.countActiveLoansForPerson(person.getId());
                    return count >= from && count <= to;
                })
                .toList();
    }

    /**
     * Finds persons with active loans.
     * @param loanService LoanService instance
     * @return a list of persons with active loans
     */
    public List<Person> findPersonsWithActiveLoans(LoanService loanService){
        return persons.stream().filter(person -> loanService.hasActiveLoans(person.getId())).toList();
    }

    /**
     * Finds persons without active loans.
     * @param loanService LoanService instance
     * @return a list of persons without active loans
     */
    public List<Person> findPersonsWithoutActiveLoans(LoanService loanService){
        return persons.stream().filter(person -> !loanService.hasActiveLoans(person.getId())).toList();
    }

}
