package ExerciseOne;

import com.opencsv.exceptions.CsvException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class ReadFile {

    public List<Consumption> readFile(String filePath) {

        try (var reader = new com.opencsv.CSVReaderBuilder(new FileReader(filePath))
            .withSkipLines(1)
            .withCSVParser(new com.opencsv.CSVParserBuilder().withSeparator(';').build())
            .build()) {
                return reader.readAll().stream()
                        .filter(cols -> cols.length >= 2)
                        .map(cols -> new Consumption(cols[0].trim(),
                        Double.parseDouble(cols[1].trim().replace(',', '.'))))
                        .toList();

        } catch (IOException | CsvException e )  {
            throw new IllegalArgumentException("Kunde inte läsa filen");
        }
    }
}
