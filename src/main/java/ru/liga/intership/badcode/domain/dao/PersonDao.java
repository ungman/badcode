package ru.liga.intership.badcode.domain.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.intership.badcode.domain.model.Person;
import ru.liga.intership.badcode.domain.utils.ConnectorToDB;

import java.util.List;

public class PersonDao extends CrudDao<Person> {
    public static final Logger logger = LoggerFactory.getLogger(PersonDao.class);

    public PersonDao(ConnectorToDB connectorToDB) {
        super(connectorToDB);
        logger.info("Enter to {} {}", this.getClass(), "PersonDao(ConnectorToDB connectorToDB)");
    }

    @Override
    public Person findById(int id) {
        logger.debug("Enter to {} {}", this.getClass(), "Person findById(int id) ");
        return super.findById(id);
    }

    @Override
    public void update(Person object, Person objectNew) {
        logger.debug("Enter to {} {}", this.getClass(), "void update(Person object, Person objectNew)");
        super.update(object, objectNew);
    }

    @Override
    public void delete(Person object) {
        logger.debug("Enter to {} {}", this.getClass(), "void delete(Person object)");
        super.delete(object);
    }

    @Override
    public List<Person> getAll() {
        logger.debug("Enter to {} {}", this.getClass(), "List<Person> getAll()");
        return super.getAll();
    }

}
