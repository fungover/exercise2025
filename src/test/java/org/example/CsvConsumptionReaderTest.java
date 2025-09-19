package org.example;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvConsumptionReaderTest {

    @Test
    void shouldReadTestConsumptionFile() throws Exception {
        String filePath = "src/test/resources/test-consumption.csv";
        List<ConsumptionPoint> points = CsvConsumptionReader.read(filePath);

        assertFalse(points.isEmpty(), "CSV-filen borde inte vara tom");
        assertEquals("2025-08-01 00:00", points.get(0).getTime());
        assertEquals(0.23, points.get(0).getConsumption());
        assertEquals("2025-08-01 04:00", points.get(4).getTime());
        assertEquals(0.2, points.get(4).getConsumption());
    }
}