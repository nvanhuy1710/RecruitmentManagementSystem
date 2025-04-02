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

    private final UserSkillService user_skillService;

    private final QueryUserSkillService queryUserSkillService;

    public UserSkillController(UserSkillService user_skillService, QueryUserSkillService queryUserSkillService) {
        this.user_skillService = user_skillService;
        this.queryUserSkillService = queryUserSkillService;
    }

    @PostMapping("/user_skills")
    @PreAuthorize("hasAuthority(\"" + AuthorityConstant.ADMIN + "\")")
    public ResponseEntity<UserSkill> create(@Valid @RequestBody UserSkill user_skill) throws URISyntaxException {
        UserSkill result = user_skillService.create(user_skill);
        return ResponseEntity.created(new URI("/api/user_skills/" + result.getId())).body(result);
    }

    @PutMapping("/user_skills/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthorityConstant.ADMIN + "\")")
    public ResponseEntity<UserSkill> update(@PathVariable("id") Long id, @Valid @RequestBody UserSkill user_skill){
        if (user_skill.getId() == null) user_skill.setId(id);
        UserSkill res = user_skillService.update(user_skill);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/user_skills")
    @PreAuthorize("hasAuthority(\"" + AuthorityConstant.ADMIN + "\")")
    public ResponseEntity<List<UserSkill>> getUserSkillPages(@ParameterObject UserSkillCriteria criteria, @ParameterObject Pageable pageable) {
        Page<UserSkill> page = queryUserSkillService.findPageByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/user_skills/all")
    @PreAuthorize("hasAuthority(\"" + AuthorityConstant.ADMIN + "\")")
    public ResponseEntity<List<UserSkill>> getAllUserSkills(@ParameterObject UserSkillCriteria criteria){
        List<UserSkill> user_skills = queryUserSkillService.findListByCriteria(criteria);
        return ResponseEntity.ok(user_skills);
    }

    @GetMapping("/user_skills/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthorityConstant.ADMIN + "\")")
    public ResponseEntity<UserSkill> getById(@PathVariable("id") Long id){
        UserSkill res = queryUserSkillService.getById(id);
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/user_skills/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthorityConstant.ADMIN + "\")")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        user_skillService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
