package ExerciseOne;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ReadFile {

    public List<Consumption> readFile() {

        Scanner sc = new Scanner(System.in);
        System.out.print("Ange sökväg till csv fil: ");
        String filePath = sc.nextLine();

        try(CSVReader reader = new CSVReader(new FileReader(filePath))){
            List<String[]> consumptionList = reader.readAll();

            return consumptionList.stream()
                    .skip(1)
                    .map(rad -> rad[0].split(";"))
                    .filter(i -> i.length == 2)
                    .map(elem -> new Consumption(elem[0], Double.parseDouble(elem[1])))
                    .toList();

        } catch (IOException | CsvException e )  {
            System.out.printf("Error: %s\n", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
