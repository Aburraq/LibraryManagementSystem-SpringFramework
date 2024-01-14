package com.burak.LibraryManagementSystem.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @NotNull(message = "Name cannot be null.")
    @NotBlank(message = "Name cannot be empty or white space.")
    @Size(min = 4, max = 25, message = "Name '${validatedValue}' should be between {min} and {max}.")
    private String title;

    private String author;

    @Column(nullable = false)
    private String publicationDate;

    @ManyToMany(mappedBy = "books", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("books")
    private List<Teacher> teachers = new ArrayList<>();

}
