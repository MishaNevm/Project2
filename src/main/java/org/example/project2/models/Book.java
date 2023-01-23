package org.example.project2.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "Book")
public class Book {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    @Column(name = "name")
    @NotEmpty(message = "Book name should be not empty")
    private String name;

    @Column(name = "author_name")
    @NotEmpty(message = "Author name should be not empty")
    private String authorName;

    @Column(name = "author_surname")
    @NotEmpty(message = "Author surname should be not empty")
    private String authorSurname;

    @Column(name = "year_of_publishing")
    @Min(value = 1700, message = "Book year of publishing should be bigger than 1700")
    private int yearOfPublishing;

    @Column(name = "date_of_taken_away")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfTakenAway;

    @Transient
    private boolean overdue;

    @Transient
    private int storageDays;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorSurname() {
        return authorSurname;
    }

    public void setAuthorSurname(String authorSurname) {
        this.authorSurname = authorSurname;
    }

    public int getYearOfPublishing() {
        return yearOfPublishing;
    }

    public void setYearOfPublishing(int yearOfEdition) {
        this.yearOfPublishing = yearOfEdition;
    }

    public Date getDateOfTakenAway() {
        return dateOfTakenAway;
    }

    public void setDateOfTakenAway(Date dateOfTakenAway) {
        this.dateOfTakenAway = dateOfTakenAway;
    }

    public boolean isOverdue() {
        return overdue;
    }

    public void setOverdue(boolean overdue) {
        this.overdue = overdue;
    }

    public int getStorageDays() {
        return storageDays;
    }

    public void setStorageDays(int storageDays) {
        this.storageDays = storageDays;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", authorName='" + authorName + '\'' +
                ", authorSurname='" + authorSurname + '\'' +
                ", yearOfEdition=" + yearOfPublishing +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id && yearOfPublishing == book.yearOfPublishing && Objects.equals(name, book.name)
                && Objects.equals(authorName, book.authorName) && Objects.equals(authorSurname, book.authorSurname)
                && Objects.equals(dateOfTakenAway, book.dateOfTakenAway);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, authorName, authorSurname, yearOfPublishing, dateOfTakenAway);
    }
}
