package com.imaee.propinq.rents.services.usecases.interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface IUpdateContractUseCase {

    void updateContract(UUID rentId, MultipartFile contract);
}
