package com.sasha.bookscrape.controller;

import com.sasha.bookscrape.model.Book;
import com.sasha.bookscrape.repository.BookRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookApiController {

    private final BookRepository bookRepository;

    // Gives this API controller access to the saved books
    public BookApiController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Returns every book as JSON
    @GetMapping
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Returns one book using its UPC
    @GetMapping("/{upc}")
    public Book getBookByUpc(@PathVariable String upc) {
        return bookRepository.findById(upc)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    // Returns books from a certain category
    @GetMapping("/category/{category}")
    public List<Book> getBooksByCategory(@PathVariable String category) {
        return bookRepository.findByCategoryIgnoreCase(category);
    }

    // Returns books with titles that match the search word
    @GetMapping("/search")
    public List<Book> searchBooks(@RequestParam String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    // Returns books from cheapest to most expensive
    @GetMapping("/price/low-to-high")
    public List<Book> getBooksByPriceLowToHigh() {
        return bookRepository.findByOrderByPriceAsc();
    }

    // Returns books from most expensive to cheapest
    @GetMapping("/price/high-to-low")
    public List<Book> getBooksByPriceHighToLow() {
        return bookRepository.findByOrderByPriceDesc();
    }

    // Returns books with a rating of 4 or higher
    @GetMapping("/top-rated")
    public List<Book> getTopRatedBooks() {
        return bookRepository.findByRatingGreaterThanEqual(4);
    }
}