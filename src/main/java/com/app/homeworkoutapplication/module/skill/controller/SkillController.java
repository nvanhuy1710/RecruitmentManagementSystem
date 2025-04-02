package com.app.homeworkoutapplication.module.skill.controller;

import com.app.homeworkoutapplication.module.skill.dto.Skill;
import com.app.homeworkoutapplication.module.skill.service.QuerySkillService;
import com.app.homeworkoutapplication.module.skill.service.SkillService;
import com.app.homeworkoutapplication.module.skill.service.criteria.SkillCriteria;
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
public class SkillController {

    private final SkillService skillService;

    private final QuerySkillService querySkillService;

    public SkillController(SkillService skillService, QuerySkillService querySkillService) {
        this.skillService = skillService;
        this.querySkillService = querySkillService;
    }

    @PostMapping("/skills")
    public ResponseEntity<Skill> create(@Valid @RequestBody Skill skill) throws URISyntaxException {
        Skill result = skillService.create(skill);
        return ResponseEntity.created(new URI("/api/skills/" + result.getId())).body(result);
    }

    @PutMapping("/skills/{id}")
    public ResponseEntity<Skill> update(@PathVariable("id") Long id, @Valid @RequestBody Skill skill){
        if (skill.getId() == null) skill.setId(id);
        Skill res = skillService.update(skill);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/skills")
    public ResponseEntity<List<Skill>> getSkillPages(@ParameterObject SkillCriteria criteria, @ParameterObject Pageable pageable) {
        Page<Skill> page = querySkillService.findPageByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/skills/all")
    public ResponseEntity<List<Skill>> getAllSkills(@ParameterObject SkillCriteria criteria){
        List<Skill> skills = querySkillService.findListByCriteria(criteria);
        return ResponseEntity.ok(skills);
    }

    @GetMapping("/skills/{id}")
    public ResponseEntity<Skill> getById(@PathVariable("id") Long id){
        Skill res = querySkillService.getById(id);
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/skills/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        skillService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
