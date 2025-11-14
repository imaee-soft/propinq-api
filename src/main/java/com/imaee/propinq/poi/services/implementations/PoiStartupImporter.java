package com.imaee.propinq.poi.services.implementations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import java.nio.file.Files;
import java.nio.file.Path;
@Component
public class PoiStartupImporter implements ApplicationRunner {

    private final PoiImportService importService;

    @Value("${poi.startup.enabled:true}")
    private boolean startupEnabled;

    // Scripts
    @Value("${poi.scheduler.run-download:true}")
    private boolean runDownload;

    @Value("${poi.scheduler.run-extraction:true}")
    private boolean runExtraction;

    @Value("${poi.scheduler.region:south-america/argentina-latest}")
    private String region;

    @Value("${poi.scheduler.pbf-path:/app/data/argentina-latest.osm.pbf}")
    private String pbfPath;

    @Value("${poi.scheduler.out-dir:/app/out}")
    private String outDir;

    public PoiStartupImporter(PoiImportService importService) {
        this.importService = importService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (!startupEnabled) return;

        if (runDownload) {
            runScript("bash", "/app/scripts/download_geofabrik.sh", "/app/data", region);
        }
        if (runExtraction) {
            runScript("bash", "/app/scripts/extract_pois.sh", pbfPath, outDir);
        }
        // Only run the importer if the extracted GeoJSON file exists
        var importPath = Path.of(outDir, "pois.geojsonseq");
        if (Files.exists(importPath)) {
            long n = importService.importToMySql();
            System.out.println("[POI] Startup import completo. Registros = " + n);
        } else {
            System.out.println("[POI] Archivo de importación no encontrado: " + importPath + " - saltando import en startup.");
        }
    }

    private static void runScript(String... cmd) throws Exception {
        int code = new ProcessBuilder(cmd).inheritIO().start().waitFor();
        if (code != 0 && code != 2) { // 2 = "sin cambios" en download_geofabrik.sh
            throw new IllegalStateException("Falló script: " + String.join(" ", cmd) + " (exit " + code + ")");
        }
    }
}
*/