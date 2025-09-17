package org.example;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class App {
    public static void main(String[] args) throws NoSuchAlgorithmException, SQLException {
         var strings = someStrings();
         strings.forEach(System.out::println);

         Logger logger = Logger.getLogger("App");
         logger.info("Looks to work okey");
         logger.severe("This is a problem");

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashCode = md.digest("Hello World".getBytes());
        StringBuilder sb = new StringBuilder();
        for( byte b : hashCode){
            sb.append(String.format("%02X", b));
        }
        System.out.println(sb.toString());

        //Factory methods
        List.of();
        Integer.valueOf(1);
        Map.of("key","value","key2","value2");

        //Not a factory method
        double sqrt = Math.sqrt(2.0);
    }


    public static List<String> someStrings(){
        //return new ArrayList<>(List.of("a","b"));
        return List.of("1","2");
    }

}
