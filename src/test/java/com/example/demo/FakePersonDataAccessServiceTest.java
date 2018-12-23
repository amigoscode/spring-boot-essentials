package com.example.demo;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class FakePersonDataAccessServiceTest {

  private FakePersonDataAccessService underTest;

  @Before
  public void setUp() {
    underTest = new FakePersonDataAccessService();
  }

  @Test
  public void canPerformCrud() {
    // Given person called James Bond aged 33
    UUID idOne = UUID.randomUUID();
    Person personOne = new Person(idOne, "James Bond", 33);

    // ...And Anna Smith aged 40
    UUID idTwo = UUID.randomUUID();
    Person personTwo = new Person(idTwo, "Anna Smith", 40);

    // When James and Anna added to db
    underTest.addPerson(idOne, personOne);
    underTest.addPerson(idTwo, personTwo);

    // Then can retrieve James by id
    assertThat(underTest.getPerson(idOne))
        .isPresent()
        .hasValueSatisfying(personFromDb -> assertThat(personFromDb).isEqualToComparingFieldByField(personOne));

    // ...And also Anna by id
    assertThat(underTest.getPerson(idTwo))
        .isPresent()
        .hasValueSatisfying(personFromDb -> assertThat(personFromDb).isEqualToComparingFieldByField(personTwo));

    // When get all people
    List<Person> people = underTest.getPeople();

    // ...List should have size 2 and should have both James and Anna
    assertThat(people)
        .hasSize(2)
        .usingFieldByFieldElementComparator()
        .containsExactlyInAnyOrder(personOne, personTwo);

    // ... An update request (James Bond name to Jake Black)
    Person personUpdate = new Person(idOne, "Jake Black", 33);

    // When Update
    assertThat(underTest.updatePerson(idOne, personUpdate)).isEqualTo(1);

    // Then when get person with idOne then should have name as James Bond > Jake Black
    assertThat(underTest.getPerson(idOne))
        .isPresent()
        .hasValueSatisfying(personFromDb -> assertThat(personFromDb).isEqualToComparingFieldByField(personUpdate));

    // When Delete Jake Black
    assertThat(underTest.deletePerson(idOne)).isEqualTo(1);

    // When get personOne should be empty
    assertThat(underTest.getPerson(idOne)).isEmpty();

    // Finally DB should only contain only Anna Smith
    assertThat(underTest.getPeople())
        .hasSize(1)
        .usingFieldByFieldElementComparator()
        .containsExactlyInAnyOrder(personTwo);
  }

  @Test
  public void willReturn0IfNoPersonFoundToDelete() {
    // Given
    UUID id = UUID.randomUUID();

    // When
    int deleteResult = underTest.deletePerson(id);

    // Then
    assertThat(deleteResult).isEqualTo(0);
  }

  @Test
  public void willReturn0IfNoPersonFoundToUpdate() {
    // Given
    UUID id = UUID.randomUUID();
    Person person = new Person(id, "James Not In Db", 33);

    // When
    int deleteResult = underTest.updatePerson(id, person);

    // Then
    assertThat(deleteResult).isEqualTo(0);
  }
}