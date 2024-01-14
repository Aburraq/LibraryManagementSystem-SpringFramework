package com.burak.LibraryManagementSystem.controller;

import com.burak.LibraryManagementSystem.domain.Teacher;
import com.burak.LibraryManagementSystem.dto.TeacherDto;
import com.burak.LibraryManagementSystem.service.BookService;
import com.burak.LibraryManagementSystem.service.TeacherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties;
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
@RequestMapping("/teachers")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @PostMapping
    public ResponseEntity<Map<String, String>> saveTeacher(@Valid @RequestBody Teacher teacher){

        Teacher saveTeacher = teacherService.saveTeacher(teacher);

        Map<String,String> map= new HashMap<>();
        map.put("message","Teacher has been successfully saved!");
        map.put("status","true");
        return new ResponseEntity<>(map, HttpStatus.CREATED);//201
    }

    @GetMapping
    public ResponseEntity<List<Teacher>> getAllTeachers(){

        List<Teacher> teachers = teacherService.findAllTeacher();
        return new ResponseEntity<>(teachers, HttpStatus.OK); // 200

    }

    @GetMapping("/{id}")
    public ResponseEntity<Teacher> findTeacherByIdUsingPathVariable(@PathVariable Long id){

        Teacher teacher = teacherService.getTeacherById(id);
        return new ResponseEntity<>(teacher, HttpStatus.OK);

    }

    @GetMapping("/query")
    public ResponseEntity<Teacher> findTeacherByIdUsingRequestParam(@RequestParam Long id){

        Teacher teacher = teacherService.getTeacherById(id);
        return new ResponseEntity<>(teacher, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTeacherById(@PathVariable Long id){

        teacherService.deleteTeacherById(id);
        String message = "Teacher is deleted successfully with id: "+id;
        return new ResponseEntity<>(message, HttpStatus.OK);

    }

    @PutMapping("/{teacherId}")
    public ResponseEntity<Map<String, String>> updateTeacher(@PathVariable Long teacherId,
                                                             @Valid @RequestBody TeacherDto teacherDto){

        teacherService.updateTeacherByDTO(teacherId, teacherDto);
        Map<String, String> map = new HashMap<>();
        map.put("message", "Teacher has been successfully updated with id: " + teacherId);
        map.put("status", "true");
        return ResponseEntity.ok(map);

    }

    @GetMapping("/query/dto")
    public ResponseEntity<TeacherDto> getTeacherByDto(@RequestParam("id") Long id){

        TeacherDto teacherDto = teacherService.getTeacherByDto(id);
        return ResponseEntity.ok(teacherDto);

    }

    @GetMapping("/page")
    public ResponseEntity<Page<Teacher>> getTeacherWithPage(@RequestParam("page") int page,
                                                            @RequestParam("size") int size,
                                                            @RequestParam("sort") String properties,
                                                            @RequestParam("direction")Sort.Direction direction){

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, properties));
        Page<Teacher> pageOfTeachers = teacherService.getTeacherWithPage(pageable);
        return ResponseEntity.ok(pageOfTeachers);

    }

    @PostMapping("{bookId}/teachers")
    public ResponseEntity<Map<String, String>> addTeacherForBook(@PathVariable Long bookId,
                                                                 @RequestParam Long teacherId){

        return teacherService.addTeacherForBook(bookId, teacherId);

    }



}
