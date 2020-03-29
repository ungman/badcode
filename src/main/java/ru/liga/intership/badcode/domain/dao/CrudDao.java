package ru.liga.intership.badcode.domain.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.intership.badcode.domain.model.Model;
import ru.liga.intership.badcode.domain.utils.ConnectorToDB;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class CrudDao<T extends Model> {
    public static final Logger logger = LoggerFactory.getLogger(CrudDao.class);
    private static HashMap<Class<?>, Function<String, ?>> parser = new HashMap<>();

    static {
        parser.put(boolean.class, Boolean::parseBoolean); // Support boolean literals too
        parser.put(int.class, Integer::parseInt);
        parser.put(long.class, Long::parseLong);
        parser.put(Boolean.class, Boolean::valueOf);
        parser.put(Integer.class, Integer::valueOf);
        parser.put(Long.class, Long::valueOf);
        parser.put(Double.class, Double::valueOf);
        parser.put(Float.class, Float::valueOf);
        parser.put(String.class, String::valueOf);  // Handle String without special test
        parser.put(BigDecimal.class, BigDecimal::new);
        parser.put(BigInteger.class, BigInteger::new);
        parser.put(LocalDate.class, LocalDate::parse); // Java 8 time API
    }

    private final ConnectorToDB connectorToDB;
    private final Class<T> persistentClass;
    private Map<String, Class<?>> mapFieldsNameAndType;

    public CrudDao(ConnectorToDB connectorToDB) {
        logger.info("Enter to {} {}", this.getClass(), "CrudDao(ConnectorToDB connectorToDB)");
        this.connectorToDB = connectorToDB;
        this.persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        mapFieldsNameAndType = new HashMap<>();
        getFieldClass();
    }

    private static Object parse(String argString, Class param) {
        logger.debug("Enter to {} {}", "CrudDao", "Object parse(String argString, Class param)");
        Function<String, ?> func = parser.get(param);
        if (func != null)
            return func.apply(argString);
        if (param.isEnum())
            return Enum.valueOf(param, argString);
        logger.trace("Cannot parse string to " + param.getName());
        throw new UnsupportedOperationException("Cannot parse string to " + param.getName());
    }

    public T findById(int id) {
        logger.debug("Enter to {} {}", this.getClass(), " T findById(int id)");
        Object object = connectorToDB.getByQuery("WHERE id=" + id, null);
        return castToTFromResultSet(object);
    }

    public void save(T object) {
        logger.debug("Enter to {} {}", this.getClass(), "T save(T object)");
        if (object != null){
            connectorToDB.saveByQuery(((Model) object).objectToSaveStatement(), null);
        }else {
            logger.trace("Cannot save empty object");
            throw new RuntimeException("Cannot save empty object");
        }
    }

    public void update(T object, T objectNew) {
        logger.debug("Enter to {} {}", this.getClass(), "T  update(T object, T objectNew)");
        if (object != null && objectNew!=null)
            connectorToDB.updateByQuery(((Model) object).objectToUpdateStatement(objectNew), null);
        else {
            logger.trace("Cannot update empty object");
            throw new RuntimeException("Cannot update empty object");
        }

    }

    public void delete(T object) {
        logger.debug("Enter to {} {}", this.getClass(), "T  delete(T object)");
        if (object != null)
            connectorToDB.deleteByQuery(((Model) object).objectToWhereStatement(), null);
        else {
            logger.trace("Cannot delete empty object");
            throw new RuntimeException("Cannot delete empty object");
        }
    }

    public List<T> getAll() {
        logger.debug("Enter to {} {}", this.getClass(), "List<T> getAll()");
        ResultSet resultSet = null;
        Object object = connectorToDB.getByQuery("", null);
        List<T> listT = new ArrayList<>();
        try {
            if (object instanceof ResultSet)
                resultSet = ((ResultSet) object);
            else
                return null;
            while (resultSet.next()) {
                listT.add(castToTFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            logger.trace(e.getStackTrace().toString());
            throw new RuntimeException("Error on connect "+e.getMessage());
        }
        return listT;
    }

    private T castToTFromResultSet(Object object) {
        logger.debug("Enter to {} {}", this.getClass(), "T castToTFromResultSet(Object object)");
        T dto = null;
        ResultSet resultSet = null;
        try {
            if (object instanceof ResultSet){
                resultSet = ((ResultSet) object);
            }
            else{
                return null;
            }
            dto = persistentClass.getConstructor().newInstance();
            List<Method> methods = Arrays.asList(persistentClass.getDeclaredMethods()).stream()
                    .filter(method -> method.getName().toLowerCase().contains("set") || method.getName().toLowerCase().contains("get"))
                    .collect(Collectors.toList());
            for (Map.Entry<String, Class<?>> entry : mapFieldsNameAndType.entrySet()) {
                String nameField = entry.getKey();
                if (resultSet.getObject(nameField) != null) {
                    Optional<Method> method = methods.stream()
                            .filter(method1 -> method1.getName().equalsIgnoreCase("set" + nameField)).
                                    findFirst();
                    if (method.isPresent()) {
                        Object value = parse(resultSet.getObject(nameField).toString(), entry.getValue());
                        method.get().invoke(dto, entry.getValue().cast(value));
                    }
                }
            }
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException | SQLException ex) {
            logger.trace(ex.getStackTrace().toString());
            throw new RuntimeException("Error  "+ex.getMessage());
        }
        return dto;
    }

    private void getFieldClass() {
        logger.debug("Enter to {} {}", this.getClass(), "void getFieldClass()");
        Field[] fields = persistentClass.getDeclaredFields();
        Method[] method = persistentClass.getDeclaredMethods();
        for (Field field : fields) {
            String fieldName = field.getName();
            boolean isExistingGetMethod = Stream.of(method).anyMatch(method1 -> method1.getName().equalsIgnoreCase("get" + fieldName));
            boolean isExistingSetMethod = Stream.of(method).anyMatch(method1 -> method1.getName().equalsIgnoreCase("set" + fieldName));
            if (isExistingGetMethod && isExistingSetMethod) {
                mapFieldsNameAndType.put(fieldName, field.getType());
            }
        }
    }
}
