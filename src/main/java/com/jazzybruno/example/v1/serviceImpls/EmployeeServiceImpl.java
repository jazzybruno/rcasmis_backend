package com.jazzybruno.example.v1.serviceImpls;

import com.jazzybruno.example.v1.dto.requests.CreateEmployeeDTO;
import com.jazzybruno.example.v1.dto.requests.UpdateEmployeeDTO;
import com.jazzybruno.example.v1.models.Department;
import com.jazzybruno.example.v1.models.Employee;
import com.jazzybruno.example.v1.payload.ApiResponse;
import com.jazzybruno.example.v1.repositories.DepartmentsRepository;
import com.jazzybruno.example.v1.repositories.EmployeeRepository;
import com.jazzybruno.example.v1.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
