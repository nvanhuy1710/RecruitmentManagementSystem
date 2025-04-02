package com.app.homeworkoutapplication.module.user.controller;

import com.app.homeworkoutapplication.module.user.dto.User;
import com.app.homeworkoutapplication.module.user.service.QueryUserService;
import com.app.homeworkoutapplication.module.user.service.criteria.UserCriteria;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;

import java.util.List;

@RestController
@RequestMapping("/public/api")
@SecurityRequirement(name = "Authentication")
public class PublicUserController {

    private final QueryUserService queryUserService;

    public PublicUserController(QueryUserService queryUserService) {
        this.queryUserService = queryUserService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getPages(@ParameterObject UserCriteria criteria, @ParameterObject Pageable pageable) {
        Page<User> page = queryUserService.findPageByCriteria(criteria, pageable);
        page.getContent().forEach(user -> user.setPassword(null));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/users/all")
    public ResponseEntity<List<User>> getAll(@ParameterObject UserCriteria criteria){
        List<User> Users = queryUserService.findListByCriteria(criteria);
        Users.forEach(user -> user.setPassword(null));
        return ResponseEntity.ok(Users);
    }

    @GetMapping("/users/count")
    public ResponseEntity<Long> count(@ParameterObject UserCriteria criteria){
        return ResponseEntity.ok(queryUserService.count(criteria));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> findById(@PathVariable("id") Long id){
        User res = queryUserService.findById(id);
        res.setPassword(null);
        return ResponseEntity.ok(res);
    }
}
