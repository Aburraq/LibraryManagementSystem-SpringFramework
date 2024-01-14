package com.burak.LibraryManagementSystem.dto;

import com.burak.LibraryManagementSystem.domain.Book;
import com.burak.LibraryManagementSystem.domain.Teacher;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @NotNull(message = "Name cannot be null.")
    @NotBlank(message = "Name cannot be empty or white space.")
    @Size(min = 4, max = 25, message = "Name '${validatedValue}' should be between {min} and {max}.")
    private String name;

    private String lastName;

    @Email(message = "Please enter a valid e-mail.")
    private String email;

    private String phoneNumber;

    private LocalDateTime registerDate = LocalDateTime.now();
    private List<Book> books = new ArrayList<>();

    public TeacherDto(Teacher teacher){

        this.id = teacher.getId();
        this.name = teacher.getName();
        this.lastName = teacher.getLastName();
        this.phoneNumber = teacher.getPhoneNumber();
        this.email = teacher.getEmail();
        this.registerDate = teacher.getRegisterDate();

    }

}
