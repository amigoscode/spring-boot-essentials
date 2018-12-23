package com.example.demo;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PersonDao {

  UUID addPerson(UUID id, Person person);

  default UUID addPerson(Person person) {
    UUID id = UUID.randomUUID();
    return addPerson(id, person);
  }

  List<Person> getPeople();

  Optional<Person> getPerson(UUID id);

  int deletePerson(UUID id);

  int updatePerson(UUID id, Person person);

}
