package com.example.demo;

import java.util.UUID;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class Person {

  private final UUID id;
  @NotBlank
  private final String name;

  @NotNull
  @Min(0)
  private final Integer age;

  public Person(UUID id, String name, Integer age) {
    this.id = id;
    this.name = name;
    this.age = age;
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Integer getAge() {
    return age;
  }
}
