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

    public static final int MAX_STORAGE_DAYS = 10;

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll(Integer page, Integer bookForPage, Boolean sortByYearOfPublishing) {
        if (sortByYearOfPublishing != null && sortByYearOfPublishing && bookForPage != null) {
            return findAllWithSortByYearOfEditionAndPagination(page, bookForPage);
        } else if (sortByYearOfPublishing != null && sortByYearOfPublishing) {
            return findAllWithSortByYearOfPublishing();
        } else if (bookForPage != null) {
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

    public List<Book> findByNameLike(String nameLike) {
        if (nameLike != null) {
            return bookRepository.findByNameLike(nameLike);
        } else return null;
    }

    public Book findOne(int id) {
        Book book = bookRepository.findById(id).orElse(new Book());
        int storageDays = calculateStorageDays(book.getDateOfTakenAway());
        book.setOverdue(storageDays >= MAX_STORAGE_DAYS);
        book.setStorageDays(storageDays);
        return book;

    }

    protected int calculateStorageDays(Date dateOfTakenAway) {
        Date now = new Date();
        return (int) ((dateOfTakenAway.getTime() - now.getTime())
                / (1000 * 60 * 60 * 24));
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
    public void setOwner(Person owner, int id, Book book) {
        book.setOwner(owner);
        book.setDateOfTakenAway(new Date());
        book.setId(id);
        bookRepository.save(book);
    }

    @Transactional
    public void deleteOwner(int id, Book book) {
        book.setOwner(null);
        book.setId(id);
        bookRepository.save(book);
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

}
