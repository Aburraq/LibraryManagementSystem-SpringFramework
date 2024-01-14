package com.burak.LibraryManagementSystem.service;

import com.burak.LibraryManagementSystem.domain.Book;
import com.burak.LibraryManagementSystem.domain.Teacher;
import com.burak.LibraryManagementSystem.dto.TeacherDto;
import com.burak.LibraryManagementSystem.exception.ConflictException;
import com.burak.LibraryManagementSystem.exception.ResourceNotFoundException;
import com.burak.LibraryManagementSystem.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private BookService bookService;

    public Teacher saveTeacher(Teacher teacher) {

        Teacher existTeacher = teacherRepository.findByEmail(teacher.getEmail());
        if (existTeacher != null) throw new ConflictException("Teacher with same "+teacher.getEmail()+" already exist.");
        return teacherRepository.save(teacher);

    }

    public List<Teacher> findAllTeacher() {

        List<Teacher> teachers = teacherRepository.findAll();
        if (teachers.isEmpty()) throw new ResourceNotFoundException("Teacher list is empty.");
        return teachers;

    }

    public Teacher getTeacherById(Long id) {

        return teacherRepository.findById(id).orElseThrow(()->
        new ResourceNotFoundException("Teacher cannot be found with id: " + id));


    }

    public void deleteTeacherById(Long id) {

        Teacher teacher = getTeacherById(id);
        teacherRepository.delete(teacher);

    }

    public void updateTeacherByDTO(Long teacherId, TeacherDto teacherDto) {

        Teacher existTeacher = getTeacherById(teacherId);

        if (!existTeacher.getEmail().equalsIgnoreCase(teacherDto.getEmail())){

            Teacher updateWithUpdateEmail = teacherRepository.findTeacherByEmail(teacherDto.getEmail());

            if (updateWithUpdateEmail != null) throw new ConflictException("E-mail is already used before");

        }

        existTeacher.setName(teacherDto.getName());
        existTeacher.setLastName(teacherDto.getLastName());
        existTeacher.setEmail(teacherDto.getEmail());
        existTeacher.setPhoneNumber(teacherDto.getPhoneNumber());
        teacherRepository.save(existTeacher);

    }

    public TeacherDto getTeacherByDto(Long id) {

        return teacherRepository.findTeacherByDto(id).orElseThrow(
                ()-> new ResourceNotFoundException("Teacher cannot be found with id: " +id));

    }

    public Page<Teacher> getTeacherWithPage(Pageable pageable) {

        return teacherRepository.findAll(pageable);

    }

    @Transactional
    public ResponseEntity<Map<String, String>> addTeacherForBook(Long bookId, Long teacherId) {

        Book existBook = bookService.getBookById(bookId);
        Teacher existTeacher = getTeacherById(teacherId);
        boolean isTeacherAdded = existBook.getTeachers().stream().anyMatch(t-> t.getId().equals(existTeacher.getId()));

        Map<String, String> map = new HashMap<>();

        if (isTeacherAdded){

            map.put("message", "Teacher cannot be found with id: " + teacherId);
            map.put("status", "false");
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);

        }

        existBook.getTeachers().add(existTeacher);
        map.put("message", "Teacher is added to the book with id: " + bookId);
        map.put("status", "true");
        return new ResponseEntity<>(map, HttpStatus.CREATED);



    }
}
