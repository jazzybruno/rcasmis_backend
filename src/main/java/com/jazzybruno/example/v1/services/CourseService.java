package com.jazzybruno.example.v1.services;

import com.jazzybruno.example.v1.dto.requests.CreateCourseDTO;
import com.jazzybruno.example.v1.dto.requests.UpdateCourseDTO;
import com.jazzybruno.example.v1.models.Course;
import com.jazzybruno.example.v1.payload.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface CourseService {
    public ResponseEntity<ApiResponse> getAllCourses();
    public ResponseEntity<ApiResponse> getCourseById(Long courseId);
    public ResponseEntity<ApiResponse> createCourse(CreateCourseDTO createCourseDTO);
    public ResponseEntity<ApiResponse> updateCourse(Long courseId , UpdateCourseDTO updateCourseDTO);
    public ResponseEntity<ApiResponse> deleteCourse(Long courseId);

}
