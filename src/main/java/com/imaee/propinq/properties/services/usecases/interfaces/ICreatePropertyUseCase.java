package com.imaee.propinq.properties.services.usecases.interfaces;

import com.imaee.propinq.properties.controllers.requests.CreatePropertyRequest;
import org.springframework.web.multipart.MultipartFile;

public interface ICreatePropertyUseCase {
    void createProperty(CreatePropertyRequest request, MultipartFile[] imageFiles);
}