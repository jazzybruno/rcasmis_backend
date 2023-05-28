package com.jazzybruno.example.v1.controllers;

import com.jazzybruno.example.v1.dto.requests.CreateEmployeeDTO;
import com.jazzybruno.example.v1.dto.requests.UpdateEmployeeDTO;
import com.jazzybruno.example.v1.payload.ApiResponse;
import com.jazzybruno.example.v1.serviceImpls.EmployeeServiceImpl;
import com.jazzybruno.example.v1.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {
    private final EmployeeServiceImpl employeeService;
    @GetMapping
    public ResponseEntity<ApiResponse> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/id/{employeeId}")
    public ResponseEntity<ApiResponse> getEmployeeById(@PathVariable Long employeeId) {
        return employeeService.getEmployeeById(employeeId);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createEmployee(@RequestBody CreateEmployeeDTO createEmployeeDTO) {
        return employeeService.createEmployee(createEmployeeDTO);
    }

    @PostMapping("/upload/{employeeId}")
    public ResponseEntity<ApiResponse> uploadEmployeePhoto(@PathVariable Long employeeId, @RequestParam("file") MultipartFile multipartFile) {
        return employeeService.uploadEmployeePhoto(employeeId , multipartFile);
    }

    @PostMapping("/create/all")
    public ResponseEntity<ApiResponse> createManyEmployees(@RequestBody List<CreateEmployeeDTO> createEmployeeDTOS) {
        return employeeService.createManyEmployees(createEmployeeDTOS);
    }

    @PutMapping("/update/{employeeId}")
    public ResponseEntity<ApiResponse> updateEmployee(@PathVariable Long employeeId, @RequestBody UpdateEmployeeDTO updateEmployeeDTO) {
        return employeeService.updateEmployee(employeeId , updateEmployeeDTO);
    }

    @DeleteMapping("/delete/{employeeId}")
    public ResponseEntity<ApiResponse> deleteEmployee(@PathVariable Long employeeId) {
        return employeeService.deleteEmployee(employeeId);
    }
}
