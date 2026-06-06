package com.sasha.bookscrape.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Book {

    // UPC is unique for each book
    @Id
    private String upc;

    private String title;
    private String category;
    private double price;
    private int rating;
    private boolean inStock;
    private String imageUrl;
    private String productUrl;

    // Gives description room
    @Column(length = 100000)
    private String description;

    // Spring needs this empty constructor to create Book objects
    public Book() {
    }

    // This constructor allows quick creation of a book after scraping the website
    public Book(String upc, String title, String category, double price, int rating,
                boolean inStock, String imageUrl, String productUrl, String description) {
        this.upc = upc;
        this.title = title;
        this.category = category;
        this.price = price;
        this.rating = rating;
        this.inStock = inStock;
        this.imageUrl = imageUrl;
        this.productUrl = productUrl;
        this.description = description;
    }

    public String getUpc() {
        return upc;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public int getRating() {
        return rating;
    }

    public boolean isInStock() {
        return inStock;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}