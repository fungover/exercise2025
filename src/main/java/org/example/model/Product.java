package org.example.model;

import jakarta.persistence.*;

@Entity
public class Product {

     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

     private String name;
     private String sku; // Stock Keeping Unit
     private int quantity;

     @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

     // Getters & Setters
     public Long getId() {
         return id;
     }
     public void setId(Long id) {
         this.id = id;
     }
     public String getName() {
         return name;
     }

     public void setName(String name) {
         this.name = name;
     }

     public String getSku() {
         return sku;
     }
     public void setSku(String sku) {
         this.sku = sku;
     }
     public int getQuantity() {
         return quantity;
    }
    public void setQuantity(int quantity) {
         this.quantity = quantity;
    }
    public Location getLocation() {
         return location;
    }
    public void setLocation(Location location) {
         this.location = location;
    }

}

