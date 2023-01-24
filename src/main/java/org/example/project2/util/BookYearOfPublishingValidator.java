package org.example.project2.util;

import org.example.project2.models.Book;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

@Component
public class BookYearOfPublishingValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Book.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;
        if (book.getYearOfPublishing() > LocalDate.now().getYear()) {
            errors.rejectValue("yearOfPublishing", "", "Book should be not from future...");
        } else if (book.getYearOfPublishing() < 1800)
            errors.rejectValue("yearOfPublishing","","Book should be publishing after 1800 year");
    }
}
