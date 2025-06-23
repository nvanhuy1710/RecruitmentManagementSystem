package com.app.homeworkoutapplication.module.applicant.dto.event;

import com.app.homeworkoutapplication.common.Event;
import com.app.homeworkoutapplication.module.applicant.dto.Applicant;

public class ApplicantRejectEvent extends Event<Applicant> {

    public ApplicantRejectEvent(Object source, Applicant applicant) {
        super(source, applicant);
    }

    @Override
    public String getName() {
        return "applicant.reject.event";
    }

    @Override
    public String getType() {
        return "applicant";
    }
}
