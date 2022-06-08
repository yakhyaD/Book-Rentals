package sn.yakhya_diome.book_rentals.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sn.yakhya_diome.book_rentals.models.Book;
import sn.yakhya_diome.book_rentals.models.Cart;
import sn.yakhya_diome.book_rentals.services.Impl.BookServiceImpl;
import sn.yakhya_diome.book_rentals.services.Impl.CartServiceimpl;

import java.util.List;
import java.util.Optional;

/*
* Base url: /api/v1/user
*   GET: /books: list all available books
*   GET: /books/:id:  book by id
*   GET: /cart: user cart
*   POST: /cart/:id: add book to user cart
*   DELETE: /cart/:id: delete book from user cart
* */

@RestController
@RequestMapping("api/v1/user")
@AllArgsConstructor
public class UserController {

    @Autowired
    private BookServiceImpl bookService;

    @Autowired
    private CartServiceimpl cartService;


    @GetMapping("/books")
    public List<Book> getBooks(){
        return bookService.getBooks();
    }

    @GetMapping("/books/{id}")
     public Book getBook(@PathVariable Long id){
        return bookService.getBook(id);
    }

    @GetMapping("/cart")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Optional<Cart> getUserCart(@RequestHeader("Authorization") String token){
        return cartService.getUserCart(token);
    }


    @PostMapping("/cart/{bookId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String addToCart(@PathVariable Long  bookId, @RequestHeader("Authorization") String token){
        return cartService.addToCart(bookId, token);
    }

    @DeleteMapping("/cart/{bookId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public void deleteFromCart(@PathVariable Long bookId, @RequestHeader("Authorization") String token) {
        cartService.deleteFromCart(bookId, token);
    }
}
