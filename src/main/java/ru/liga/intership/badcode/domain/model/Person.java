package ru.liga.intership.badcode.domain.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.intership.badcode.domain.dao.PersonDao;

public class Person implements Model{
    public static final Logger logger = LoggerFactory.getLogger(PersonDao.class);

    private Long id;
    private String sex;
    private String name;
    private Long age;
    private Long weight;
    private Long height;

    public Person() {logger.info("Enter to {} {}",this.getClass()," Person()");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    @Override
    public String objectToWhereStatement() {
        logger.debug("Enter to {} {}","String objectToWhereStatement()");
        String query=
                " WHERE id="+id+
                ", sex="+sex+
                ", name="+name+
                ", age="+age+
                ", weight="+weight+
                ", height="+height;
        return query;
    }

    @Override
    public String objectToSaveStatement() {
        logger.debug("Enter to {} {}","String objectToSaveStatement()");

        String query=null;
        if(id!=null){
            query="(id,sex,name,age,weight,height)"+
                    "VALUES ("+id+","+sex+","+name+","+age+","+weight+","+height+")";
        }else{
            query="(sex,name,age,weight,height)"+
                    "VALUES ("+sex+","+name+","+age+","+weight+","+height+")";
        }
        return  query;
    }

    @Override
    public String objectToUpdateStatement(Object object) {
        logger.debug("Enter to {} {}","String objectToUpdateStatement(Object object)");
        String newValue=((Model) object).objectToWhereStatement().replaceAll("WHERE","");
        String query=" SET "+newValue+objectToWhereStatement();
        return  query;
    }
}
