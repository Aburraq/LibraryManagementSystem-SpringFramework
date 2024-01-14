package com.burak.LibraryManagementSystem.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.rmi.StubNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_teacher")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @NotNull(message = "Name cannot be null.")
    @NotBlank(message = "Name cannot be empty or white space.")
    @Size(min = 4, max = 25, message = "Name '${validatedValue}' should be between {min} and {max}.")
    private String name;

    @Column(nullable = false, length = 30, unique = false)
    private String lastName;

    @Email(message = "Please enter a valid e-mail.")
    @Column(nullable = false, length = 50, unique = true)
    private String email;

    private String phoneNumber;

    @Setter(AccessLevel.NONE)
    private LocalDateTime registerDate = LocalDateTime.now();

    @ManyToMany
    @JoinTable(name = "teacher_book",
            joinColumns = @JoinColumn(name = "teacher_id"), inverseJoinColumns = @JoinColumn(name = "book_id"))
    private List<Book> books = new ArrayList<>();

}
