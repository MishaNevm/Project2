package org.example.project2.services;

import org.example.project2.models.Book;
import org.example.project2.models.Person;
import org.example.project2.repositories.BookRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll(int page, int bookForPage, boolean sortByYearOfPublishing) {
        if (sortByYearOfPublishing && bookForPage > 1) {
            return findAllWithSortByYearOfEditionAndPagination(page, bookForPage);
        } else if (sortByYearOfPublishing) {
            return findAllWithSortByYearOfPublishing();
        } else if (bookForPage > 1) {
            return findAllWithPagination(page, bookForPage);
        } else return findAll();
    }

    private List<Book> findAll() {
        return bookRepository.findAll();
    }

    private List<Book> findAllWithSortByYearOfPublishing() {
        return bookRepository.findAll(Sort.by("yearOfPublishing"));
    }

    private List<Book> findAllWithPagination(int page, int bookForPage) {
        return bookRepository.findAll(PageRequest.of(page, bookForPage)).getContent();
    }

    private List<Book> findAllWithSortByYearOfEditionAndPagination
            (int page, int bookForPage) {
        return bookRepository.findAll(PageRequest.of(page, bookForPage, Sort.by("yearOfPublishing"))).getContent();
    }

    public Book findOne(int id) {
        Book book = bookRepository.findById(id).orElse(new Book());
        book.setOverdue(calculateOverdue(book.getDateOfTakenAway()));
        return book;
    }

    private boolean calculateOverdue(Date dateOfTakenAway) {
        if (dateOfTakenAway == null) return false;
        Date now = new Date();
        int diffInDays = (int) ((dateOfTakenAway.getTime() - now.getTime())
                / (1000 * 60 * 60 * 24));
        return diffInDays >= 10;
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void update(int id, Book book) {
        book.setId(id);
        bookRepository.save(book);
    }

    @Transactional
    public void updateOwner(Person owner, int id, Book book) {
        book.setOwner(owner);
        book.setDateOfTakenAway(new Date());
        book.setId(id);
        bookRepository.save(book);
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

}
