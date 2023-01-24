package org.example.project2.services;

import org.example.project2.models.Person;
import org.example.project2.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> findAll(Integer page, Integer personForPage, Boolean sortByYear) {
        if (personForPage != null && sortByYear != null && sortByYear) {
            return findAllWithSortByAgeAndPagination(page, personForPage);
        } else if (sortByYear != null && sortByYear) {
            return findAllWithSortByAge();
        } else if (personForPage != null) {
            return findAllWithPagination(page, personForPage);
        } else return findAll();
    }

    public List<Person> findAllByEmail(String email) {
        return personRepository.findByEmail(email);
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    private List<Person> findAllWithSortByAge() {
        return personRepository.findAll(Sort.by("age"));
    }

    private List<Person> findAllWithPagination(int page, int personForPage) {
        return personRepository.findAll(PageRequest.of(page, personForPage)).getContent();
    }

    private List<Person> findAllWithSortByAgeAndPagination(int page, int personForPage) {
        return personRepository.findAll(PageRequest.of(page, personForPage, Sort.by("age"))).getContent();
    }

    public List<Person> findBySurnameLike(String surnameLike) {
        if (surnameLike != null) {
            return personRepository.findBySurnameLike(surnameLike + "%");
        } else return null;
    }

    public Person findOne(int id) {
        Person person = personRepository.findById(id).orElse(null);
        Objects.requireNonNull(person).getBooks()
                .forEach(a -> a.setOverdue
                        (BookService.calculateStorageDays(a.getDateOfTakenAway()) >= BookService.MAX_STORAGE_DAYS));
        return person;
    }
    @Transactional
    public void unAppointAll(Person person) {
        person.getBooks().forEach(a -> {
            a.setOwner(null);
            a.setDateOfTakenAway(null);
        });
        person.setBooks(null);
    }


    @Transactional
    public void save(Person person) {
        person.setAge(calculateAge(person.getDateOfBirth()));
        personRepository.save(person);
    }

    public int calculateAge(Date dateOfBirth) {
        Date now = new Date();
        return (int) ((now.getTime() - dateOfBirth.getTime())
                / (1000 * 60 * 60 * 24) / 365);
    }

    @Transactional
    public void update(int id, Person person) {
        person.setId(id);
        personRepository.save(person);
    }

    @Transactional
    public void delete(int id) {
        personRepository.deleteById(id);
    }

}
