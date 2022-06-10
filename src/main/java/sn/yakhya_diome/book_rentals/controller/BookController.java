package sn.yakhya_diome.book_rentals.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sn.yakhya_diome.book_rentals.payload.request.BookBody;
import sn.yakhya_diome.book_rentals.services.Impl.BookServiceImpl;
import sn.yakhya_diome.book_rentals.models.Book;

import java.util.List;


@RestController
@RequestMapping("api/v1/books")
public class BookController {

    @Autowired
    private BookServiceImpl bookService;

    @GetMapping
    public List<Book> getBooks(){
        return bookService.getBooks();
    }

    @GetMapping("/{id}")
    public Book getBook(@PathVariable Long id){
        return bookService.getBook(id);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CREATOR')")
    public String addBook(@RequestBody BookBody newBook, @RequestHeader("Authorization") String token){
        return bookService.addBook(newBook, token);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CREATOR')")
    public String updateBook(@RequestHeader("Authorization") String token, @PathVariable Long id, @RequestBody BookBody updatedBook){
       return bookService.updateBook(id, updatedBook, token);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CREATOR')")
    public void deleteBook(@PathVariable Long id, @RequestHeader("Authorization") String token){
        bookService.deleteBook(id, token);
    }

    @GetMapping("/search")
    public List<Book> searchBooks(@RequestParam(value = "query", required = false) String query){
        return bookService.searchBooks(query);
    }
}
