package com.app.homeworkoutapplication.module.account.service;

import com.app.homeworkoutapplication.module.account.dto.RegisterRequest;
import com.app.homeworkoutapplication.module.company.dto.Company;
import com.app.homeworkoutapplication.module.user.dto.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AccountService {

    User register(RegisterRequest registerRequest, String industrys);

    void activateUser(String email);

    User updateAccount(User user);

    void followCompany(Long companyId);

    void unFollowCompany(Long companyId);

    List<User> findListFollowByCompanyId(Long companyId);

    List<Company> getFollowedCompanies();

    void updateAvatar(MultipartFile file);

    void updatePassword(String newPass);

    void forgotPassword(String username);
}
