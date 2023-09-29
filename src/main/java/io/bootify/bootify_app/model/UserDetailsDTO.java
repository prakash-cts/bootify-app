package io.bootify.bootify_app.model;

import jakarta.validation.constraints.Size;


public class UserDetailsDTO {

    private Long id;

    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String email;

    private Long department;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public Long getDepartment() {
        return department;
    }

    public void setDepartment(final Long department) {
        this.department = department;
    }

}
