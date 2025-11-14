package org.example.entities;

import jakarta.persistence.*;
import org.example.Authority;
import org.hibernate.proxy.HibernateProxy;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @ManyToMany(mappedBy = "roles")
    private Set<CustomizedUser> customizedUser = new HashSet<>();

    public Role() {
    }

    public Role(Authority authority) {
        this.authority = authority;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer id) {
        this.roleId = id;
    }

    public Authority getAuthority() {
        return authority;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }

    public Set<CustomizedUser> getUser() {
        return customizedUser;
    }

    public void setUser(Set<CustomizedUser> customizedUser) {
        this.customizedUser = customizedUser;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Role role = (Role) o;
        return getRoleId() != null && Objects.equals(getRoleId(), role.getRoleId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
