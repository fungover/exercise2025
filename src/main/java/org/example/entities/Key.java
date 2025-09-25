package org.example.entities;

public class Key extends Item {

        public Key(String name, String description, String type, int quantity) {
            super(name, description, type, quantity);
        }

        @Override
        public void displayInfo() {
            System.out.println(getName() + " - " + getDescription() + " - Quantity: " + getQuantity());
        }

        @Override
        public String toString() {
            return getName() + " - (" + getDescription() + ")";
        }

}

