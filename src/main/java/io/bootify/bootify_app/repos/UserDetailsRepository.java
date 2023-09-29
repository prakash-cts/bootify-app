package io.bootify.bootify_app.repos;

import io.bootify.bootify_app.domain.Department;
import io.bootify.bootify_app.domain.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {

    UserDetails findFirstByDepartment(Department department);

}
