package com.app.homeworkoutapplication.module.applicant.service.eventconsumer;

import com.app.homeworkoutapplication.module.applicant.dto.event.ApplicantCreatedEvent;
import com.app.homeworkoutapplication.module.applicant.service.CaculateApplicantService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class ApplicantEventConsumer {

    private final CaculateApplicantService caculateApplicantService;

    public ApplicantEventConsumer(CaculateApplicantService caculateApplicantService) {
        this.caculateApplicantService = caculateApplicantService;
    }

    @TransactionalEventListener
    public void handleEvent(ApplicantCreatedEvent event) {
        caculateApplicantService.caculateMatchScoreByApplicantId(event.getEventData().getId());
    }
}
