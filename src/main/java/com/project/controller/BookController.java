package com.project.controller;

import com.project.DAO.BookDAO;
import com.project.DAO.ReaderPersonDAO;
import com.project.entity.Book;
import com.project.entity.ReaderPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookDAO bookDAO;

    @Autowired
    private ReaderPersonDAO personDAO;
    @GetMapping()
    public String index(Model model){
        model.addAttribute("books",bookDAO.getAllBook());
        return "library/list_of_books";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book){
        return "library/new_book";
    }
    @PostMapping("/create")
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult result){
        if (result.hasErrors())
            return "library/new_book";
        bookDAO.create(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String showBook(@PathVariable("id") int id,Model model,@ModelAttribute("person") ReaderPerson reader){
        model.addAttribute("book",bookDAO.showBook(id));
        model.addAttribute("owner",bookDAO.getOwner(id));
        if (bookDAO.getOwner(id)==null)
            model.addAttribute("readers",personDAO.getAllPerson());
        return "library/show_book";
    }

    @PatchMapping("/{id}/choosing")
    public String chooseOwner(@PathVariable("id") int id,@ModelAttribute("person") ReaderPerson reader){
        bookDAO.choosePersonForBook(id,reader);
        return "redirect:/books";
    }

    @PatchMapping("/{id}")
    public String deletePersonFromBook(@PathVariable("id") int id){
        bookDAO.deletePersonFromBook(id);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String update(@PathVariable("id") int id,Model model){
        model.addAttribute("book",bookDAO.showBook(id));
        return "library/update_book";
    }

    @PatchMapping("/{id}/update")
    public String editBook(@PathVariable("id") int id,@ModelAttribute("book") @Valid Book book,BindingResult result){
        if (result.hasErrors())
            return "library/update_book";
        bookDAO.update(id,book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteBook(@PathVariable("id") int id){
        bookDAO.delete(id);
        return "redirect:/books";
    }
}
