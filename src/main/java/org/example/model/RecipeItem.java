package org.example.model;

import jakarta.persistence.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Entity
@Table(name = "recipe_item")
public class RecipeItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private String unit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    protected RecipeItem() {
    }

    // -------- builder --------
    public static Builder builder() {
        return new Builder();
    }

    // getters
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public String getUnit() {
        return unit;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    // helpers
    void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    // Equals and hashcode
    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        RecipeItem that = (RecipeItem) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    public static final class Builder {
        private String name;
        private Double amount;
        private String unit;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder amount(double amount) {
            this.amount = amount;
            return this;
        }

        public Builder unit(String unit) {
            this.unit = unit;
            return this;
        }

        public RecipeItem build() {
            if (name == null || amount == null || unit == null)
                throw new IllegalStateException("name, amount, unit are required");
            RecipeItem item = new RecipeItem();
            item.name = name;
            item.amount = amount;
            item.unit = unit;
            return item;
        }
    }
}
