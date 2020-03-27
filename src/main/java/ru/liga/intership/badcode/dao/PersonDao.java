package ru.liga.intership.badcode.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.liga.intership.badcode.models.Person;
import ru.liga.intership.badcode.utils.HibernateSessionFactoryUtil;

import java.util.List;

public class PersonDao {

    public Person findById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Person.class, id);
    }

    public void save(Person person) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(person);
        tx1.commit();
        session.close();
    }

    public void update(Person person) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(person);
        tx1.commit();
        session.close();
    }

    public void delete(Person person) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(person);
        tx1.commit();
        session.close();
    }

    public List<Person> findAll() {
        List<Person> personList = (List<Person>) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From Person").list();
        return personList;
    }
    public List<Person> executeQuery(String query){
        return (List<Person>) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery(query).list();
    }
}
