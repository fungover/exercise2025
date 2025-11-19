package org.example.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Entity
public class Inventory {
    @EmbeddedId
    private InventoryId inventoryId = new InventoryId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("bookId")
    @JoinColumn(name = "book_id", nullable = false)
    @NotNull(message = "Books must be inserted")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("storeId")
    @JoinColumn(name = "store_id",  nullable = false)
    @NotNull(message = "Store must be inserted")
    private Store store;

    private int amount;

    public Inventory(){}

    public Inventory(Book book, Store store, int amount) {
        this.inventoryId = new InventoryId();
        this.book = book;
        this.store = store;
        this.amount = amount;
    }

    public InventoryId getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(InventoryId inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Inventory inventory = (Inventory) o;
        return getInventoryId() != null && Objects.equals(getInventoryId(), inventory.getInventoryId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

}
