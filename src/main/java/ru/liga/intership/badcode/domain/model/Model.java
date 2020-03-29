package ru.liga.intership.badcode.domain.model;

public interface Model {

    String objectToWhereStatement();
    String objectToSaveStatement();
    String objectToUpdateStatement(Object object);

}
