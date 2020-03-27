package ru.liga.intership.badcode.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.liga.intership.badcode.dao.PersonDao;
import ru.liga.intership.badcode.models.Person;
import sun.dc.path.PathError;


import java.util.ArrayList;
import java.util.List;

public class PersonService {
    public static final Logger logger = LoggerFactory.getLogger(PersonService.class);

    private final PersonDao personDao=new PersonDao();
    public  Person findPersonById(int id){
        return personDao.findById(id);
    }
    public void updatePerson(Person person){
        personDao.update(person);
    }
    public void deletePerson(Person person){
        personDao.delete(person);
    }
    public List<Person> findAllPerson() {
        return personDao.findAll();
    }
    public List<Person> findByQueryPerson(String query) {
        return personDao.executeQuery(query);
    }


    /**
     * Возвращает средний индекс массы тела всех лиц мужского пола старше 18 лет
     *
     * @return
     */
    public Double getAdultMaleUsersAverageBMI() {
        logger.debug("Enter to {} {}","PersonService","getAdultMaleUsersAverageBMI()");
        String query = "FROM Person P WHERE P.sex = 'male' AND P.age > 18";
        return getAverageBMI(query);
    }

    public Double getAverageBMI(String query) {
        logger.debug("Enter to {} {}","PersonService","getAverageBMI(String query) ");
        //session.createQuery("FROM Developer D WHERE D.id = 1")

//        listPerson.addAll(findByQueryPerson(query));
        List<Person> listPerson=findAllPerson();
        System.out.println("listperson size"+listPerson.size());
        double totalImt = 0.0;
        long countOfPerson = 0;

        for (Person p : listPerson) {
            double heightInMeters = p.getHeight() / 100d;
            double imt = p.getWeight() / (Double) (heightInMeters * heightInMeters);
            totalImt += imt;
        }
        Double bmi=0D;
        countOfPerson = listPerson.size();
        bmi = totalImt / countOfPerson;
        System.out.println("Average imt - " + bmi);
        return bmi;
    }
}