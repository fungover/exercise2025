package org.example.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class TruckSize {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int size;

    private double boardWidth;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private TruckBrand brandId;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public TruckSize() {}

    public TruckSize(int size, double boardWidth, TruckBrand brandId) {
        this.size = size;
        this.boardWidth = boardWidth;
        this.brandId = brandId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public double getBoardWidth() {
        return boardWidth;
    }

    public void setBoardWidth(double boardWidth) {
        this.boardWidth = boardWidth;
    }

    public TruckBrand getBrandId() {
        return brandId;
    }

    public void setBrandId(TruckBrand truckId) {
        this.brandId = truckId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        TruckSize truckSize = (TruckSize) o;
        return getId() != null && Objects.equals(getId(), truckSize.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
