package com.project.controller;

import com.project.DAO.ReaderPersonDAO;
import com.project.entity.ReaderPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class ReaderRersonController {
    @Autowired
    private ReaderPersonDAO readerPersonDAO;

    @GetMapping()
    public String index(Model model){
        model.addAttribute("people",readerPersonDAO.getAllPerson());
        return "library/all_reader_person";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("person",readerPersonDAO.showPerson(id));
        model.addAttribute("books",readerPersonDAO.getBookByPersonId(id));
        return "library/show_reader_person";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") ReaderPerson person){
        return "library/new_person";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid ReaderPerson person, BindingResult result, Model model){
        if (result.hasErrors())
            return "library/new_person";
        System.out.println(person.getFullName()+" "+person.getYearOfBirth());
        if (readerPersonDAO.getFullNamePerson(person.getFullName())!=null){
            model.addAttribute("current",readerPersonDAO.getFullNamePerson(person.getFullName()));
            return "library/new_person";
        }

        readerPersonDAO.create(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/update")
    public String update(@PathVariable("id") int id, Model model){
        model.addAttribute("person",readerPersonDAO.showPerson(id));
        return "library/update_person";
    }

    @PatchMapping("/{id}")
    public String edit(@ModelAttribute("person") @Valid ReaderPerson person,BindingResult result,@PathVariable("id") int id){
        if (result.hasErrors())
            return "library/update_person";
        readerPersonDAO.update(id,person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        readerPersonDAO.delete(id);
        return "redirect:/people";
    }
}
