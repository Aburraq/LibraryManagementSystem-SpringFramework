package com.burak.LibraryManagementSystem.service;

import com.burak.LibraryManagementSystem.domain.Book;
import com.burak.LibraryManagementSystem.domain.Teacher;
import com.burak.LibraryManagementSystem.dto.BookDto;
import com.burak.LibraryManagementSystem.exception.ConflictException;
import com.burak.LibraryManagementSystem.exception.ResourceNotFoundException;
import com.burak.LibraryManagementSystem.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private TeacherService teacherService;

    public Book saveBook(Book book) {

        Book bookExist = bookRepository.findByTitleAndAuthorAndPublicationDate(book.getTitle(), book.getAuthor(), book.getPublicationDate());

        //Book existTitle = bookRepository.findByTitle(book.getTitle());
        //Book existAuthor = bookRepository.findByAuthor(book.getAuthor());
        //Book existPD = bookRepository.findByPublicationDate(book.getPublicationDate());
        //boolean bookExist = existTitle != null || existAuthor != null || existPD != null;

        if (bookExist != null) {
            throw new ConflictException(
                                        "Book with title: " + book.getTitle()+
                                        "\nauthor: " +book.getAuthor()+
                                        "\npublication date: "+ book.getPublicationDate() +
                                        " already exist.");
        }

        return bookRepository.save(book);

    }

    public List<Book> getAllBooks() {

        List<Book> list = bookRepository.findAll();
        if (list.isEmpty()) throw new ResourceNotFoundException("Book list is empty.");

        return list;

    }

    public Book getBookById(Long id) {

        return bookRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Book cannot be found with id: " +id));

    }

    public void deleteBookById(Long id) {

        Book book = getBookById(id);
        bookRepository.delete(book);

    }

    public List<Book> getBookByTitle(String title) {

        List<Book> books = bookRepository.findByTitle(title);
        if (books == null) throw new ResourceNotFoundException("Book cannot be found with title: " +title);
        return books;

    }

    @Transactional
    public ResponseEntity<Map<String, String>> addBookForTeacher(Long teacherId, Long bookId) {

        Teacher teacher = teacherService.getTeacherById(teacherId);

        Optional<Book> optionalBook = bookRepository.findById(bookId);

        if (optionalBook.isEmpty()){

            Map<String, String> map = new HashMap<>();
            map.put("message", "Book cannot be found with id: "+bookId);
            map.put("status", "false");

            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }

        Book book = optionalBook.get();

        boolean existBook = teacher.getBooks().stream().anyMatch(b-> b.getId().equals(book.getId()));

        if (existBook) {

            Map<String, String> map = new HashMap<>();
            map.put("message", "Book cannot be found with id: "+bookId);
            map.put("status", "false");

            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }

        teacher.getBooks().add(book);

        Map<String, String> map = new HashMap<>();
        map.put("message", "Book has been added to teacher with id: " + teacherId);
        map.put("status", "true");

        return new ResponseEntity<>(map, HttpStatus.CREATED);

    }

    public void updateBookByDto(Long bookId, BookDto bookDto) {

        Book existBook = getBookById(bookId);
        existBook.setTitle(bookDto.getTitle());
        existBook.setAuthor(bookDto.getAuthor());
        existBook.setPublicationDate(bookDto.getPublicationDate());
        bookRepository.save(existBook);
    }

    public BookDto getBookWithDto(Long id) {

        return bookRepository.findBookByDto(id).orElseThrow(
                ()-> new ResourceNotFoundException("Book cannot be found with id: " +id));

    }

    public Page<Book> getBookWithPage(Pageable pageable) {

        return bookRepository.findAll(pageable);

    }
}
