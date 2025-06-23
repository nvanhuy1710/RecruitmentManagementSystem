package com.app.homeworkoutapplication.module.applicant.service.eventconsumer;

import com.app.homeworkoutapplication.entity.enumeration.ApplicantStatus;
import com.app.homeworkoutapplication.module.applicant.dto.event.ApplicantAcceptEvent;
import com.app.homeworkoutapplication.module.applicant.dto.event.ApplicantCreatedEvent;
import com.app.homeworkoutapplication.module.applicant.dto.event.ApplicantRejectEvent;
import com.app.homeworkoutapplication.module.applicant.service.CaculateApplicantService;
import com.app.homeworkoutapplication.module.mail.service.MailService;
import com.app.homeworkoutapplication.module.user.service.QueryUserService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class ApplicantEventConsumer {

    private final CaculateApplicantService caculateApplicantService;

    private final QueryUserService queryUserService;

    private final MailService mailService;

    public ApplicantEventConsumer(CaculateApplicantService caculateApplicantService, QueryUserService queryUserService, MailService mailService) {
        this.caculateApplicantService = caculateApplicantService;
        this.queryUserService = queryUserService;
        this.mailService = mailService;
    }

    @Async("eventTaskExecutor")
    @TransactionalEventListener
    public void handleEvent(ApplicantCreatedEvent event) {
        caculateApplicantService.caculateMatchScoreByApplicantId(event.getEventData().getId());
    }

    @Async("eventTaskExecutor")
    @TransactionalEventListener
    public void handleEvent(ApplicantAcceptEvent event) {
        mailService.sendReviewApplicantResult(queryUserService.findById(event.getEventData().getUserId()), ApplicantStatus.ACCEPTED);
    }

    @Async("eventTaskExecutor")
    @TransactionalEventListener
    public void handleEvent(ApplicantRejectEvent event) {
        mailService.sendReviewApplicantResult(queryUserService.findById(event.getEventData().getUserId()), ApplicantStatus.DECLINED);
    }
}
