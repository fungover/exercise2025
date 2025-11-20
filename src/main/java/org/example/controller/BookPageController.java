package org.example.controller;

import org.example.repository.BookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("books")
public class BookPageController {

    private final BookRepository bookRepository;

    public BookPageController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping
    public String bookPage(){
        return "books";
    }

     @GetMapping("/list")
    public String listOfBooks(Model model){
        model.addAttribute("books", bookRepository.findAll());
        return "listOfBooks :: listOfBooks";
     }

}
