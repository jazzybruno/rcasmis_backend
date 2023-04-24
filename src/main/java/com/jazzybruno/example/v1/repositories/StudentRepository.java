package com.jazzybruno.example.v1.repositories;

import com.jazzybruno.example.v1.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}