package dev.emre.librarymanagementsystem.logic;


import dev.emre.librarymanagementsystem.models.Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonService {
    private final Map<String, Person> persons = new HashMap<>();

    public void addPerson(Person person){
        if(person == null){
            throw new IllegalArgumentException("Person cannot be null");
        }
        if(persons.containsKey(person.getId())){
            throw new IllegalArgumentException("Person already exists");
        }
        persons.put(person.getId(), person);
    }

    public void updatePerson(String id, Person updatedPerson){
        if(updatedPerson == null){
            throw new IllegalArgumentException("Person cannot be null");
        }
        if(id == null || id.isBlank()){
            throw new IllegalArgumentException("Person id cannot be null");
        }
        if(!id.equals(updatedPerson.getId())){
            throw new IllegalArgumentException("Person id does not match");
        }
        if(!persons.containsKey(id)){
            throw new IllegalArgumentException("Person not found");
        }
        persons.put(id, updatedPerson);
    }
    public Person findPersonById(String id){
        if(id == null || id.isBlank()){
            throw new IllegalArgumentException("Person id cannot be null");
        }
        Person person = persons.get(id);
        if(person == null){
            throw new IllegalArgumentException("Person not found");
        }
        return person;
    }
    public List<Person> getAllPersonsList(){
        return new ArrayList<>(persons.values()) ;
    }
    public void deletePerson(String id){
        if(id == null){
            throw new IllegalArgumentException("Person id cannot be null");
        }
        if(!persons.containsKey(id)){
            throw new IllegalArgumentException("Person not found");
        }
        persons.remove(id);
    }

}
