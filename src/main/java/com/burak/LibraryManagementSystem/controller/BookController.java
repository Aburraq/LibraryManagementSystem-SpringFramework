package com.burak.LibraryManagementSystem.controller;

import com.burak.LibraryManagementSystem.domain.Book;
import com.burak.LibraryManagementSystem.dto.BookDto;
import com.burak.LibraryManagementSystem.service.BookService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity<Map<String, String>> saveBook(@Valid @RequestBody Book book){

        Book saveBook = bookService.saveBook(book);

        Map<String, String> map = new HashMap<>();
        map.put("message", "Book is saved successfully!");
        map.put("status", "true");

        return new ResponseEntity<>(map, HttpStatus.CREATED);

    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks(){

        List<Book> bookList = bookService.getAllBooks();

        return new ResponseEntity<>(bookList,HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id){

        Book book = bookService.getBookById(id);
        return new ResponseEntity<>(book,HttpStatus.OK);

    }

    @GetMapping("/query")
    public ResponseEntity<Book> getBookByIdRequestParam(@RequestParam Long id){

        Book book = bookService.getBookById(id);
        return new ResponseEntity<>(book, HttpStatus.OK);

    }

    @GetMapping("/title")
    public ResponseEntity<List<Book>> getBookByTitle(@RequestParam String title){

        List<Book> books = bookService.getBookByTitle(title);
        return new ResponseEntity<>(books, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBookById(@PathVariable Long id){

        bookService.deleteBookById(id);
        String message = "Book is deleted successfully with id: " + id;
        return new ResponseEntity<>(message, HttpStatus.OK);

    }

    @PutMapping("/{bookId}")
    public ResponseEntity<Map<String, String>> updateBook(@PathVariable Long bookId,
                                                          @Valid @RequestBody BookDto bookDto){

        bookService.updateBookByDto(bookId, bookDto);
        Map<String, String> map = new HashMap<>();
        map.put("message", "Book is updated successfully!");
        map.put("status", "true");
        return ResponseEntity.ok(map);

    }

    @GetMapping("/query/dto")
    public ResponseEntity<BookDto> getBookWithDto(@RequestParam("id") Long id){

        BookDto bookDto = bookService.getBookWithDto(id);
        return ResponseEntity.ok(bookDto);

    }


    @PostMapping("/{teacherId}/books")
    public ResponseEntity<Map<String, String>> addBookForTeacher(@PathVariable Long teacherId,
                                                                 @RequestParam Long bookId){

        return bookService.addBookForTeacher(teacherId, bookId);

    }

    @GetMapping("page")
    public ResponseEntity<Page<Book>> getBooksWithPage(@RequestParam("p") int page,
                                                       @RequestParam("size") int size,
                                                       @RequestParam("sort") String sort,
                                                       @RequestParam("direction")Sort.Direction direction){

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort));
        Page<Book> bookPage = bookService.getBookWithPage(pageable);
        return ResponseEntity.ok(bookPage);

    }

}
