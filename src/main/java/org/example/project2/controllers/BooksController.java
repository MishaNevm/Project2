package org.example.project2.controllers;

import org.example.project2.dao.BookDAO;
import org.example.project2.models.Book;
import org.example.project2.models.Person;
import org.example.project2.services.BookService;
import org.example.project2.services.PersonService;
import org.example.project2.util.BookYearOfPublishingValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookService bookService;
    private final PersonService personService;
    private final BookDAO bookDAO;
    private final BookYearOfPublishingValidator bookYearOfPublishingValidator;

    @Autowired
    public BooksController(BookService bookService, PersonService personService, BookDAO bookDAO,
                           BookYearOfPublishingValidator publishingValidator) {
        this.bookService = bookService;
        this.personService = personService;
        this.bookDAO = bookDAO;
        this.bookYearOfPublishingValidator = publishingValidator;
    }

    @GetMapping
    public String showAllBooks(
            Model model, @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "bookForPage", required = false) Integer bookForPage,
            @RequestParam(value = "sortByYearOfPublishing", required = false) Boolean sortByYearOfPublishing) {
        model.addAttribute("books", bookService.findAll(page, bookForPage, sortByYearOfPublishing));
        return "book/showAllBooks";
    }

    @GetMapping("/free")
    public String showAllFreeBooks(Model model) {
        model.addAttribute("books", bookDAO.findAllFreeBooks());
        return "book/showAllFreeBooks";
    }

    @GetMapping("/search")
    public String searchBookByNameLike(Model model, @RequestParam("nameLike") String nameLike) {
        model.addAttribute("books", bookService.findByNameLike(nameLike));
        return "book/searchBookByNameLike";
    }

    @GetMapping("/{id}")
    public String showOneBook(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        Book book = bookService.findOne(id);
        model.addAttribute("book", book);
        Person bookOwner = book.getOwner();
        if (bookOwner != null)
            model.addAttribute("owner", bookOwner);
        else
            model.addAttribute("persons", personService.findAll());
        return "book/showOneBook";
    }

    @PatchMapping("/{id}/appoint")
    public String appoint(@PathVariable("id") int id, @ModelAttribute("person") Person selectedPerson) {
        bookService.appoint(id, selectedPerson);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/unAppoint")
    public String unAppoint(@PathVariable("id") int id) {
        bookService.unAppoint(id);
        return "redirect:/books/" + id;
    }

    @GetMapping("/new")
    public String createNewBook(@ModelAttribute("book") Book book) {
        return "book/createNewBook";
    }

    @PostMapping
    public String createNewBook(@ModelAttribute("book") Book book, BindingResult bindingResult) {
        bookYearOfPublishingValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()) return "book/createNewBook";
        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String updateBook(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookService.findOne(id));
        return "book/updateBook";
    }

    @PatchMapping("/{id}")
    public String updateBook(@PathVariable("id") int id,
                             @ModelAttribute("book") Book book, BindingResult bindingResult) {
        bookYearOfPublishingValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()) return "book/updateBook";
        bookService.update(id, book);
        return "redirect:/books/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/books";
    }
}
