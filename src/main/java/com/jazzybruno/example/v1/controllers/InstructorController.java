package com.jazzybruno.example.v1.controllers;

import com.jazzybruno.example.v1.dto.requests.CreateInstructorDTO;
import com.jazzybruno.example.v1.dto.requests.UpdateInstructorDTO;
import com.jazzybruno.example.v1.payload.ApiResponse;
import com.jazzybruno.example.v1.serviceImpls.InstructorServiceImpl;
import com.jazzybruno.example.v1.services.InstructorService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/instructors/")
public class InstructorController {
    private final InstructorServiceImpl instructorService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllInstructors() {
        return instructorService.getAllInstructors();
    }

    @GetMapping("/id/{instructorId}")
    public ResponseEntity<ApiResponse> getInstructorById(@PathVariable  Long instructorId) {
        return instructorService.getInstructorById(instructorId);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createInstructor(@RequestBody CreateInstructorDTO createInstructorDTO) {
        return instructorService.createInstructor(createInstructorDTO);
    }

    @PostMapping("/upload/{instructorId}")
    public ResponseEntity<ApiResponse> uploadInstructorPhoto(@PathVariable Long instructorId, @RequestParam("file") MultipartFile multipartFile) {
        return instructorService.uploadInstructorPhoto(instructorId , multipartFile);
    }

    @PostMapping("/create/all")
    public ResponseEntity<ApiResponse> createManyInstructor(@RequestBody  List<CreateInstructorDTO> createInstructorDTOS) {
        return instructorService.createManyInstructor(createInstructorDTOS);
    }

    @PutMapping("/update/{instructorId}")
    public ResponseEntity<ApiResponse> updateInstructor(@PathVariable Long instructorId, @RequestBody UpdateInstructorDTO updateInstructorDTO) {
        return instructorService.updateInstructor(instructorId , updateInstructorDTO);
    }

    @DeleteMapping("/delete/{instructorId}")
    public ResponseEntity<ApiResponse> deleteInstructor(@PathVariable Long instructorId) {
        return instructorService.deleteInstructor(instructorId);
    }
}
