package exercise8.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "allergen")
public class Allergen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private AllergenSeverity severity;

    // Constructors
    public Allergen() {
    }

    public Allergen(String name, String description, AllergenSeverity severity) {
        this.name = name;
        this.description = description;
        this.severity = severity;
    }

    // Getters and Setters
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AllergenSeverity getSeverity() {
        return severity;
    }

    public void setSeverity(AllergenSeverity severity) {
        this.severity = severity;
    }
}
