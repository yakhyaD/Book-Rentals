package sn.yakhya_diome.book_rentals.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sn.yakhya_diome.book_rentals.models.Book;
import sn.yakhya_diome.book_rentals.payload.request.BookBody;
import sn.yakhya_diome.book_rentals.services.Impl.BookServiceImpl;

import java.util.List;

@RestController
@RequestMapping("api/v1/creator/books")
@PreAuthorize("hasRole('ROLE_CREATOR')")
public class CreatorBookController {

    @Autowired
    private BookServiceImpl bookService;


    @GetMapping
    public List<Book> getCreatorBooks(@RequestHeader("Authorization") String token){
       return bookService.getCreatorBooks(token);
    }

    @GetMapping("/{id}")
    public Book getBook(@PathVariable("id") Long id){
        return bookService.getBook(id);
    }

    @PostMapping("/create")
    public String addBook(@RequestBody BookBody newBook, @RequestHeader("Authorization") String token){
        return bookService.addBook(newBook, token);
    }

    @PutMapping("/{id}")
    public String updateBook(@RequestHeader("Authorization") String token, @PathVariable Long id, @RequestBody BookBody updatedBook){
       return bookService.updateBook(id, updatedBook, token);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id, @RequestHeader("Authorization") String token){
        bookService.deleteBook(id, token);
    }

}
