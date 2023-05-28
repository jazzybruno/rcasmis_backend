package com.jazzybruno.example.v1.serviceImpls;

import com.jazzybruno.example.v1.dto.requests.CreateEmployeeDTO;
import com.jazzybruno.example.v1.dto.requests.UpdateEmployeeDTO;
import com.jazzybruno.example.v1.dto.responses.FileUploadResponse;
import com.jazzybruno.example.v1.models.Department;
import com.jazzybruno.example.v1.models.Employee;
import com.jazzybruno.example.v1.models.Student;
import com.jazzybruno.example.v1.payload.ApiResponse;
import com.jazzybruno.example.v1.repositories.DepartmentsRepository;
import com.jazzybruno.example.v1.repositories.EmployeeRepository;
import com.jazzybruno.example.v1.services.EmployeeService;
import com.jazzybruno.example.v1.utils.FileUpload;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentsRepository departmentsRepository;

    public ResponseEntity<ApiResponse> getAllEmployees() {
        try {
            List<Employee> employees = employeeRepository.findAll();
            return ResponseEntity.ok().body(
                    new ApiResponse(
                            true,
                            "Successfully fetched all the employees",
                            employees
                    )
            );
        }catch (Exception e){
            return ResponseEntity.status(500).body(
                    new ApiResponse(
                            false,
                            e.getMessage()
                    )
            );
        }
    }

    @Override
    public ResponseEntity<ApiResponse> getEmployeeById(Long employeeId) {
        if(employeeRepository.existsById(employeeId)){
            Employee employee = employeeRepository.findById(employeeId).get();
            return ResponseEntity.ok().body(
                    new ApiResponse(
                            true,
                            "Successfully fetched the employee with id: " + employee,
                            employee
                    )
            );
        }else {
            return ResponseEntity.status(404).body(
                    new ApiResponse(
                            false,
                            "The employee with id: " + employeeId + " does not exist"
                    )
            );
        }
    }

    @Override
    public ResponseEntity<ApiResponse> createEmployee(CreateEmployeeDTO createEmployeeDTO) {
        if (departmentsRepository.existsById(createEmployeeDTO.getDepartmentId())){
        try {
            Department department = departmentsRepository.findById(createEmployeeDTO.getDepartmentId()).get();
            Employee  employee = new Employee(
                    createEmployeeDTO.getName(),
                    createEmployeeDTO.getCode(),
                    createEmployeeDTO.getJobTitle(),
                    createEmployeeDTO.getDob(),
                    department
            );
            employeeRepository.save(employee);
            return ResponseEntity.ok().body(
                    new ApiResponse(
                            true,
                            "Successfully saved the employee",
                            employee
                    )
            );
        }catch (Exception e){
            return ResponseEntity.status(500).body(
                    new ApiResponse(
                            false,
                            e.getMessage()
                    )
            );
        }
        }else {
            return ResponseEntity.status(404).body(
                    new ApiResponse(
                            false,
                            "The Department with id: " + createEmployeeDTO.getDepartmentId() + " does not exist"
                    )
            );
        }
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse> uploadEmployeePhoto(Long employeeId, MultipartFile multipartFile) {
        try {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            long size = multipartFile.getSize();
            if(employeeRepository.existsById(employeeId)){
                Employee employee = employeeRepository.findById(employeeId).get();
                if(employee.getProfileId() == null){
                    String fileCode = FileUpload.saveFile(fileName , multipartFile);
                    FileUploadResponse fileUploadResponse = new FileUploadResponse();
                    fileUploadResponse.setSize(size);
                    fileUploadResponse.setFileName(fileName);
                    fileUploadResponse.setDownloadUri("/downloadFile/" + fileCode);
                    employee.setProfileId(fileCode);

                    return ResponseEntity.ok().body(
                            new ApiResponse(
                                    true,
                                    "Successfully uploaded the employee's picture with name: " + employee.getName(),
                                    fileUploadResponse
                            )
                    );

                }else{
                    // TODO: 5/27/2023 Delete the existing and add the new photo
                    String savedFileCode = employee.getProfileId();
                    String filePath = FileUpload.getFile(savedFileCode);
                    System.out.println(filePath);
                    boolean isFilePresent = FileUpload.deleteFile(filePath);
                    if(isFilePresent){
                        String fileCode = FileUpload.saveFile(fileName , multipartFile);
                        FileUploadResponse fileUploadResponse = new FileUploadResponse();
                        fileUploadResponse.setSize(size);
                        fileUploadResponse.setFileName(fileName);
                        fileUploadResponse.setDownloadUri("/downloadFile/" + fileCode);
                        employee.setProfileId(fileCode);

                        return ResponseEntity.ok().body(
                                new ApiResponse(
                                        true,
                                        "Successfully uploaded the employee's picture with name: " + employee.getName(),
                                        fileUploadResponse
                                )
                        );

                    }else {
                        return ResponseEntity.status(500).body(
                                new ApiResponse(
                                        false,
                                        "Failed to delete the already existing file"
                                )
                        );
                    }

                }

                // TODO: 5/27/2023  Finished the file upload

            }else {
                return ResponseEntity.status(500).body(
                        new ApiResponse(
                                false,
                                "The employee with id: " + employeeId + " does not exists"
                        )
                );
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ApiResponse(
                            false ,
                            e.getMessage()
                    )
            );
        }
    }

    @Override
    public ResponseEntity<ApiResponse> createManyEmployees(List<CreateEmployeeDTO> createEmployeeDTOS) {
        try {
            List<Employee> employeeList = new ArrayList<Employee>();
            int i = 0;
            while (i < createEmployeeDTOS.size()){
                if(departmentsRepository.existsById(createEmployeeDTOS.get(i).getDepartmentId())){
                    Department department = departmentsRepository.findById(createEmployeeDTOS.get(i).getDepartmentId()).get();
                    Employee employee = new Employee(
                            createEmployeeDTOS.get(i).getName(),
                            createEmployeeDTOS.get(i).getCode(),
                            createEmployeeDTOS.get(i).getJobTitle(),
                            createEmployeeDTOS.get(i).getDob(),
                            department
                    );
                    employeeList.add(employee);
                    i++;
                }else {
                    return ResponseEntity.status(404).body(
                            new ApiResponse(
                                    false,
                                    "The Department with id: " + createEmployeeDTOS.get(i).getDepartmentId() + " does not exist"
                            )
                    );
                }
            }
            employeeRepository.saveAll(employeeList);
            return ResponseEntity.ok().body(
                    new ApiResponse(
                            true,
                            "Successfully created all the employees",
                            employeeList
                    )
            );
        }catch (Exception e){
            return ResponseEntity.status(500).body(
                    new ApiResponse(
                            false,
                            e.getMessage()
                    )
            );
        }
    }

    public void employeeMapper(Employee employee , UpdateEmployeeDTO updateEmployeeDTO , Department department){
        employee.setName(updateEmployeeDTO.getName());
        employee.setCode(updateEmployeeDTO.getCode());
        employee.setJobTitle(updateEmployeeDTO.getJobTitle());
        employee.setDob(updateEmployeeDTO.getDob());
        employee.setDepartment(department);
    }
    @Override
    @Transactional
    public ResponseEntity<ApiResponse> updateEmployee(Long employeeId, UpdateEmployeeDTO updateEmployeeDTO) {
        if(employeeRepository.existsById(employeeId)){
            if(departmentsRepository.existsById(updateEmployeeDTO.getDepartmentId())){
                try {
              Department department = departmentsRepository.findById(updateEmployeeDTO.getDepartmentId()).get();
              Employee employee = employeeRepository.findById(employeeId).get();
              employeeMapper(employee , updateEmployeeDTO , department);
              return ResponseEntity.ok().body(
                      new ApiResponse(
                              true,
                              "Successfully updated the employee with id: " + employeeId,
                              employee
                      )
              );
                }catch (Exception e){
                    return ResponseEntity.status(500).body(
                            new ApiResponse(
                                    false,
                                    e.getMessage()
                            )
                    );
                }
            }else {
                return ResponseEntity.status(404).body(
                        new ApiResponse(
                                false,
                                "The Department with id: " + updateEmployeeDTO.getDepartmentId() + " does not exist"
                        )
                );
            }
        }else {
            return ResponseEntity.status(404).body(
                    new ApiResponse(
                            false,
                            "The employee with id: " + employeeId + " does not exist"
                    )
            );
        }
    }

    @Override
    public ResponseEntity<ApiResponse> deleteEmployee(Long employeeId) {
        if(employeeRepository.existsById(employeeId)){
            try {
                Employee employee = employeeRepository.findById(employeeId).get();
                employeeRepository.deleteById(employeeId);
                return ResponseEntity.ok().body(
                        new ApiResponse(
                                true,
                                "Successfully deleted the employee with id: " + employee,
                                employee
                        )
                );
            }catch (Exception e){
                return ResponseEntity.status(500).body(
                        new ApiResponse(
                                false,
                                e.getMessage()
                        )
                );
            }
        }else {
            return ResponseEntity.status(404).body(
                    new ApiResponse(
                            false,
                            "The employee with id: " + employeeId + " does not exist"
                    )
            );
        }
    }
}
