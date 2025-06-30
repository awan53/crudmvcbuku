package com.demo.crud.model;




import jakarta.persistence.*;
import java.util.Set; // Untuk Role

@Entity
@Table(name = "users") // Gunakan nama 'users' untuk menghindari konflik dengan keywords SQL
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password; // Password akan disimpan terenkripsi

    // Jika Anda ingin Roles (Admin, User, dll.)
    @ManyToMany(fetch = FetchType.EAGER) // Role seringkali dimuat segera
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    // Constructors, Getters, Setters
    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // --- Getters and Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Set<Role> getRoles() { return roles; }
    public void setRoles(Set<Role> roles) { this.roles = roles; }
}
