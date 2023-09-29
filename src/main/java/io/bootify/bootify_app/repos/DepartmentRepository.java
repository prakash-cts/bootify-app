package io.bootify.bootify_app.repos;

import io.bootify.bootify_app.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
