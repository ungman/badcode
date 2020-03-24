package ru.liga.intership.badcode;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.liga.intership.badcode.domain.ConnectionToDB;
import ru.liga.intership.badcode.domain.Person;
import ru.liga.intership.badcode.domain.PersonDao;
import ru.liga.intership.badcode.service.PersonService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonServiceTest {

    public static PersonService personService;
    public static double adultMaleUsersAverageBMI;
    public static List<Person> anotherData;

    @BeforeClass
    public static void initClass() {
        personService = new PersonService();
    }

    public static Double getAverageBMI(List<Person> personList) {
        double totalImt = 0.0;
        long countOfPerson = 0;
        for (Person p : personList) {
            double heightInMeters = p.getHeight() / 100d;
            double imt = p.getWeight() / (Double) (heightInMeters * heightInMeters);
            totalImt += imt;
        }
        countOfPerson = personList.size();
        adultMaleUsersAverageBMI = totalImt / countOfPerson;
        return adultMaleUsersAverageBMI;
    }

    @Test
    public void testConstructor() {
        assertThat(personService).isNotNull();
        assertThat(personService).isInstanceOf(PersonService.class);
    }

    @Test
    public void testConstructorCheckArgument() {
        assertThat(personService.getPersonDao()).isNotNull();
        assertThat(personService.getConnection()).isNotNull();
        assertThat(personService.getPersonDao()).isInstanceOf(PersonDao.class);
        assertThat(personService.getConnection()).isInstanceOf(ConnectionToDB.class);
    }
    @Test
    public void testGetRightAverage(){
        double averageBMI1=personService.getAdultMaleUsersAverageBMI();
        double averageBMI2=getAverageBMI(personService.getPersonDao().getPersons());
        assertThat(averageBMI1).isEqualTo(averageBMI2);
    }
    @Test
    public void testGetWrongAverage(){
        double averageBMI1=personService.getAdultMaleUsersAverageBMI();
        List<Person> personList=personService.getPersonDao().getPersons();
        personList.add(Person.create()
                .setAge(20L)
                .setHeight(170L)
                .setWeight(75L)
                .build());
        double averageBMI2=getAverageBMI(personList);
        assertThat(averageBMI1).isNotEqualTo(averageBMI2);
    }

}
