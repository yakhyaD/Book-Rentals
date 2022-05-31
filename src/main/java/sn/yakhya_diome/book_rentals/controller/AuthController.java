package sn.yakhya_diome.book_rentals.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.yakhya_diome.book_rentals.payload.request.LoginRequest;
import sn.yakhya_diome.book_rentals.payload.request.RegisterRequest;
import sn.yakhya_diome.book_rentals.services.Impl.AuthServiceImpl;

import javax.validation.Valid;


/*
    * POST /register  -->  Register a new user
    * POST /login  -->  Login a user
 */

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthServiceImpl authService;

    @PostMapping("/signup")
    public ResponseEntity<?> register (@Valid @RequestBody RegisterRequest registerBody) {
        return authService.register(registerBody);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login (@Valid @RequestBody LoginRequest loginBody) {
        return authService.login(loginBody);
    }

}
