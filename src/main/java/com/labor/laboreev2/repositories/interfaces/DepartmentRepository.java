package com.labor.laboreev2.repositories.interfaces;

import com.labor.laboreev2.models.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository {
    Department save(Department department);
    Optional<Department> findById(Long id);
    List<Department> findAll();
    void deleteById(Long id);
}
