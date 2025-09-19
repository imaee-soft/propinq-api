package com.imaee.propinq.poi.services.implementations;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imaee.propinq.poi.data.enums.PoiType;
import com.imaee.propinq.poi.mappers.PoiMapper;
import com.imaee.propinq.poi.data.repositories.PoiBulkRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.UUID;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
public class PoiImportService {

    private final ObjectMapper mapper = new ObjectMapper();
    private final PoiBulkRepository poiBulkRepository;

    @Value("${poi.import.file:/app/out/pois.geojsonseq}")
    private String importFile;

    @Value("${poi.import.batch-size:8000}")
    private int batchSize;

    @Value("${poi.import.mode:REPLACE}") // REPLACE | UPSERT
    private String importMode;

    public PoiImportService(PoiBulkRepository poiBulkRepository) {
        this.poiBulkRepository = poiBulkRepository;
    }

    // Ahora no devuelve nada
    public long importToMySql() throws Exception {
        boolean replace = "REPLACE".equalsIgnoreCase(importMode);
        var rows = new ArrayList<PoiBulkRepository.Row>(batchSize);
        long processed = 0;

        if (replace) {
            poiBulkRepository.truncate();
        }

        try (BufferedReader br = Files.newBufferedReader(Path.of(importFile), UTF_8)) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || "{".equals(line) || "}".equals(line)) continue;

                JsonNode feat;
                try { feat = mapper.readTree(line); } catch (Exception ignore) { continue; }

                var geom = feat.get("geometry");
                if (geom == null || !"Point".equals(geom.path("type").asText())) continue;

                var coords = geom.path("coordinates");
                if (!coords.isArray() || coords.size() < 2) continue;
                double lon = coords.get(0).asDouble();
                double lat = coords.get(1).asDouble();

                var props = feat.get("properties");
                if (props == null || props.isMissingNode()) continue;

                PoiType type = PoiMapper.classifyType(props);
                if (type == null) continue;

                String name = props.hasNonNull("name") ? props.get("name").asText() : null;
                String id = UUID.randomUUID().toString();

                rows.add(new PoiBulkRepository.Row(id, type.name(), name, lat, lon));

                if (rows.size() >= batchSize) {
                    if (replace) poiBulkRepository.upsertBatch(rows); else poiBulkRepository.upsertBatch(rows);
                    processed += rows.size();
                    rows.clear();
                }
            }
        }

        if (!rows.isEmpty()) {
            if (replace) poiBulkRepository.upsertBatch(rows); else poiBulkRepository.upsertBatch(rows);
            processed += rows.size();
        }

        // Logging opcional
        System.out.println("[POI] Import finalizado. Procesados = " + processed);
        return processed;
    }
}