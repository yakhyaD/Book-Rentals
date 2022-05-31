package sn.yakhya_diome.book_rentals.payload.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

/*
* username
* password
  confirmedPassword
  email
  phoneNumber
  location
  roles = List<String> / admin, user, creator
* */

@Builder
@Getter
@Setter
public class RegisterRequest {

    @NotBlank
     @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 12, max = 12)
    private String phoneNumber;

    @Size(max = 200)
    private String location;

    @NotBlank
    @Size(min= 6)
    private String password;

    @NotBlank
    @Size(min= 6)
    private String passwordConfirm;

    private Set<String> roles;

}
