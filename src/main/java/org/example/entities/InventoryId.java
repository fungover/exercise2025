package org.example.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class InventoryId implements Serializable {

    @Column(name ="book_id")
    private Integer bookId;
    @Column(name = "store_id")
    private Integer storeId;

    public InventoryId() {}

    public InventoryId(Integer bookId, Integer storeId) {
        this.bookId = bookId;
        this.storeId = storeId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer title) {
        this.bookId = title;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof InventoryId that)) return false;
        return Objects.equals(bookId, that.bookId) && Objects.equals(storeId, that.storeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, storeId);
    }
}
