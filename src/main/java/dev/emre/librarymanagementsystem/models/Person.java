package dev.emre.librarymanagementsystem.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

/**
 * Represents a person.
 */
public class Person implements Comparable<Person> {
    private final String id;
    private final String name;
    private final String surname;
    private final LocalDate birthDate;
    private Address address;
    private List<Fee> fees = new ArrayList<>();

    private Person(PersonBuilder builder){
        this.id = builder.id;
        this.name = builder.name;
        this.surname = builder.surname;
        this.birthDate = builder.birthDate;
        this.address = builder.address;
        this.fees = builder.fees;
    }

    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public LocalDate getBirthDate() {
        return birthDate;
    }
    public Address getAddress() {
        return address;
    }
    public String getId() {
        return id;
    }
    public List<Fee> getFees() {
        return fees;
    }
    public void addFee(Fee fee){
        if(fee == null){
            throw new IllegalArgumentException("Fee cannot be null");
        }
        fees.add(fee);
    }
    public BigDecimal calculateTotalFees(){
        return fees.stream().map(Fee::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    public static PersonBuilder builder(){
        return new PersonBuilder();
    }
    public PersonBuilder toBuilder(){
        return new PersonBuilder()
                .id(id)
                .name(name)
                .surname(surname)
                .birthdate(birthDate)
                .address(address);
    }

    public static class PersonBuilder{
        private String id;
        private String name;
        private String surname;
        private LocalDate birthDate;
        private Address address;
        private final List<Fee> fees = new ArrayList<>();
        public void addFee(Fee fee){
            if(fee == null){
                throw new IllegalArgumentException("Fee cannot be null");
            }
            fees.add(fee);
        }
        public PersonBuilder id(String id){
            this.id = id;
            return this;
        }
        public PersonBuilder name(String name){
            this.name = name;
            return this;
        }
        public PersonBuilder surname(String surname){
            this.surname = surname;
            return this;
        }
        public PersonBuilder birthdate(LocalDate birthDate){
            this.birthDate = birthDate;
            return this;
        }
        public PersonBuilder address(Address address){
            this.address = address;
            return this;
        }
        public Person build(){
            if(id == null){
                this.id = UUID.randomUUID().toString();
            }

            validate();

            return new Person(this);
        }
        private void validate(){
            if(name == null || name.isEmpty()){
                throw new IllegalArgumentException("Person name cannot be null or empty");
            }
            if(surname == null || surname.isEmpty()){
                throw new IllegalArgumentException("Person surname cannot be null or empty");
            }
            if(birthDate == null){
                throw new IllegalArgumentException("Person birthdate cannot be null");
            }
            if(address == null){
                throw new IllegalArgumentException("Person address cannot be null");
            }
        }

    }

    @Override
    public String toString() {
        return String.format("%s %s, geboren %s ", name, surname, birthDate);
    }
    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if( obj == null || getClass() != obj.getClass() ) return false;
        Person person = (Person) obj;
        return Objects.equals(id, person.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    @Override
    public int compareTo(Person other) {
        if(other == null){
            return 1;
        }
        return this.name.compareTo(other.name);
    }

}
