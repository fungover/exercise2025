package org.example.helloworld;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Hello {

    private String name;

    public Hello(String name) {
        this.name = name;
    }

    public String getHello(){
        return name;
    }
}
