// src/main/java/com/demo/crud/model/Role.java
package com.demo.crud.model;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // Contoh: ROLE_USER, ROLE_ADMIN

    // Constructors, Getters, Setters
    public Role() {}
    public Role(String name) { this.name = name; }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}