package ru.liga.intership.badcode.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.liga.intership.badcode.service.PersonService;

@Configuration
public class BadcodeApplicationConfigure {

    @Bean
    public PersonService personService(){
        return  new PersonService();
    }

}
