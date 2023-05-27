package com.jazzybruno.example.v1.repositories;

import com.jazzybruno.example.v1.models.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentsRepository extends JpaRepository<Department, Long> {
}