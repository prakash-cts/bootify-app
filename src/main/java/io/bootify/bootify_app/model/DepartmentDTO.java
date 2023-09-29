package io.bootify.bootify_app.model;

import jakarta.validation.constraints.Size;


public class DepartmentDTO {

    private Long id;

    @Size(max = 255)
    private String department;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(final String department) {
        this.department = department;
    }

}
