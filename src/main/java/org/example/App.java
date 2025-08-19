package org.example;

import org.example.api.ElprisApi;

import java.time.LocalDate;

public class App {
    public static void main(String[] args) throws Exception {
        ElprisApi api = new ElprisApi();
        String response = api.getRequest(LocalDate.now(), "SE3");
        System.out.println(response);
    }
}
