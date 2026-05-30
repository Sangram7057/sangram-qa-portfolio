package com.sangram.qa.utilities;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public final class CsvUtils {
    private CsvUtils() {
    }

    public static List<Map<String, String>> readRows(String resourcePath) {
        try (InputStreamReader reader = new InputStreamReader(
                 ResourceUtils.openResource(resourcePath), StandardCharsets.UTF_8);
             CSVParser parser = CSVFormat.DEFAULT.builder()
                 .setHeader()
                 .setSkipHeaderRecord(true)
                 .setTrim(true)
                 .build()
                 .parse(reader)) {
            List<Map<String, String>> rows = new ArrayList<>();
            for (CSVRecord record : parser) {
                rows.add(record.toMap());
            }
            return rows;
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to read CSV resource: " + resourcePath, exception);
        }
    }
}
