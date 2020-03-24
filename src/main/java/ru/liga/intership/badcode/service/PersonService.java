package ru.liga.intership.badcode.service;


import ru.liga.intership.badcode.domain.ConnectionToDB;
import ru.liga.intership.badcode.domain.Person;
import ru.liga.intership.badcode.domain.PersonDao;
import ru.liga.intership.badcode.domain.PersonDaoDefault;

import java.util.List;

public class PersonService {

    private final PersonDao personDao;
    private final ConnectionToDB connectionToDB;
    private Double bmi;
    private List<Person> listPerson;

    public PersonService() {
        connectionToDB = ConnectionToDB.getInstance("jdbc:hsqldb:mem:test", "sa", "");
        personDao = new PersonDaoDefault(connectionToDB);
    }

    public PersonService(String url, String user, String password) {
        this.connectionToDB = ConnectionToDB.getInstance(url, user,password);
        personDao = new PersonDaoDefault(connectionToDB);
    }

    public PersonDao getPersonDao() {
        return personDao;
    }

    public ConnectionToDB getConnection() {
        return connectionToDB;
    }

    /**
     * Возвращает средний индекс массы тела всех лиц мужского пола старше 18 лет
     *
     * @return
     */
    public Double getAdultMaleUsersAverageBMI() {
        String query = "SELECT * FROM person WHERE sex = 'male' AND age > 18";
        return getAverageBMI(query);
    }

    public Double getAverageBMI(String query) {

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