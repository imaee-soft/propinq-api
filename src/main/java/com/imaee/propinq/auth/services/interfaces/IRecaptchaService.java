package com.imaee.propinq.auth.services.interfaces;

public interface IRecaptchaService {
    public boolean validateToken(String token);
}
