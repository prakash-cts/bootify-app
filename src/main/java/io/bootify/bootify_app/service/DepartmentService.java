package io.bootify.bootify_app.service;

import io.bootify.bootify_app.domain.Department;
import io.bootify.bootify_app.domain.UserDetails;
import io.bootify.bootify_app.model.DepartmentDTO;
import io.bootify.bootify_app.repos.DepartmentRepository;
import io.bootify.bootify_app.repos.UserDetailsRepository;
import io.bootify.bootify_app.util.NotFoundException;
import io.bootify.bootify_app.util.WebUtils;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final UserDetailsRepository userDetailsRepository;

    public DepartmentService(final DepartmentRepository departmentRepository,
            final UserDetailsRepository userDetailsRepository) {
        this.departmentRepository = departmentRepository;
        this.userDetailsRepository = userDetailsRepository;
    }

    public List<DepartmentDTO> findAll() {
        final List<Department> departments = departmentRepository.findAll(Sort.by("id"));
        return departments.stream()
                .map(department -> mapToDTO(department, new DepartmentDTO()))
                .toList();
    }

    public DepartmentDTO get(final Long id) {
        return departmentRepository.findById(id)
                .map(department -> mapToDTO(department, new DepartmentDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final DepartmentDTO departmentDTO) {
        final Department department = new Department();
        mapToEntity(departmentDTO, department);
        return departmentRepository.save(department).getId();
    }

    public void update(final Long id, final DepartmentDTO departmentDTO) {
        final Department department = departmentRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(departmentDTO, department);
        departmentRepository.save(department);
    }

    public void delete(final Long id) {
        departmentRepository.deleteById(id);
    }

    private DepartmentDTO mapToDTO(final Department department, final DepartmentDTO departmentDTO) {
        departmentDTO.setId(department.getId());
        departmentDTO.setDepartment(department.getDepartment());
        return departmentDTO;
    }

    private Department mapToEntity(final DepartmentDTO departmentDTO, final Department department) {
        department.setDepartment(departmentDTO.getDepartment());
        return department;
    }

    public String getReferencedWarning(final Long id) {
        final Department department = departmentRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final UserDetails departmentUserDetails = userDetailsRepository.findFirstByDepartment(department);
        if (departmentUserDetails != null) {
            return WebUtils.getMessage("department.userDetails.department.referenced", departmentUserDetails.getId());
        }
        return null;
    }

}
