package com.jazzybruno.example.v1.repositories;

import com.jazzybruno.example.v1.models.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {
}