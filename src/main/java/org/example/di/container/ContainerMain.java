package org.example.di.container;

public class ContainerMain {
    public static void main(String[] args) {
        SimpleContainer container = new SimpleContainer();
        GreetingServiceImp service = container.getInstance(GreetingServiceImp.class);
        System.out.println(service.getGreeting("User from container"));
    }
}
