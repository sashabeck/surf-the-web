package com.sasha.bookscrape.service;

import com.sasha.bookscrape.model.Book;
import com.sasha.bookscrape.repository.BookRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
public class Scraper {

    private final BookRepository bookRepository;

    // Gives the scraper access to the database so it can save books
    public Scraper(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Starts the scraper and goes through all 50 pages of the book website.
    public void scrapeBooks() {
        try {
            for (int page = 1; page <= 50; page++) {
                System.out.println("Scraping page " + page);

                String pageUrl = "https://books.toscrape.com/catalogue/page-" + page + ".html";
                scrapeListingPage(pageUrl);
            }
        } catch (Exception e) {
            System.out.println("Error while scraping books: " + e.getMessage());
        }
    }

    // Gets all the small book cards from one catalog page
    private void scrapeListingPage(String pageUrl) throws Exception {
        Document document = Jsoup.connect(pageUrl).get();
        Elements bookCards = document.select(".product_pod");

        for (Element card : bookCards) {
            String title = card.select("h3 a").attr("title");
            String relativeLink = card.select("h3 a").attr("href");
            String productUrl = "https://books.toscrape.com/catalogue/" + relativeLink;

            scrapeBookDetails(productUrl, title);
        }
    }

    // Opens one book's page and collects the details from it
    private void scrapeBookDetails(String productUrl, String title) throws Exception {
        Document document = Jsoup.connect(productUrl).get();

        String upc = document.select("table tr:contains(UPC) td").text();

        // Skip book if already saved so that there are no dupes.
        if (bookRepository.existsById(upc)) {
            return;
        }

        String category = document.select(".breadcrumb li").get(2).text();

        String priceText = document.select(".price_color").first().text();
        double price = Double.parseDouble(priceText.replace("£", ""));

        String availabilityText = document.select(".availability").text();
        boolean inStock = availabilityText.contains("In stock");

        String description = "No description available.";
        Element descriptionHeader = document.select("#product_description").first();

        // Checks if book has description before trying to read one
        if (descriptionHeader != null) {
            Element descriptionParagraph = descriptionHeader.nextElementSibling();

            if (descriptionParagraph != null) {
                description = descriptionParagraph.text();
            }
        }

        String imageRelative = document.select(".item.active img").attr("src");
        String imageUrl = "https://books.toscrape.com/" + imageRelative.replace("../", "");

        String ratingClass = document.select(".star-rating").first().className();
        int rating = convertRatingToNumber(ratingClass);

        Book book = new Book(
                upc,
                title,
                category,
                price,
                rating,
                inStock,
                imageUrl,
                productUrl,
                description
        );

        bookRepository.save(book);
    }

    // The website stores ratings as words, so this changes the word into a number
    private int convertRatingToNumber(String ratingClass) {
        if (ratingClass.contains("One")) return 1;
        if (ratingClass.contains("Two")) return 2;
        if (ratingClass.contains("Three")) return 3;
        if (ratingClass.contains("Four")) return 4;
        if (ratingClass.contains("Five")) return 5;
        return 0;
    }
}