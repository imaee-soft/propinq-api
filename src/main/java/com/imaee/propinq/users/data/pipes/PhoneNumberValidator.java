package com.imaee.propinq.users.data.pipes;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements
        ConstraintValidator<com.imaee.propinq.users.data.pipes.PhoneNumber, String>{
    private String isoCode;

    @Override
    public void initialize(com.imaee.propinq.users.data.pipes.PhoneNumber phoneNumberConstraint) {
        this.isoCode = phoneNumberConstraint.isoCode();
    }

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext constraintValidatorContext) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return true;
        }
      
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

        try{
            PhoneNumber arNumberProto = phoneUtil.parse(phoneNumber, isoCode);
            return phoneUtil.isValidNumberForRegion(arNumberProto, isoCode);
        } catch (NumberParseException e) { return false; }
    }

}
