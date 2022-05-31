package sn.yakhya_diome.book_rentals.services;

import org.springframework.http.ResponseEntity;
import sn.yakhya_diome.book_rentals.payload.request.LoginRequest;
import sn.yakhya_diome.book_rentals.payload.request.RegisterRequest;


public interface AuthService {
     ResponseEntity<?> register(RegisterRequest registerBody);

     ResponseEntity<?> login(LoginRequest loginBody);
}
