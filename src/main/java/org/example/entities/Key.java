package org.example.entities;

public class Key extends Item {
        private boolean unlock = true;

        public Key(String name, String description, String type, int quantity, boolean unlock) {
            super(name, description, type, quantity);
        }

        public boolean isUnlocked() {
            return unlock;
        }

        @Override
        public void displayInfo() {
            System.out.println("Key: " + getName() + " - " + getDescription() + " - Quantity: " + getQuantity());
        }

        @Override
        public String toString() {
            return getName() + " - (" + getDescription() + ")";
        }

}

