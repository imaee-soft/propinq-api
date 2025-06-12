package com.imaee.propinq.users.data.pipes;

import com.imaee.propinq.users.utils.PhoneNumberConversion;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class PhoneNumberConverter implements AttributeConverter<String, String> {

    @Override
    public String convertToDatabaseColumn(String phoneNumber) {
        return PhoneNumberConversion.convertPhoneNumber(phoneNumber);
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return dbData;
    }
}
