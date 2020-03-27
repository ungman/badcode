package ru.liga.intership.badcode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.liga.intership.badcode.configuration.BadcodeApplicationConfigure;
import ru.liga.intership.badcode.service.PersonService;

@SpringBootApplication
public class BadcodeApplication {
    public static final Logger logger = LoggerFactory.getLogger(BadcodeApplication.class);
    public static void main(String[] args) {
        logger.debug("Enter to {} {}","BadcodeApplication","main(String[] args");
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(BadcodeApplicationConfigure.class);
        ctx.refresh();
        SpringApplication.run(BadcodeApplication.class, args);
        PersonService personService = ctx.getBean(PersonService.class);
        personService.getAdultMaleUsersAverageBMI();
    }
}
