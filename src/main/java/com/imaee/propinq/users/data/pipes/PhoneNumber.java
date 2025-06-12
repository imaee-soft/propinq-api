package com.imaee.propinq.users.data.pipes;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = phoneNumberValidator.class)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface PhoneNumber {

   String isoCode() default "AR";
   String message() default  "Phone number is not valid";
   Class<?>[] groups() default{};
   Class<? extends Payload>[] payload() default {};

   @Documented
   @Retention(RetentionPolicy.RUNTIME)
   @Target({ElementType.FIELD})
   @interface List {
         PhoneNumber[] value();
   }
}
