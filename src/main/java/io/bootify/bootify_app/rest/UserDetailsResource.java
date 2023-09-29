package io.bootify.bootify_app.rest;

import io.bootify.bootify_app.model.UserDetailsDTO;
import io.bootify.bootify_app.service.UserDetailsService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/userDetailss", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserDetailsResource {

    private final UserDetailsService userDetailsService;

    public UserDetailsResource(final UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping
    public ResponseEntity<List<UserDetailsDTO>> getAllUserDetailss() {
        return ResponseEntity.ok(userDetailsService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDetailsDTO> getUserDetails(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(userDetailsService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createUserDetails(
            @RequestBody @Valid final UserDetailsDTO userDetailsDTO) {
        final Long createdId = userDetailsService.create(userDetailsDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateUserDetails(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final UserDetailsDTO userDetailsDTO) {
        userDetailsService.update(id, userDetailsDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteUserDetails(@PathVariable(name = "id") final Long id) {
        userDetailsService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
