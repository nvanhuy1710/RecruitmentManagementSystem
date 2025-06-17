package com.app.homeworkoutapplication.module.usernotification.service.impl;

import com.app.homeworkoutapplication.entity.UserEntity_;
import com.app.homeworkoutapplication.entity.UserNotificationEntity;
import com.app.homeworkoutapplication.entity.UserNotificationEntity_;
import com.app.homeworkoutapplication.entity.mapper.UserNotificationMapper;
import com.app.homeworkoutapplication.module.usernotification.dto.UserNotification;
import com.app.homeworkoutapplication.module.usernotification.service.QueryUserNotificationService;
import com.app.homeworkoutapplication.module.usernotification.service.criteria.UserNotificationCriteria;
import com.app.homeworkoutapplication.repository.UserNotificationRepository;
import com.app.homeworkoutapplication.web.rest.error.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.LongFilter;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true, noRollbackFor = Exception.class)
public class QueryUserNotificationServiceImpl extends QueryService<UserNotificationEntity> implements QueryUserNotificationService {

    private final UserNotificationRepository userNotificationRepository;

    private final UserNotificationMapper userNotificationMapper;

    public QueryUserNotificationServiceImpl(UserNotificationRepository userNotificationRepository, UserNotificationMapper userNotificationMapper) {
        this.userNotificationRepository = userNotificationRepository;
        this.userNotificationMapper = userNotificationMapper;
    }

    @Override
    public List<UserNotification> findListByCriteria(UserNotificationCriteria criteria) {
        return userNotificationRepository.findAll(createSpecification(criteria)).stream().map(
                entity -> {
                    UserNotification notification = userNotificationMapper.toDto(entity);
                    fetchData(entity, notification);
                    return notification;
                }
        ).toList();
    }

    @Override
    public Page<UserNotification> findByUserId(Long userId, Pageable pageable) {
        UserNotificationCriteria criteria = new UserNotificationCriteria();
        LongFilter filter = new LongFilter();
        filter.setEquals(userId);
        criteria.setUserId(filter);
        return findPageByCriteria(criteria, pageable);
    }

    @Override
    public long countByUserId(Long userId) {
        UserNotificationCriteria criteria = new UserNotificationCriteria();
        LongFilter filter = new LongFilter();
        filter.setEquals(userId);
        criteria.setUserId(filter);

        BooleanFilter viewFilter = new BooleanFilter();
        viewFilter.setEquals(false);
        criteria.setViewed(viewFilter);

        return userNotificationRepository.count(createSpecification(criteria));
    }
    @Override
    public Page<UserNotification> findPageByCriteria(UserNotificationCriteria criteria, Pageable pageable) {
        Page<UserNotificationEntity> page =  userNotificationRepository.findAll(createSpecification(criteria), pageable);
        return new PageImpl<>(
                page.getContent().stream().map(entity -> {
                    UserNotification notification = userNotificationMapper.toDto(entity);
                    fetchData(entity, notification);
                    return notification;
                }).toList(),
                pageable,
                page.getTotalElements()
        );
    }

    @Override
    public UserNotification getById(Long id) {
        Optional<UserNotificationEntity> entity = userNotificationRepository.findById(id);
        if (entity.isEmpty()) {
            throw new NotFoundException("Not found notification by id " + id);
        }
        UserNotification notification = userNotificationMapper.toDto(entity.get());
        fetchData(entity.get(), notification);
        return notification;
    }

    private Specification<UserNotificationEntity> createSpecification(UserNotificationCriteria criteria) {
        Specification<UserNotificationEntity> specification = Specification.where(null);

        if (criteria.getId() != null) {
            specification = specification.and(buildRangeSpecification(criteria.getId(), UserNotificationEntity_.id));
        }
        if (criteria.getUserId() != null) {
            specification = specification.and(buildSpecification(criteria.getUserId(),
                    notificationEntityRoot -> notificationEntityRoot.join(UserNotificationEntity_.user).get(UserEntity_.id)));
        }
        if (criteria.getViewed() != null) {
            specification = specification.and(buildSpecification(criteria.getViewed(), UserNotificationEntity_.viewed));
        }
        return specification;
    }

    private void fetchData(UserNotificationEntity entity, UserNotification notification) {}
}
