package ru.liga.intership.badcode;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.liga.intership.badcode.domain.Person;
import ru.liga.intership.badcode.domain.PersonDao;
import ru.liga.intership.badcode.service.PersonService;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        personService.getAdultMaleUsersAverageBMI();//need for init list
        anotherData = generateData(5, 10, 150, 210, 18, 80, 40, 100, "male");
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

    public static List<Person> generateData(int minN, int maxN, int minH, int maxH, int minA, int maxA, int minW, int maxW, String m) {
        List<Person> personList = new ArrayList<>();
        int countElement = random(minN, maxN);
        while (countElement > 0) {
            countElement--;
            personList.add(Person.create()
                    .setSex(m)
                    .setAge((long) random(minA, maxA))
                    .setWeight((long) random(minW, maxW))
                    .setHeight((long) (random(minH, maxH)))
                    .build());
        }
        return personList;
    }

    public static int random(int min, int max) {
        return new Random().nextInt((max - min) + 1) + min;
    }


    @Test
    public void testRightTypeField() {
        assertThat(personService.getConnection()).isNotNull();
        assertThat(personService.getPersonDao()).isNotNull();
        if (personService.getPersonDao() != null && personService.getConnection() != null) {
            assertThat(personService.getConnection()).isInstanceOf(Connection.class);
            assertThat(personService.getPersonDao()).isInstanceOf(PersonDao.class);
        }
    }

    @Test
    public void testListPersonNotNull() {
        assertThat(personService.getPersonDao().getPersons()).isNotNull();
    }

    @Test
    public void testPersonsIsAdultMale() {
        assertThat(personService.getPersonDao().getPersons().stream()
                .allMatch(person -> person.getAge() > 18 && person.getSex().equals("male"))).isTrue();
    }

    @Test
    public void testPersonsIsHaveHeightAndWeight() {
        assertThat(personService.getPersonDao().getPersons().stream()
                .allMatch(person -> (person.getHeight() != null && person.getHeight() >= 0) && (person.getWeight() != null && person.getWeight() >= 0)))
                .isTrue();
    }

    @Test
    public void testGetAdultMaleUsersAverageBMI() {
        getAverageBMI(anotherData);
        assertThat(personService.getAverageBMI("")).isEqualTo(adultMaleUsersAverageBMI);
    }

    @Test
    public void testGetWrongDataAverageBMI() {
        getAverageBMI(anotherData);
        assertThat(personService.getAverageBMI("")).isNotEqualTo(adultMaleUsersAverageBMI);
    }


}
