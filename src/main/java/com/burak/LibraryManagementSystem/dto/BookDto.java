package com.burak.LibraryManagementSystem.dto;

import com.burak.LibraryManagementSystem.domain.Book;
import com.burak.LibraryManagementSystem.domain.Teacher;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @NotNull(message = "Name cannot be null.")
    @NotBlank(message = "Name cannot be empty or white space.")
    @Size(min = 4, max = 25, message = "Name '${validatedValue}' should be between {min} and {max}.")
    private String title;

    private String author;

    private String publicationDate;

    @JsonIgnoreProperties("books")
    private List<Teacher> teachers = new ArrayList<>();

    public BookDto(Book book){

        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.publicationDate = book.getPublicationDate();

    }

}
