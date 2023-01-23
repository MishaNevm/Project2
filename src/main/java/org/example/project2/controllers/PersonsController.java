package org.example.project2.controllers;

import org.example.project2.dao.PersonDAO;
import org.example.project2.models.Person;
import org.example.project2.services.PersonService;
import org.example.project2.util.PersonEmailValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/persons")
public class PersonsController {

    private final PersonService personService;
    private final PersonDAO personDAO;
    private final PersonEmailValidator personEmailValidator;


    public PersonsController(PersonService personService, PersonDAO personDAO, PersonEmailValidator personEmailValidator) {
        this.personService = personService;
        this.personDAO = personDAO;
        this.personEmailValidator = personEmailValidator;
    }

    @GetMapping
    public String showAllPersons (Model model, @RequestParam(value = "page", required = false) Integer page,
                                  @RequestParam(value = "personForPage", required = false) Integer personForPage,
                                  @RequestParam(value = "sortByAge", required = false) Boolean sortByAge){
        model.addAttribute("persons", personService.findAll(page, personForPage, sortByAge));
        return "person/showAllPersons";
    }

    @GetMapping("/{id}")
    public String showOnePerson (@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personService.findOne(id));
        return "person/showOnePerson";
    }

    @PatchMapping("/{id}/unAppointAllFromPerson")
    public String unAppointAll (@PathVariable("id") int id) {
        personDAO.unAppointAll(id);
        return "redirect:/persons/" + id;
    }

    @GetMapping("/new")
    public String createNewPerson (@ModelAttribute("person") Person person){
        return "person/createNewPerson";
    }

    @PostMapping
    public String createNewPerson (@ModelAttribute("person") Person person, BindingResult bindingResult) {
        personEmailValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) return "person/createNewPerson";
        personService.save(person);
        return "redirect:/persons";
    }

    @GetMapping("/{id}/edit")
    public String updatePerson (Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personService.findOne(id));
        return "person/updatePerson";
    }

    @PatchMapping("/{id}")
    public String updatePerson
            (@PathVariable("id") int id ,@ModelAttribute("person") Person person, BindingResult bindingResult){
        personEmailValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) return "person/updatePerson";
        personService.update(id,person);
        return "redirect:/persons";
    }

    @DeleteMapping("/{id}")
    public String delete (@PathVariable("id") int id) {
        personService.delete(id);
        return "redirect:/persons";
    }
}
