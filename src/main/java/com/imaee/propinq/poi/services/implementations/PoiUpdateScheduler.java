package com.imaee.propinq.poi.services.implementations;

import com.imaee.propinq.poi.services.implementations.PoiImportService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;

@Component
@EnableScheduling
public class PoiUpdateScheduler {

    private final PoiImportService importService;

    @Value("${poi.scheduler.enabled:true}")
    private boolean enabled;

    // Conservamos flags por compatibilidad, pero delegamos la lógica al orquestador.
    @Value("${poi.scheduler.region:south-america/argentina-latest}")
    private String region;

    @Value("${poi.scheduler.pbf-path:/app/data/argentina-latest.osm.pbf}")
    private String pbfPath;

    @Value("${poi.import.file:/app/out/pois.geojsonseq}")
    private String importFile;

    @Value("${poi.scheduler.out-dir:/app/out}")
    private String outDir;

    public PoiUpdateScheduler(PoiImportService importService) {
        this.importService = importService;
    }

    // Podés dejarlo diario; el throttle evitará trabajo innecesario.
    // Si preferís una vez por año: "0 30 3 1 1 *"
    @Scheduled(cron = "${poi.scheduler.cron:0 30 3 * * *}")
    public void run() throws Exception {
        if (!enabled) return;

        // Llamamos al orquestador idempotente (respeta MD5 y throttle anual).
        int code = new ProcessBuilder("bash", "/app/scripts/update-argentina.sh", "/app/data", outDir, region)
                .inheritIO()
                .start()
                .waitFor();
        if (code != 0) {
            throw new IllegalStateException("update-argentina.sh exit " + code);
        }

        if (Files.exists(Path.of(importFile))) {
            long n = importService.importToMySql();
            System.out.println("[POI] Scheduler import completo. Registros = " + n);
        }
    }
}