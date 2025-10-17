package com.imaee.propinq.poi.services.implementations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
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

    @Value("${poi.scheduler.cron:0 30 3 * * *}")
    private String cronExpression;

    @Value("${poi.scheduler.region:south-america/argentina-latest}")
    private String region;

    @Value("${poi.scheduler.pbf-path:/app/data/argentina-latest.osm.pbf}")
    private String pbfPath;

    @Value("${poi.import.file:/app/out/pois.geojsonseq}")
    private String importFile;

    @Value("${poi.scheduler.out-dir:/app/out}")
    private String outDir;

    @Value("${poi.scheduler.run-on-startup:false}")
    private boolean runOnStartup;

    public PoiUpdateScheduler(PoiImportService importService) {
        this.importService = importService;
    }

    @Scheduled(cron = "${poi.scheduler.cron:0 30 3 * * *}")
    public void scheduledRun() throws Exception {
        if (!enabled) return;
        updateAndImport();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void maybeRunOnStartup() throws Exception {
        if (runOnStartup && enabled) {
            updateAndImport();
        }
    }

    private void updateAndImport() throws Exception {
        int code = new ProcessBuilder("bash", "/app/scripts/update-argentina.sh", "/app/data", outDir, region)
                .inheritIO()
                .start()
                .waitFor();
        if (code != 0) {
            throw new IllegalStateException("update-argentina.sh exit " + code);
        }

        if (Files.exists(Path.of(importFile))) {
            long n = importService.importToMySql();
            System.out.println("[POI] Import completo. Registros = " + n);
        } else {
            System.out.println("[POI] No se encontró archivo para importar: " + importFile);
        }
    }
}