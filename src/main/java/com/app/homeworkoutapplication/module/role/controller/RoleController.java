package com.app.homeworkoutapplication.module.role.controller;

import com.app.homeworkoutapplication.module.role.dto.Role;
import com.app.homeworkoutapplication.module.role.service.QueryRoleService;
import com.app.homeworkoutapplication.module.role.service.RoleService;
import com.app.homeworkoutapplication.module.role.service.criteria.RoleCriteria;
import com.app.homeworkoutapplication.security.AuthorityConstant;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "Authentication")
public class RoleController {

    private final RoleService roleService;

    private final QueryRoleService queryRoleService;

    public RoleController(RoleService roleService, QueryRoleService queryRoleService) {
        this.roleService = roleService;
        this.queryRoleService = queryRoleService;
    }

    @PostMapping("/roles")
    @PreAuthorize("hasAuthority(\"" + AuthorityConstant.ADMIN + "\")")
    public ResponseEntity<Role> create(@Valid @RequestBody Role role) throws URISyntaxException {
        Role result = roleService.create(role);
        return ResponseEntity.created(new URI("/api/roles/" + result.getId())).body(result);
    }

    @PutMapping("/roles/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthorityConstant.ADMIN + "\")")
    public ResponseEntity<Role> update(@PathVariable("id") Long id, @Valid @RequestBody Role role){
        if (role.getId() == null) role.setId(id);
        Role res = roleService.update(role);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/roles")
    @PreAuthorize("hasAuthority(\"" + AuthorityConstant.ADMIN + "\")")
    public ResponseEntity<List<Role>> getRolePages(@ParameterObject RoleCriteria criteria, @ParameterObject Pageable pageable) {
        Page<Role> page = queryRoleService.findPageByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/roles/all")
    @PreAuthorize("hasAuthority(\"" + AuthorityConstant.ADMIN + "\")")
    public ResponseEntity<List<Role>> getAllRoles(@ParameterObject RoleCriteria criteria){
        List<Role> roles = queryRoleService.findListByCriteria(criteria);
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/roles/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthorityConstant.ADMIN + "\")")
    public ResponseEntity<Role> getById(@PathVariable("id") Long id){
        Role res = queryRoleService.getById(id);
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/roles/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthorityConstant.ADMIN + "\")")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        roleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
