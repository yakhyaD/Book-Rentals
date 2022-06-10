package sn.yakhya_diome.book_rentals.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sn.yakhya_diome.book_rentals.models.Cart;
import sn.yakhya_diome.book_rentals.services.Impl.CartServiceImpl;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/user/cart")
//@PreAuthorize("hasRole('ROLE_USER')")
//@AllArgsConstructor
public class CartController {

    @Autowired
    private CartServiceImpl cartService;

    @GetMapping
    private Optional<Cart> getUserCart(@RequestHeader("Authorization") String token){
        return cartService.getUserCart(token);
    }

    @PostMapping("/{bookId}")
    private String addBookToCart(
            @RequestHeader("Authorization") String token,
            @PathVariable("bookId") Long bookId
    ){
        return cartService.addToCart(bookId, token);
    }
    
    @DeleteMapping("/{bookId}")
    private void removeBookFromCart(
            @RequestHeader("Authorization") String token,
            @PathVariable("bookId") Long bookId
    ){
        cartService.removeFromCart(bookId, token);
    }
}
