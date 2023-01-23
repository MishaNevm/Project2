package org.example.project2.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @Size(min = 1, max = 30, message = "Name should be in range from 1 to 30")
    @NotEmpty(message = "Name should not be empty")
    private String name;

    @Column(name = "surname")
    @Size(min = 1, max = 50, message = "Surname should be in range from 1 to 50")
    @NotEmpty(message = "Surname should not be empty")
    private String surname;

    @Column(name = "patronymic")
    @Size(min = 1, max = 30, message = "Patronymic should be in range from 1 to 30")
    @NotEmpty(message = "Patronymic should not be empty")
    private String patronymic;

    @Column(name = "email")
    @Email(message = "Email should be valid")
    private String email;

    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "Date of birth should be in the past")
    private Date dateOfBirth;

    @Column(name = "age")
    private int age;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", email='" + email + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", age=" + age +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id && age == person.age && Objects.equals(name, person.name)
                && Objects.equals(surname, person.surname) && Objects.equals(patronymic, person.patronymic)
                && Objects.equals(email, person.email) && Objects.equals(dateOfBirth, person.dateOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, patronymic, email, dateOfBirth, age);
    }
}
