package org.example.project2.util;

import org.example.project2.models.Person;
import org.example.project2.services.PersonService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class PersonEmailValidator implements Validator {

    private final PersonService personService;

    public PersonEmailValidator(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Person.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        List<Person> personList = personService.findAllByEmail(person.getEmail());
        if (!personList.isEmpty() && !personList.get(0).equals(person)) {
            errors.rejectValue("email", "", "This email is already in use");
        }
    }
}
