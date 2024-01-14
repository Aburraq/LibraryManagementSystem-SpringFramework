package com.burak.LibraryManagementSystem.repository;

import com.burak.LibraryManagementSystem.domain.Teacher;
import com.burak.LibraryManagementSystem.dto.TeacherDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Teacher findByEmail(String email);

    Teacher findTeacherByEmail(String email);

    @Query("SELECT new com.burak.LibraryManagementSystem.dto.TeacherDto (t) FROM Teacher t WHERE t.id =:id")
    Optional<TeacherDto> findTeacherByDto(@Param("id")Long id);
}
