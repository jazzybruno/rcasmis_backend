package com.jazzybruno.example.v1.repositories;

import com.jazzybruno.example.v1.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}