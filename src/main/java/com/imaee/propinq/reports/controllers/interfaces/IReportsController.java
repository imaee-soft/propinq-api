package com.imaee.propinq.reports.controllers.interfaces;

import com.imaee.propinq.config.utils.Endpoints;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@RequestMapping(Endpoints.API_V1 + "/reports")
@Tag(
        name = "reports",
        description = "Operations for managing and querying reports."
)
public interface IReportsController {

    @Operation(
            summary = "Get secure Metabase embed URL",
            description = "Returns a secure URL (iframe) for embedding Metabase questions or dashboards, with support for dynamic filters."
    )
    @GetMapping("/metabase/embed-url")
    Map<String, String> getMetabaseEmbedUrl(
            @Parameter(description = "Embed type: 'question' or 'dashboard'", example = "question")
            @RequestParam String type,
            @Parameter(description = "Metabase question or dashboard ID", example = "20")
            @RequestParam Long resourceId,
            @Parameter(description = "Filters for dashboards or questions (e.g., owner_id, date, etc.)")
            @RequestParam Map<String, String> allParams
    );
}

