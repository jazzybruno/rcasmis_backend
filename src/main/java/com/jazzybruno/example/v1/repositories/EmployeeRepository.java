package com.jazzybruno.example.v1.repositories;

import com.jazzybruno.example.v1.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}