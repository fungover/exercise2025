package org.example.functional;

import java.util.List;
import java.util.function.Predicate;

public class HigherOrder {

    static void main() {

        List<String> names = List.of("Martin", "Mary", "John", "Jane");

        //Imperative style for printing all names starting with M
        for (int i = 0; i < names.size(); i++) {
            String name = names.get(i);
            if (name.startsWith("M"))
                System.out.println(name);
        }
        //A little better with an enhanced for loop
        for (String name : names) {
            if (name.startsWith("M"))
                System.out.println(name);
        }
        //Functional style with streams
        Predicate<String> predicate = name -> name.startsWith("M");
        String anM = "M";
        names.stream()
                .filter(name -> name.startsWith(anM)) //Lambda expression
                .forEach(System.out::println);  //Method reference

        var namesStartingWithM = keepNamesStartingWithM(names);
    }

    public static List<String> keepNamesStartingWithM(List<String> names) {
        return names.stream()
                .filter(name -> name.startsWith("M"))
                .toList();
    }


}
