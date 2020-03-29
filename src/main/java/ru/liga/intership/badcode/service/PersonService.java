package ru.liga.intership.badcode.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.intership.badcode.domain.dao.PersonDao;
import ru.liga.intership.badcode.domain.model.Person;
import ru.liga.intership.badcode.domain.utils.ConnectorFactory;
import ru.liga.intership.badcode.domain.utils.ConnectorToDB;
import ru.liga.intership.badcode.domain.utils.ConnectorToDBPerson;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PersonService {
    private final PersonDao personDao;
    private Predicate<Person> predicatePersonAdultAndMale;
    public static final Logger logger = LoggerFactory.getLogger(PersonService.class);

    public PersonService() {
        logger.info("Enter to {} {}", this.getClass(), "PersonService()");
        ConnectorToDB connectorToDB = new ConnectorToDBPerson("person", ConnectorFactory.getInstance().getConnection());
        personDao = new PersonDao(connectorToDB);
    }

    public void setPredicatePersonAdultAndMale(Predicate<Person> predicatePersonAdultAndMale) {
        logger.debug("Enter to {} {}", this.getClass(), "void setPredicatePersonAdultAndMale(Predicate<Person> predicatePersonAdultAndMale)");
        this.predicatePersonAdultAndMale = predicatePersonAdultAndMale;
    }

    public Double getAverageBMIFromPredicate() {
        logger.debug("Enter to {} {}", this.getClass(), "Double getAverageBMIFromPredicate()");
        List<Person> personAdultAndMale = personDao.getAll().stream().filter(predicatePersonAdultAndMale).collect(Collectors.toList());
        return getAverageBMI(personAdultAndMale);
    }

    private Double getAverageBMI(List<Person> personsList) {
        logger.debug("Enter to {} {}", this.getClass(), "Double getAverageBMI(List<Person> personsList)");

        Double bmi = 0d;
        double totalImt = 0.0;
        long countOfPerson = 0;

        for (Person p : personsList) {
            double heightInMeters = p.getHeight() / 100d;
            double imt = p.getWeight() / (Double) (heightInMeters * heightInMeters);
            totalImt += imt;
        }

        countOfPerson = personsList.size();
        bmi = totalImt / countOfPerson;
        System.out.println("Average imt - " + bmi);
        return bmi;
    }

}
