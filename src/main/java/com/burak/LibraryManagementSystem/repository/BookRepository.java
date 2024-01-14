package com.burak.LibraryManagementSystem.repository;

import com.burak.LibraryManagementSystem.domain.Book;
import com.burak.LibraryManagementSystem.dto.BookDto;
import com.burak.LibraryManagementSystem.dto.TeacherDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByTitle(String title);
    Book findByAuthor(String author);
    Book findByPublicationDate(String publicationDate);
    Book findByTitleAndAuthorAndPublicationDate(String title, String author, String publicationDate);

    @Query("SELECT new com.burak.LibraryManagementSystem.dto.BookDto (b) FROM Book b WHERE b.id =:id")
    Optional<BookDto> findBookByDto(@Param("id")Long id);


}
