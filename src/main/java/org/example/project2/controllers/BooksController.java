package org.example.project2.controllers;

import org.example.project2.models.Book;
import org.example.project2.services.BookService;
import org.example.project2.util.BookYearOfPublishingValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookService bookService;

    private final BookYearOfPublishingValidator bookYearOfPublishingValidator;

    public BooksController(BookService bookService, BookYearOfPublishingValidator publishingValidator) {
        this.bookService = bookService;
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

    @GetMapping("/search")
    public String searchBookByNameLike(Model model, @RequestParam("nameLike") String nameLike) {
        model.addAttribute("books", bookService.findByNameLike(nameLike));
        return "book/searchBookByNameLike";
    }

    @GetMapping("/{id}")
    public String showOneBook (@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookService.findOne(id));
        return "book/showOneBook";
    }

    @GetMapping("/new")
    public String createNewBook (@ModelAttribute("book")Book book) {
        return "book/createNewBook";
    }
    @PostMapping
    public String createNewBook (@ModelAttribute("book") Book book, BindingResult bindingResult) {
        bookYearOfPublishingValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()) return "book/createNewBook";
        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String updateBook (@PathVariable("id") int id ,Model model) {
        model.addAttribute("book", bookService.findOne(id));
        return "book/updateBook";
    }
    @PatchMapping("/{id}")
    public String updateBook (@PathVariable("id") int id,
                              @ModelAttribute("book") Book book, BindingResult bindingResult) {
        bookYearOfPublishingValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()) return "book/updateBook";
        bookService.update(id, book);
        return "redirect:/books/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete (@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/books";
    }
}
