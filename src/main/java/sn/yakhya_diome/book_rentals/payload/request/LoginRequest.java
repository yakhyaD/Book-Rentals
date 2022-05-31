package sn.yakhya_diome.book_rentals.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class LoginRequest {


        @NotBlank
        @Size(min = 3)
        private String username;

        @NotBlank
        @Size(min = 6)
        private String password;

}
