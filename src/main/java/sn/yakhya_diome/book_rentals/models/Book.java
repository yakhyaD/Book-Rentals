package sn.yakhya_diome.book_rentals.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "books")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    @NotNull
    private String title;

    @Column(length = 100)
    @NotNull
    private String author;

    @Column(unique = true)
    @NotNull
    private String isbn;

    @Column(length = 1000)
    @NotNull
    private String description;

    private Boolean available = false;

    // one book has one publisher(user)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User publisher;

    private String coverUrl;

}
