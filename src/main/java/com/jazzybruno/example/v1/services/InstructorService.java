package com.jazzybruno.example.v1.services;

import com.cloudinary.Api;
import com.jazzybruno.example.v1.dto.requests.CreateInstructorDTO;
import com.jazzybruno.example.v1.dto.requests.UpdateInstructorDTO;
import com.jazzybruno.example.v1.payload.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface InstructorService {
public ResponseEntity<ApiResponse> getAllInstructors();
public ResponseEntity<ApiResponse> getInstructorById(Long instructorId);
public ResponseEntity<?> getInstructorProfile(Long instructorId) throws IOException;
public ResponseEntity<ApiResponse> createInstructor(CreateInstructorDTO createInstructorDTO);
public ResponseEntity<ApiResponse> uploadInstructorPhoto(Long instructorId , MultipartFile multipartFile);
public ResponseEntity<ApiResponse> createManyInstructor(List<CreateInstructorDTO> createInstructorDTOS);
public ResponseEntity<ApiResponse> updateInstructor( Long instructorId , UpdateInstructorDTO updateInstructorDTO);
public ResponseEntity<ApiResponse> deleteInstructor(Long instructorId);
}
