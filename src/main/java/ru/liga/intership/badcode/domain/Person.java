package ru.liga.intership.badcode.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.intership.badcode.BadcodeApplication;

import java.util.Map;

public class Person {
    public static final Logger logger = LoggerFactory.getLogger(Person.class);
    public static final Logger logger1 = LoggerFactory.getLogger(PersonBuilder.class);

    private final Long id;
    private final String sex;
    private final String name;
    private final Long age;
    private final Long weight;
    private final Long height;

    private Person(Long id, String sex, String name, Long age, Long weight, Long height) {
        logger.debug("Enter to {} , {} ","Person","Person(Long id, String sex, String name, Long age, Long weight, Long height)");

        this.id = id;
        this.sex = sex;
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.height = height;
    }

    public static PersonBuilder create() {
        logger.debug("Enter to {} , {} ","Person","create()");

        return new PersonBuilder();
    }

    public Long getId() {
        return id;
    }

    public String getSex() {
        return sex;
    }

    public String getName() {
        return name;
    }

    public Long getAge() {
        return age;
    }

    public Long getWeight() {
        return weight;
    }

    public Long getHeight() {
        return height;
    }


    public static class PersonBuilder {
        private Long id;
        private String sex;
        private String name;
        private Long age;
        private Long weight;
        private Long height;

        public PersonBuilder setId(Long id) {

            this.id = id;
            return this;
        }

        public PersonBuilder setSex(String sex) {
            this.sex = sex;
            return this;
        }

        public PersonBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public PersonBuilder setAge(Long age) {
            this.age = age;
            return this;
        }

        public PersonBuilder setWeight(Long weight) {
            this.weight = weight;
            return this;
        }

        public PersonBuilder setHeight(Long height) {
            this.height = height;
            return this;
        }

        public Person build() {
            logger.debug("Enter to {} , {} ","PersonBuilder","build()");
            return new Person(id, sex, name, age, weight, height);
        }
    }

}