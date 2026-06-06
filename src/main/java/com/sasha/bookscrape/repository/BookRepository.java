package com.sasha.bookscrape.repository;

import com.sasha.bookscrape.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, String> {

    List<Book> findByCategoryIgnoreCase(String category);

    List<Book> findByTitleContainingIgnoreCase(String title);

    List<Book> findByOrderByPriceAsc();

    List<Book> findByOrderByPriceDesc();

    List<Book> findByRatingGreaterThanEqual(int rating);
}