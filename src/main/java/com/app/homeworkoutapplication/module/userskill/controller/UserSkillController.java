package com.app.homeworkoutapplication.module.userskill.controller;

import com.app.homeworkoutapplication.module.userskill.dto.UserSkill;
import com.app.homeworkoutapplication.module.userskill.service.QueryUserSkillService;
import com.app.homeworkoutapplication.module.userskill.service.UserSkillService;
import com.app.homeworkoutapplication.module.userskill.service.criteria.UserSkillCriteria;
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
public class UserSkillController {

    private final UserSkillService userSkillService;
    private final QueryUserSkillService queryUserSkillService;

    public UserSkillController(UserSkillService userSkillService, QueryUserSkillService queryUserSkillService) {
        this.userSkillService = userSkillService;
        this.queryUserSkillService = queryUserSkillService;
    }

    @PostMapping("/user-skills")
    @PreAuthorize("hasAuthority(\"" + AuthorityConstant.ADMIN + "\")")
    public ResponseEntity<UserSkill> create(@Valid @RequestBody UserSkill userSkill) throws URISyntaxException {
        UserSkill result = userSkillService.create(userSkill);
        return ResponseEntity.created(new URI("/api/user-skills/" + result.getId())).body(result);
    }

    @PutMapping("/user-skills/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthorityConstant.ADMIN + "\")")
    public ResponseEntity<UserSkill> update(@PathVariable("id") Long id, @Valid @RequestBody UserSkill userSkill) {
        if (userSkill.getId() == null) userSkill.setId(id);
        UserSkill res = userSkillService.update(userSkill);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/user-skills")
    @PreAuthorize("hasAuthority(\"" + AuthorityConstant.ADMIN + "\")")
    public ResponseEntity<List<UserSkill>> getUserSkillPages(@ParameterObject UserSkillCriteria criteria, @ParameterObject Pageable pageable) {
        Page<UserSkill> page = queryUserSkillService.findPageByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/user-skills/all")
    @PreAuthorize("hasAuthority(\"" + AuthorityConstant.ADMIN + "\")")
    public ResponseEntity<List<UserSkill>> getAllUserSkills(@ParameterObject UserSkillCriteria criteria) {
        List<UserSkill> userSkills = queryUserSkillService.findListByCriteria(criteria);
        return ResponseEntity.ok(userSkills);
    }

    @GetMapping("/user-skills/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthorityConstant.ADMIN + "\")")
    public ResponseEntity<UserSkill> getById(@PathVariable("id") Long id) {
        UserSkill res = queryUserSkillService.getById(id);
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/user-skills/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthorityConstant.ADMIN + "\")")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        userSkillService.delete(id);
        return ResponseEntity.noContent().build();
    }
} 