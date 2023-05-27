package com.jazzybruno.example.v1.services;

import com.cloudinary.Api;
import com.jazzybruno.example.v1.dto.requests.CreateStudentDTO;
import com.jazzybruno.example.v1.dto.requests.UpdateStudentDTO;
import com.jazzybruno.example.v1.payload.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface StudentService {
    public ResponseEntity<ApiResponse> getAllStudents();
    public ResponseEntity<ApiResponse> getStudentById(Long studentId);
    public ResponseEntity<ApiResponse> createStudent(CreateStudentDTO createStudentDTO);
    public ResponseEntity<ApiResponse> createManyStudents(List<CreateStudentDTO> createStudentDTOS);
    public ResponseEntity<ApiResponse>updateStudent(Long studentId , UpdateStudentDTO updateStudentDTO);
    public ResponseEntity<ApiResponse>deleteStudent(Long studentId);
}
