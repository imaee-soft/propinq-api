package com.imaee.propinq.properties.services.facades.implementations;

import com.imaee.propinq.properties.data.models.Property;
import com.imaee.propinq.properties.services.facades.interfaces.IPropertyFacade;
import com.imaee.propinq.shared.data.models.Image;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class PropertyFacade implements IPropertyFacade {

    @Override
    public List<String> getImagesURLs(Property property) {
        return getImagesURLs(property.getImages());
    }

    @Override
    public List<String> getImagesURLs(List<Image> images) {
        return images.stream()
                .map(Image::getUrl)
                .toList();
    }
}
