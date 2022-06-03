package sn.yakhya_diome.book_rentals.payload.request;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class bookBody {

    @NotNull
    private String title;

    @NotNull
    private String author;

    @NotNull
    @Size(min = 6)
    private String isbn;

    @Size(max = 250)
    private String description;

    @NotNull
    private String coverUrl;

    private Boolean available = false;
}
