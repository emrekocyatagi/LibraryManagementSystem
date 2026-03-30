package dev.emre.librarymanagementsystem.logic;


import dev.emre.librarymanagementsystem.models.Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonService {

    private final Map<String, Person> persons = new HashMap<>();

    /**
     * Adds a new person to the collection.
     *
     * @param person the person to be added; must not be null
     * @throws IllegalArgumentException if the person is null or if a person with the same ID already exists
     */
    public void addPerson(Person person){
        if(person == null){
            throw new IllegalArgumentException("Person cannot be null");
        }
        if(persons.containsKey(person.getId())){
            throw new IllegalArgumentException("Person already exists");
        }
        persons.put(person.getId(), person);
    }

    /**
     * Updates the information of an existing person in the collection.
     *
     * @param id the unique identifier of the person to be updated; must not be null or blank
     * @param updatedPerson the updated Person object with the new details; must not be null and its ID must match the provided id
     * @throws IllegalArgumentException if the updatedPerson is null, the id is null or blank,
     *                                  the id does not match the ID of the updatedPerson,
     *                                  or the person with the given ID does not exist in the collection
     */
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

    /**
     * Finds and retrieves a person based on their unique identifier.
     *
     * @param id the unique identifier of the person to find; must not be null or blank
     * @return the Person object associated with the given ID
     * @throws IllegalArgumentException if the ID is null, blank, or if no person exists with the given ID
     */
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

    /**
     * Retrieves a list of all persons stored in the collection.
     *
     * @return a list of all persons, where each person is represented as a {@code Person} object.
     *         If no persons are stored, an empty list is returned.
     */
    public List<Person> getAllPersonsList(){
        return new ArrayList<>(persons.values()) ;
    }

    /**
     * Deletes a person from the collection based on their unique identifier.
     *
     * @param id the unique identifier of the person to delete; must not be null
     * @throws IllegalArgumentException if the provided id is null or if no person exists with the given id
     */
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
