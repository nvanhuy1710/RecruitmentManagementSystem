package com.app.homeworkoutapplication.module.user.controller;

import com.app.homeworkoutapplication.module.user.dto.UpdateUserRole;
import com.app.homeworkoutapplication.module.user.dto.User;
import com.app.homeworkoutapplication.module.user.service.QueryUserService;
import com.app.homeworkoutapplication.module.user.service.UserService;
import com.app.homeworkoutapplication.security.AuthorityConstant;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "Authentication")
public class UserController {
    
    private final UserService userService;
    
    private final QueryUserService queryUserService;

    public UserController(UserService userService, QueryUserService queryUserService) {
        this.userService = userService;
        this.queryUserService = queryUserService;
    }

    @PostMapping("/users")
    @PreAuthorize("hasAuthority(\"" + AuthorityConstant.ADMIN + "\")")
    public ResponseEntity<User> create(@Valid @RequestBody User user) throws URISyntaxException {
        User result = userService.save(user);
        return ResponseEntity.created(new URI("/api/users/" + result.getId())).body(result);
    }

    @PutMapping("/users/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthorityConstant.ADMIN + "\")")
    public ResponseEntity<User> update(
            @Parameter(description = "ID of the user to be updated", required = true)
            @PathVariable("id") Long id,
            @Valid
            @RequestBody User user){
        if (user.getId() == null) user.setId(id);
        User res = userService.save(user);
        return ResponseEntity.ok(res);
    }

    @PutMapping("/users/{id}/role")
    @PreAuthorize("hasAuthority(\"" + AuthorityConstant.ADMIN + "\")")
    public ResponseEntity<User> updateRole(
            @Parameter(description = "ID of the user to be updated", required = true)
            @PathVariable("id") Long id,
            @RequestBody UpdateUserRole updateUserRole){
        User res = userService.updateRole(id, updateUserRole.getRoleName());
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthorityConstant.ADMIN + "\")")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
