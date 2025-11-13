package org.example.entities;

import jakarta.persistence.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Entity
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer storeId;

    String storeName;

    public Store() {
    }

    public Store(String storeName) {
        this.storeName = storeName;
    }

    public Integer getStoreId() {
        return storeId;
    }
    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }
    public String getStoreName() {
        return storeName;
    }
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Store store = (Store) o;
        return getStoreId() != null && Objects.equals(getStoreId(), store.getStoreId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }


    @Override
    public String toString() {
        return "Store{" + "storeId=" + storeId + ", storeName=" + storeName + '}';
    }
}
