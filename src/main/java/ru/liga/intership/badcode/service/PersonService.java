package ru.liga.intership.badcode.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.intership.badcode.BadcodeApplication;
import ru.liga.intership.badcode.domain.ConnectionToDB;
import ru.liga.intership.badcode.domain.Person;
import ru.liga.intership.badcode.domain.PersonDao;
import ru.liga.intership.badcode.domain.PersonDaoDefault;

import java.util.List;

public class PersonService {
    public static final Logger logger = LoggerFactory.getLogger(PersonService.class);

    private final PersonDao personDao;
    private final ConnectionToDB connectionToDB;
    private Double bmi;
    private List<Person> listPerson;

    public PersonService() {
        logger.debug("Enter to {} {}","PersonService","PersonService()");
        connectionToDB = ConnectionToDB.getInstance("jdbc:hsqldb:mem:test", "sa", "");
        personDao = new PersonDaoDefault(connectionToDB);
    }

    public PersonService(String url, String user, String password) {
        logger.debug("Enter to {} {}","PersonService","PersonService(String url, String user, String password)");
        this.connectionToDB = ConnectionToDB.getInstance(url, user,password);
        personDao = new PersonDaoDefault(connectionToDB);
    }

    public PersonDao getPersonDao() {
        logger.debug("Enter to {} {}","PersonService","getPersonDao()");
        return personDao;
    }

    public ConnectionToDB getConnection() {
        logger.debug("Enter to {} {}","PersonService","getConnection()");
        return connectionToDB;
    }

    /**
     * Возвращает средний индекс массы тела всех лиц мужского пола старше 18 лет
     *
     * @return
     */
    public Double getAdultMaleUsersAverageBMI() {
        logger.debug("Enter to {} {}","PersonService","getAdultMaleUsersAverageBMI()");
        String query = "SELECT * FROM person WHERE sex = 'male' AND age > 18";
        return getAverageBMI(query);
    }

    public Double getAverageBMI(String query) {
        logger.debug("Enter to {} {}","PersonService","getAverageBMI(String query)");
        if (!query.isEmpty()) {
            this.getPersonDao().getPersonFromDBToList(connectionToDB.selectQuery(query));
        }

        listPerson = this.getPersonDao().getPersons();
        double totalImt = 0.0;
        long countOfPerson = 0;

        for (Person p : listPerson) {
            double heightInMeters = p.getHeight() / 100d;
            double imt = p.getWeight() / (Double) (heightInMeters * heightInMeters);
            totalImt += imt;
        }

        countOfPerson = listPerson.size();
        bmi = totalImt / countOfPerson;
        System.out.println("Average imt - " + bmi);
        return bmi;
    }
}