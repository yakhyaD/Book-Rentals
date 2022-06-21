package sn.yakhya_diome.book_rentals.payload.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EmailDetails {
    @Email
    @NotNull
    private String recipient;

    @NotNull
    private String subject;

    @NotNull
    private String emailBody;

    private String attachment;
}
