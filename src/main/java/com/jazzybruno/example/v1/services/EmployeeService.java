package com.jazzybruno.example.v1.services;

import com.jazzybruno.example.v1.dto.requests.CreateEmployeeDTO;
import com.jazzybruno.example.v1.dto.requests.UpdateEmployeeDTO;
import com.jazzybruno.example.v1.payload.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EmployeeService {
    public ResponseEntity<ApiResponse> getAllEmployees();
    public ResponseEntity<ApiResponse> getEmployeeById(Long employeeId);
    public ResponseEntity<ApiResponse> createEmployee(CreateEmployeeDTO createEmployeeDTO);
    public ResponseEntity<ApiResponse> uploadEmployeePhoto(Long employeeId , MultipartFile multipartFile);
    public ResponseEntity<ApiResponse> createManyEmployees(List<CreateEmployeeDTO> createEmployeeDTOS);
    public ResponseEntity<ApiResponse> updateEmployee(Long employeeId , UpdateEmployeeDTO updateEmployeeDTO);
    public ResponseEntity<ApiResponse> deleteEmployee(Long employeeId);
}
