package com.app.homeworkoutapplication.module.applicant.dto.event;

import com.app.homeworkoutapplication.common.Event;
import com.app.homeworkoutapplication.module.applicant.dto.Applicant;

public class ApplicantCreatedEvent extends Event<Applicant> {

    public ApplicantCreatedEvent(Object source, Applicant applicant) {
        super(source, applicant);
    }

    @Override
    public String getName() {
        return "applicant.created.event";
    }

    @Override
    public String getType() {
        return "article";
    }
}
