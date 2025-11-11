package org.example.model;

import jakarta.persistence.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "recipe")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String instructions;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecipeItem> items = new ArrayList<>();

    protected Recipe() {
    }

    // -------- builder --------
    public static Builder builder() {
        return new Builder();
    }

    // getters
    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getInstructions() {
        return instructions;
    }

    public List<RecipeItem> getItems() {
        return items;
    }

    // helpers
    public void addItem(RecipeItem item) {
        if (item == null) return;
        item.setRecipe(this);
        this.items.add(item);
    }

    public void removeItem(RecipeItem item) {
        if (item == null) return;
        this.items.remove(item);
        item.setRecipe(null);
    }

    public void clearItems() {
        for (var it : new ArrayList<>(items)) removeItem(it);
    }

    // Equals and hashcode
    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Recipe recipe = (Recipe) o;
        return getId() != null && Objects.equals(getId(), recipe.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    public static final class Builder {
        private final List<RecipeItem> items = new ArrayList<>();
        private String title;
        private String instructions;

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder instructions(String instructions) {
            this.instructions = instructions;
            return this;
        }

        public Builder addItem(RecipeItem item) {
            if (item != null) items.add(item);
            return this;
        }

        public Recipe build() {
            if (title == null || instructions == null)
                throw new IllegalStateException("title and instructions are required");
            Recipe recipe = new Recipe();
            recipe.title = title;
            recipe.instructions = instructions;
            for (RecipeItem item : items) recipe.addItem(item);
            return recipe;
        }
    }
}
