package org.example;


import jakarta.validation.constraints.NotBlank;

public class petDTO {

    private Long id;


    @NotBlank(message = "Name cannot be blank")
    private String name;
}
