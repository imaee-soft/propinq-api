package com.imaee.propinq.reports.controllers.implementations;

import com.imaee.propinq.reports.controllers.interfaces.IReportsController;
import com.imaee.propinq.reports.services.interfaces.IMetabaseEmbedService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
public class ReportsController implements IReportsController {

    private final IMetabaseEmbedService metabaseEmbedService;

    @Override
    public Map<String, String> getMetabaseEmbedUrl(String type, Long resourceId, Map<String, String> allParams) {
        allParams.remove("type");
        allParams.remove("resourceId");
        Map<String, Object> filters = new HashMap<>(allParams);
        String url = metabaseEmbedService.getEmbedUrl(type, resourceId, filters);
        return Map.of("iframeUrl", url);
    }
}
