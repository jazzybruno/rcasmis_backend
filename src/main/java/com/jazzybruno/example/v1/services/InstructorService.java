package com.jazzybruno.example.v1.services;

import com.cloudinary.Api;
import com.jazzybruno.example.v1.dto.requests.CreateInstructorDTO;
import com.jazzybruno.example.v1.dto.requests.UpdateInstructorDTO;
import com.jazzybruno.example.v1.payload.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface InstructorService {
public ResponseEntity<ApiResponse> getAllInstructors();
public ResponseEntity<ApiResponse> getInstructorById(Long instructorId);
public ResponseEntity<ApiResponse> createInstructor(CreateInstructorDTO createInstructorDTO);
public ResponseEntity<ApiResponse> createManyInstructor(List<CreateInstructorDTO> createInstructorDTOS);
public ResponseEntity<ApiResponse> updateInstructor( Long instructorId , UpdateInstructorDTO updateInstructorDTO);
public ResponseEntity<ApiResponse> deleteInstructor(Long instructorId);
}
