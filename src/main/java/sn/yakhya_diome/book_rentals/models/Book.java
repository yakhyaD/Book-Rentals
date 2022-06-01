package sn.yakhya_diome.book_rentals.models;

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

    @Column(length = 20, unique = true)
    @NotNull
    private String isbn;

    @Column(length = 250)
    @NotNull
    private String description;

    private Boolean available = false;

    // one book has one publisher(user)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User publisher;


    private String coverUrl;

}
