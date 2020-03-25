package ru.liga.intership.badcode;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.liga.intership.badcode.domain.ConnectionToDB;
import ru.liga.intership.badcode.domain.Person;
import ru.liga.intership.badcode.service.PersonService;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConnectionToDBTest {

    public static PersonService personService;
    static ConnectionToDB connectionToDB;
    static String selectQuery;
    static String updateQuery;
    static String deleteQuery;
    static Person person;

    @BeforeClass
    public static void initClass() {
        personService = new PersonService();
        connectionToDB = personService.getConnection();
    }

    @Before
    public void before(){
        selectQuery = "SELECT * FROM person WHERE age>18 and sex='male'";
        ResultSet resultSet = connectionToDB.selectQuery(selectQuery);
        person = getPerson(resultSet);
        updateQuery = "UPDATE person SET age =" + (person.getAge() + 1) + "WHERE id=" + person.getId();
        deleteQuery = "DELETE FROM person WHERE id=" + person.getId();
    }

    public static Person getPerson(ResultSet resultSet) {
        Person person = null;
        try {
            if (resultSet.next()) {
                person = Person.create()
                        .setId(resultSet.getLong("id"))
                        .setSex(resultSet.getString("sex"))
                        .setName(resultSet.getString("name"))
                        .setAge(resultSet.getLong("age"))
                        .setWeight(resultSet.getLong("weight"))
                        .setHeight(resultSet.getLong("height"))
                        .build();
            }
        } catch (SQLException e) {
//            e.printStackTrace();
        }
        return person;
    }

    @Test
    public void checkConstructor() {
        assertThat(connectionToDB).isNotNull();
    }

    @Test
    public void checkConnectToBD() {
        assertThat(connectionToDB.getConnection()).isNotNull();
        assertThat(connectionToDB.getConnection()).isInstanceOf(Connection.class);
    }

    @Test
    public void checkSelectQuery() {

        ResultSet resultSet = connectionToDB.selectQuery(selectQuery);
        assertThat(resultSet).isNotNull();
        Person person = getPerson(resultSet);
        assertThat(person).isNotNull();
        assertThat(person.getAge() > 18 && person.getSex().equalsIgnoreCase("male")).isTrue();

        Person person1 = getPerson(connectionToDB.selectQuery(selectQuery));
        Person person2 = getPerson(connectionToDB.selectQuery(selectQuery));
        assertThat(person1.getId()).isEqualTo(person2.getId());
    }

    @Test
    public void checkUpdateQuery() {
        long oldAge = person.getAge();
        assertThat(connectionToDB.updateQuery(updateQuery)).isGreaterThan(-1);
        String checkQuery = "SELECT * FROM person WHERE id=" + person.getId();
        Person person1 = getPerson(connectionToDB.selectQuery(checkQuery));
        assertThat(person.getAge()).isNotEqualTo(person1.getAge());
    }

    public void checkDeleteQuery() {
        long personId = person.getId();
        assertThat(connectionToDB.deleteQuery(deleteQuery)).isGreaterThan(-1);
        String checkQuery = "SELECT FROM person WHERE id=" + personId;
        Person deletedPerson = getPerson(connectionToDB.selectQuery(checkQuery));
        assertThat(deleteQuery).isNull();
    }
}