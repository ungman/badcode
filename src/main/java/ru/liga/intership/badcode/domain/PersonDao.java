package ru.liga.intership.badcode.domain;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public abstract class PersonDao {

    protected List<Person> personList = new ArrayList<>();
    protected ConnectionToDB connectionToDB;

    public PersonDao(ConnectionToDB connectionToDB) {
        this.connectionToDB = connectionToDB;
    }

    public abstract void getPersonFromDBToList(ResultSet resultSet);

    public List<Person> getPersons() {
        return personList;
    }

    public Person getPerson(int id) {
        return personList.get(id);
    }


}
