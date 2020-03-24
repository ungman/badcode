package ru.liga.intership.badcode.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonDaoDefault extends PersonDao {

    public PersonDaoDefault(ConnectionToDB connectionToDB) {
        super(connectionToDB);
    }

    @Override
    public void getPersonFromDBToList(ResultSet resultSet) {
        try {
            while (resultSet.next()) {
                Person p = Person.create()
                        .setId(resultSet.getLong("id"))
                        .setSex(resultSet.getString("sex"))
                        .setName(resultSet.getString("name"))
                        .setAge(resultSet.getLong("age"))
                        .setWeight(resultSet.getLong("weight"))
                        .setHeight(resultSet.getLong("height"))
                        .build();
                personList.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String deletePerson(Person person) {
        return " WHERE " + person.forQuery();
    }

    @Override
    public String updatePerson(Person oldPerson, Person person) {
        return " SET " + person.forQuery() + " WHERE " + oldPerson.forQuery();
    }


}
