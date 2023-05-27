package com.jazzybruno.example.v1.controllers;

import com.jazzybruno.example.v1.dto.requests.CreateStudentDTO;
import com.jazzybruno.example.v1.dto.requests.UpdateStudentDTO;
import com.jazzybruno.example.v1.payload.ApiResponse;
import com.jazzybruno.example.v1.serviceImpls.StudentServiceImpl;
import com.jazzybruno.example.v1.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentServiceImpl studentService;


    @GetMapping
    public ResponseEntity<ApiResponse> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/id/{studentId}")
    public ResponseEntity<ApiResponse> getStudentById(@PathVariable Long studentId) {
        return studentService.getStudentById(studentId);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createStudent(@RequestBody CreateStudentDTO createStudentDTO) {
        return studentService.createStudent(createStudentDTO);
    }

    @PostMapping("/create/all")
    public ResponseEntity<ApiResponse> createManyStudents(@RequestBody  List<CreateStudentDTO> createStudentDTOS) {
        return studentService.createManyStudents(createStudentDTOS);
    }

    @PutMapping("/update/{studentId}")
    public ResponseEntity<ApiResponse> updateStudent( @PathVariable Long studentId, @RequestBody UpdateStudentDTO updateStudentDTO) {
        return studentService.updateStudent(studentId , updateStudentDTO);
    }

    @DeleteMapping("/delete/{studentId}")
    public ResponseEntity<ApiResponse> deleteStudent(@PathVariable  Long studentId) {
        return studentService.deleteStudent(studentId);
    }
}
