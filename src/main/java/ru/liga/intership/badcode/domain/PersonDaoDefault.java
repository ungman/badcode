package ru.liga.intership.badcode.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.intership.badcode.BadcodeApplication;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonDaoDefault extends PersonDao {
    public static final Logger logger = LoggerFactory.getLogger(PersonDaoDefault.class);

    public PersonDaoDefault(ConnectionToDB connectionToDB) {
        super(connectionToDB);
        logger.debug("Enter to {} , {} ","PersonDaoDefault","PersonDaoDefault(ConnectionToDB connectionToDB)");
    }

    @Override
    public void getPersonFromDBToList(ResultSet resultSet) {
        logger.debug("Enter to {} {}","PersonDaoDefault","getPersonFromDBToList");
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


}
