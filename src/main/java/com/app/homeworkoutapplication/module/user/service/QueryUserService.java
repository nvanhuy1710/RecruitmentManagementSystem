package com.app.homeworkoutapplication.module.user.service;

import com.app.homeworkoutapplication.module.company.dto.Company;
import com.app.homeworkoutapplication.module.user.dto.User;
import com.app.homeworkoutapplication.module.user.service.criteria.UserCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QueryUserService {

    List<User> findListByCriteria(UserCriteria criteria);

    List<User> findListEmployee();

    List<User> findListFollowByCompanyId(Long id);

    List<Company> getFollowedCompanies(Long userId);

    Long count(UserCriteria criteria);

    Page<User> findPageByCriteria(UserCriteria criteria, Pageable pageable);

    User findById(Long id);

    User getByUsername(String username);

    User getByEmail(String email);
}
