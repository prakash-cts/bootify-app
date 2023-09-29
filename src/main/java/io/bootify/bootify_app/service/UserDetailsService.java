package io.bootify.bootify_app.service;

import io.bootify.bootify_app.domain.Department;
import io.bootify.bootify_app.domain.UserDetails;
import io.bootify.bootify_app.model.UserDetailsDTO;
import io.bootify.bootify_app.repos.DepartmentRepository;
import io.bootify.bootify_app.repos.UserDetailsRepository;
import io.bootify.bootify_app.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsService {

    private final UserDetailsRepository userDetailsRepository;
    private final DepartmentRepository departmentRepository;

    public UserDetailsService(final UserDetailsRepository userDetailsRepository,
            final DepartmentRepository departmentRepository) {
        this.userDetailsRepository = userDetailsRepository;
        this.departmentRepository = departmentRepository;
    }

    public List<UserDetailsDTO> findAll() {
        final List<UserDetails> userDetailses = userDetailsRepository.findAll(Sort.by("id"));
        return userDetailses.stream()
                .map(userDetails -> mapToDTO(userDetails, new UserDetailsDTO()))
                .toList();
    }

    public UserDetailsDTO get(final Long id) {
        return userDetailsRepository.findById(id)
                .map(userDetails -> mapToDTO(userDetails, new UserDetailsDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final UserDetailsDTO userDetailsDTO) {
        final UserDetails userDetails = new UserDetails();
        mapToEntity(userDetailsDTO, userDetails);
        return userDetailsRepository.save(userDetails).getId();
    }

    public void update(final Long id, final UserDetailsDTO userDetailsDTO) {
        final UserDetails userDetails = userDetailsRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(userDetailsDTO, userDetails);
        userDetailsRepository.save(userDetails);
    }

    public void delete(final Long id) {
        userDetailsRepository.deleteById(id);
    }

    private UserDetailsDTO mapToDTO(final UserDetails userDetails,
            final UserDetailsDTO userDetailsDTO) {
        userDetailsDTO.setId(userDetails.getId());
        userDetailsDTO.setName(userDetails.getName());
        userDetailsDTO.setEmail(userDetails.getEmail());
        userDetailsDTO.setDepartment(userDetails.getDepartment() == null ? null : userDetails.getDepartment().getId());
        return userDetailsDTO;
    }

    private UserDetails mapToEntity(final UserDetailsDTO userDetailsDTO,
            final UserDetails userDetails) {
        userDetails.setName(userDetailsDTO.getName());
        userDetails.setEmail(userDetailsDTO.getEmail());
        final Department department = userDetailsDTO.getDepartment() == null ? null : departmentRepository.findById(userDetailsDTO.getDepartment())
                .orElseThrow(() -> new NotFoundException("department not found"));
        userDetails.setDepartment(department);
        return userDetails;
    }

}
