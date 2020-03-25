package ru.liga.intership.badcode;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.liga.intership.badcode.domain.ConnectionToDB;

import ru.liga.intership.badcode.domain.PersonDao;
import ru.liga.intership.badcode.domain.PersonDaoDefault;

import java.sql.ResultSet;


import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonDaoDefaultTest {
    static PersonDao personDao;
    static  ConnectionToDB connectionToDB;

    @BeforeClass
    public static void initClass(){
        connectionToDB=ConnectionToDB.getInstance("jdbc:hsqldb:mem:test","sa","");
        personDao=new PersonDaoDefault(connectionToDB);

    }

    @Test
    public void checkNotNullList(){
        ResultSet resultSet=connectionToDB.selectQuery("SELECT * FROM person");
        personDao.getPersonFromDBToList(resultSet);
        assertThat(personDao.getPersons()).isNotNull();
        assertThat(personDao.getPersons()).isNotEmpty();

        assertThat(personDao.getPerson(0)).isNotNull();

    }


}
