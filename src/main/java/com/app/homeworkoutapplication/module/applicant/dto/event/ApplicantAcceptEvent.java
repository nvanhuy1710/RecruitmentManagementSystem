package com.app.homeworkoutapplication.module.applicant.dto.event;

import com.app.homeworkoutapplication.common.Event;
import com.app.homeworkoutapplication.module.applicant.dto.Applicant;

public class ApplicantAcceptEvent extends Event<Applicant> {

    public ApplicantAcceptEvent(Object source, Applicant applicant) {
        super(source, applicant);
    }

    @Override
    public String getName() {
        return "applicant.accept.event";
    }

    @Override
    public String getType() {
        return "applicant";
    }
}
