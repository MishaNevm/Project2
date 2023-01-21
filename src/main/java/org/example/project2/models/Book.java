package org.example.project2.models;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

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
    private String name;

    @Column(name = "author_name")
    private String authorName;

    @Column(name = "author_surname")
    private String authorSurname;

    @Column(name = "year_of_publishing")
    private int yearOfPublishing;

    @Column(name = "date_of_taken_away")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfTakenAway;

    @Transient
    private boolean overdue;

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
