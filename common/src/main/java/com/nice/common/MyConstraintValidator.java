package com.nice.common;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MyConstraintValidator implements ConstraintValidator<CheckBools, Agent> {


    @Override
    public void initialize(CheckBools constraintAnnotation) {

    }

    @Override
    public boolean isValid(Agent value, ConstraintValidatorContext context) {
        if (!value.isOnCall && (value.isVoiceRecorded || value.isScreenRecorded))
            return false;
        else return true;
    }
}
