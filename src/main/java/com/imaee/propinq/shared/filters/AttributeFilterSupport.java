package com.imaee.propinq.shared.filters;

import com.imaee.propinq.properties.controllers.requests.AttributeFilterRequest;
import com.imaee.propinq.properties.controllers.requests.PropertyFilterRequest;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public final class AttributeFilterSupport {
    private AttributeFilterSupport() {}

    public static boolean hasAttributeFilters(PropertyFilterRequest filter) {
        if (filter == null || filter.getAttributes() == null) return false;
        for (Field f : AttributeFilterRequest.class.getDeclaredFields()) {
            try {
                f.setAccessible(true);
                if (f.get(filter.getAttributes()) != null) return true;
            } catch (IllegalAccessException ignored) {}
        }
        return false;
    }

    public static boolean hasAttributeValues(AttributeFilterRequest attributes) {
        if (attributes == null) return false;
        for (Field f : AttributeFilterRequest.class.getDeclaredFields()) {
            try {
                f.setAccessible(true);
                if (f.get(attributes) != null) return true;
            } catch (IllegalAccessException ignored) {}
        }
        return false;
    }

    public static Set<String> handledFieldNames() {
        return Arrays.stream(AttributeFilterRequest.class.getDeclaredFields())
                .map(Field::getName)
                .collect(Collectors.toSet());
    }
}
