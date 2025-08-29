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
                        .map(cols -> {
                            final String time = cols[0].trim();
                            final String consumed = cols[1].trim().replace(',','.');
                        try{
                            return new Consumption(time, Double.parseDouble(consumed));
                        }catch(NumberFormatException e){
                            throw new IllegalArgumentException("Ogiltigt värde för "+time+" : "+consumed);
                        }
                        })
                        .toList();

        } catch (IOException | CsvException e )  {
            throw new IllegalArgumentException("Kunde inte läsa filen: " + filePath, e);
        }
    }
}
