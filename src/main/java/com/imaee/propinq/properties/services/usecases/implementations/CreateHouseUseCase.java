package com.imaee.propinq.properties.services.usecases.implementations;

import com.imaee.propinq.properties.controllers.requests.CreatePropertyRequest;
import com.imaee.propinq.properties.services.usecases.interfaces.ICreatePropertyUseCase;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CreateHouseUseCase implements ICreatePropertyUseCase {

    @Override
    public void createProperty(CreatePropertyRequest request, MultipartFile[] imageFiles) {
        throw new RuntimeException("Not implemented yet");
    }
}