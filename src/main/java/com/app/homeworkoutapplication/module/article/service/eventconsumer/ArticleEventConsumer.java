package com.app.homeworkoutapplication.module.article.service.eventconsumer;

import com.app.homeworkoutapplication.module.article.dto.Article;
import com.app.homeworkoutapplication.module.article.dto.event.ApproveArticleEvent;
import com.app.homeworkoutapplication.module.mail.service.MailService;
import com.app.homeworkoutapplication.module.user.dto.User;
import com.app.homeworkoutapplication.module.user.service.QueryUserService;
import com.app.homeworkoutapplication.module.usernotification.dto.UserNotification;
import com.app.homeworkoutapplication.module.usernotification.service.UserNotificationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

@Component
public class ArticleEventConsumer {

    private final QueryUserService queryUserService;

    private final MailService mailService;

    private final UserNotificationService userNotificationService;

    private final ObjectMapper objectMapper;

    public ArticleEventConsumer(
            QueryUserService queryUserService,
            MailService mailService,
            UserNotificationService userNotificationService,
            ObjectMapper objectMapper
    ) {
        this.queryUserService = queryUserService;
        this.mailService = mailService;
        this.userNotificationService = userNotificationService;
        this.objectMapper = objectMapper;
    }

    @TransactionalEventListener
    public void handleEvent(ApproveArticleEvent event) {
        Article article = event.getEventData();

        List<User> followUsers = queryUserService.findListFollowByCompanyId(article.getCompanyId());

        for(User followUser : followUsers) {

            mailService.sendApprovedArticle(followUser);

            UserNotification notification = new UserNotification();
            notification.setUserId(followUser.getId());
            notification.setArticleId(article.getId());
            notification.setCompanyName(article.getCompany().getName());
            try {
                notification.setData(objectMapper.writeValueAsString(article));
            } catch (JsonProcessingException e) {
                System.out.println("Error serializing article data: " + e.getMessage());
                // Không throw exception để tránh ảnh hưởng đến luồng chính
            }
            notification.setViewed(false);

            try {
                userNotificationService.create(notification);
            } catch (Exception e) {
                System.out.println("Error creating notification for user " + followUser.getId() + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
