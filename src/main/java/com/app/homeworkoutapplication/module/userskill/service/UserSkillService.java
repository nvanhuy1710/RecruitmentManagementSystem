package com.app.homeworkoutapplication.module.userskill.service;

import com.app.homeworkoutapplication.entity.UserSkillEntity;
import com.app.homeworkoutapplication.module.userskill.dto.UserSkillDTO;
import com.app.homeworkoutapplication.repository.UserSkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserSkillService {
    private final UserSkillRepository userSkillRepository;

    public List<UserSkillDTO> getAllUserSkills() {
        return userSkillRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public UserSkillDTO getUserSkillById(Long id) {
        return userSkillRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public List<UserSkillDTO> getUserSkillsByUserId(Long userId) {
        return userSkillRepository.findByUserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public UserSkillDTO createUserSkill(UserSkillDTO userSkillDTO) {
        UserSkillEntity entity = convertToEntity(userSkillDTO);
        return convertToDTO(userSkillRepository.save(entity));
    }

    public UserSkillDTO updateUserSkill(Long id, UserSkillDTO userSkillDTO) {
        if (userSkillRepository.existsById(id)) {
            UserSkillEntity entity = convertToEntity(userSkillDTO);
            entity.setId(id);
            return convertToDTO(userSkillRepository.save(entity));
        }
        return null;
    }

    public void deleteUserSkill(Long id) {
        userSkillRepository.deleteById(id);
    }

    private UserSkillDTO convertToDTO(UserSkillEntity entity) {
        UserSkillDTO dto = new UserSkillDTO();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUserId());
        dto.setSkillId(entity.getSkillId());
        dto.setLevel(entity.getLevel());
        return dto;
    }

    private UserSkillEntity convertToEntity(UserSkillDTO dto) {
        UserSkillEntity entity = new UserSkillEntity();
        entity.setUserId(dto.getUserId());
        entity.setSkillId(dto.getSkillId());
        entity.setLevel(dto.getLevel());
        return entity;
    }
} 