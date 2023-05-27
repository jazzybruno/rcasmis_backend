package com.jazzybruno.example.v1.controllers;

import com.jazzybruno.example.v1.dto.requests.CreateCourseDTO;
import com.jazzybruno.example.v1.dto.requests.UpdateCourseDTO;
import com.jazzybruno.example.v1.payload.ApiResponse;
import com.jazzybruno.example.v1.serviceImpls.CourseServiceImpl;
import com.jazzybruno.example.v1.services.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseServiceImpl courseService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/id/{courseId}")
    public ResponseEntity<ApiResponse> getCourseById(@PathVariable Long courseId) {
        return courseService.getCourseById(courseId);
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<ApiResponse> createCourse( @RequestBody  CreateCourseDTO createCourseDTO) {
        return courseService.createCourse(createCourseDTO);
    }

    @PutMapping("/update/{courseId}")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<ApiResponse> updateCourse(@PathVariable Long courseId, @RequestBody UpdateCourseDTO updateCourseDTO) {
        return courseService.updateCourse(courseId , updateCourseDTO);
    }

    @DeleteMapping("/delete/{courseId}")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<ApiResponse> deleteCourse(@PathVariable Long courseId) {
        return courseService.deleteCourse(courseId);
    }
}
