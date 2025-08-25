package org.example;

public class Order {

    private int orderNumber;

    //Private constructor, only this class can create an instance of this class
    private Order(int orderNumber){
        this.orderNumber = orderNumber;
    }

    //Factory method for getting an instance of this class
    public static Order createOrder(int orderNumber){
        return new Order(orderNumber);
    }

    private String customerName;

    public void cancelOrder(){
    }

    public void processOrder(){
    }

    public void shipOrder(){
    }

}
