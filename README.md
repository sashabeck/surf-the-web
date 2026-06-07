**README**

Surf The Web is a way for users to easily discover books based off ratings and price. It has search funcitonality to find specific books and authors. It is a Java Spring Boot web application that scrapes book data from books.toscrape.com. I based its theme on the ocean with blue colors and designs.

Features of Surf The Web:

- Scrapes + displays book title, UPC, category, price, rating, availability, image, and description
- Displays books in a responsive frontend
- Allows users to search by title
- Includes top-rated and budget-friendly book sections
- Stores book data in an H2 database
- Provides REST API endpoints

I used:

- Java
- Spring Boot
- Spring Data JPA
- Thymeleaf
- H2 Database
- JSoup
- Bootstrap

API endpoints:

To return all of the books: GET /api/books  

To return a single book by UPC: GET /api/books/{upc}  

To return books from a specific category: GET /api/books/category/{category}  

Search a book by its title: GET /api/books/search?title=example  

Return books rated 4 stars or higher: GET /api/books/top-rated  

The scraper uses JSoup to download HTML from books.toscrape.com. It starts with listing pages, finds each book card, follows the detail page link, and extracts more specific information like UPC, category, description, and availability.

I ran into multiple challenges during the project. The first one was translating different formats from the scraped website to return a different one on the website. For example, the scraped image and product links were not complete URLs, so I had to convert them into full links before displaying them. Additionally, the ratings were coded as words, so I had to convert them into numbers. Another challenge I ran into was loading all the books. I started coding the website by only loading the first 5 pages of the website so that it could run quickly. Once I was satisfied with the design and functionality, I loaded all 50 pages to display 10000 books. Now, the final website displays every book from the website, but it does take a while to load all of them.

In the future, I would focus on improving performance and scalability. Currently, book data is scraped and processed when users use the search bar, which increases the load time. In the future, I would implement a caching system or store previously scraped book information in a database to allow results to be retrieved much faster and reduce the need for repeated web requests. I also would like to implement more advanced algorithms, expand filtering options, and support a larger collection of books that load faster.
