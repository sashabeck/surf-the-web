package com.sasha.bookscrape.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final Scraper scraperService;

    public DataLoader(Scraper scraperService) {
        this.scraperService = scraperService;
    }

    @Override
    public void run(String... args) {
        scraperService.scrapeBooks();
    }
}