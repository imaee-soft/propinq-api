package com.imaee.propinq.users.utils;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.imaee.propinq.exceptions.custom_exceptions.PhoneNumberParseException;


public class PhoneNumberConversion {
    private PhoneNumberConversion(){}

    public static String convertPhoneNumber(String phoneNumber ){
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

        try{
            PhoneNumber numberProto = phoneUtil.parse(phoneNumber, "AR");
            return phoneUtil.format(numberProto, PhoneNumberFormat.E164);
        } catch (NumberParseException e) {
            throw new PhoneNumberParseException("Fallo al convertir el número de teléfono: " + phoneNumber);
        }

    }
}
