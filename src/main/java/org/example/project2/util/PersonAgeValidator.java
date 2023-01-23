package org.example.project2.util;

import org.example.project2.models.Person;
import org.example.project2.services.PersonService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonAgeValidator implements Validator {

    private final PersonService personService;

    public PersonAgeValidator(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Person.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        int age = personService.calculateAge(person.getDateOfBirth());
        if (age < 4) {
            errors.rejectValue("dateOfBirth", "", "You should be older than 4");
        } else if (age > 119) {
            errors.rejectValue("dateOfBirth", "", "You should be younger then 120");
        }
    }
}
