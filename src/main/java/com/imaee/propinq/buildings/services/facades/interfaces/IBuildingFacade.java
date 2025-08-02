package com.imaee.propinq.buildings.services.facades.interfaces;

import com.imaee.propinq.buildings.controllers.requests.CreateBuildingRequest;
import com.imaee.propinq.buildings.controllers.requests.UpdateBuildingRequest;
import com.imaee.propinq.buildings.data.models.Building;
import com.imaee.propinq.shared.data.models.Image;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface IBuildingFacade {

    void validateBuildingImages(MultipartFile[] imageFiles);

    void throwExceptionIfBuildingExistsByName(CreateBuildingRequest request);

    List<String> getImagesURLs(Building building);

    List<String> getImagesURLs(List<Image> images);

}
