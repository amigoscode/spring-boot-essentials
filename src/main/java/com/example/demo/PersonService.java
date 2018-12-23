package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PersonService {

  private final PersonDao personDao;

  @Autowired
  public PersonService(@Qualifier("fakeDao") PersonDao personDao) {
    this.personDao = personDao;
  }

  List<Person> getAllPeople() {
    return personDao.getPeople();
  }

  UUID insertNewPerson(Person person) {
    return personDao.addPerson(person);
  }

  Optional<Person> getPersonById(UUID personId) {
    return personDao.getPerson(personId);
  }

  void deletePerson(UUID personId) {
    personDao.deletePerson(personId);
  }

  void updatePerson(UUID personId, Person person) {
    personDao.updatePerson(personId, person);
  }
}
