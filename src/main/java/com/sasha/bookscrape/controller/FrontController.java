package com.sasha.bookscrape.controller;

import com.sasha.bookscrape.model.Book;
import com.sasha.bookscrape.repository.BookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class FrontController {

    private final BookRepository bookRepository;

    // Spring gives this controller access to the book database
    public FrontController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Shows the home page with every book in the database
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return "homePage";
    }

    // Shows a book when the user clicks "View Book"
    @GetMapping("/book/{upc}")
    public String bookDetails(@PathVariable String upc, Model model) {
        Book book = bookRepository.findById(upc)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        model.addAttribute("book", book);
        return "book";
    }

    // Search for books that contain the user's search word in the title
    @GetMapping("/search-page")
    public String searchPage(@RequestParam String title, Model model) {
        List<Book> books = bookRepository.findByTitleContainingIgnoreCase(title);
        model.addAttribute("books", books);
        return "homePage";
    }

    // Shows books with a rating of 4 or 5
    @GetMapping("/top-rated-page")
    public String topRated(Model model) {
        model.addAttribute("books", bookRepository.findByRatingGreaterThanEqual(4));
        return "homePage";
    }

    // Shows cheaper books: matches the "Under £20" text on the home pagef
    @GetMapping("/budget-page")
    public String budgetBooks(Model model) {
        List<Book> budgetBooks = bookRepository.findAll()
                .stream()
                .filter(book -> book.getPrice() < 20)
                .toList();

        model.addAttribute("books", budgetBooks);
        return "homePage";
    }
}