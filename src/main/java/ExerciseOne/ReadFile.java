package ExerciseOne;
import com.opencsv.exceptions.CsvException;
import java.io.IOException;
import java.util.List;

public class ReadFile {

    public List<Consumption> readFile(String filePath) {

        try (var reader = new com.opencsv.CSVReaderBuilder(
                java.nio.file.Files.newBufferedReader(java.nio.file.Path.of(filePath), java.nio.charset.StandardCharsets.UTF_8))
            .withSkipLines(1)
            .withCSVParser(new com.opencsv.CSVParserBuilder().withSeparator(';').build())
            .build()) {
                return reader.readAll().stream()
                        .filter(cols -> cols.length >= 2)
                        .map(cols -> new Consumption(cols[0].trim(),
                        Double.parseDouble(cols[1].trim().replace(',', '.'))))
                        .toList();

        } catch (IOException | CsvException e )  {
            throw new IllegalArgumentException("Kunde inte läsa filen: " + filePath, e);
        }
    }
}
