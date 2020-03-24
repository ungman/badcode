package ru.liga.intership.badcode.domain;

import java.util.Map;

public class Person implements DataToQuery{
    private final Long id;
    private final String sex;
    private final String name;
    private final Long age;
    private final Long weight;
    private final Long height;

    private Person(Long id, String sex, String name, Long age, Long weight, Long height) {
        this.id = id;
        this.sex = sex;
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.height = height;
    }

    public static PersonBuilder create() {
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

    @Override
    public String toQuery() {
        return "id=" + id + " sex=" + sex + " name=" + name + " age=" + age + " weight=" + weight + " height=" + height;
    }

    public static class PersonBuilder {
        private Long id;
        private String sex;
        private String name;
        private Long age;
        private Long weight;
        private Long height;
        private Map<String, String> varsNameInDB;

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
            return new Person(id, sex, name, age, weight, height);
        }
    }

}