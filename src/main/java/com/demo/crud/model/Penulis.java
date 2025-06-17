package com.demo.crud.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany; // Import ini untuk relasi dari sisi Penulis
import jakarta.persistence.CascadeType; // Import ini untuk cascade
import jakarta.persistence.FetchType; // Import ini untuk fetch type
import jakarta.persistence.Table;
import java.util.HashSet; // Import ini untuk Set
import java.util.Set;     // Import ini untuk Set

@Entity // Ini harus ada di atas kelas
@Table(name = "penulis") // Opsional, tapi disarankan untuk nama tabel yang eksplisit
public class Penulis { // <-- Deklarasi kelas ini harus ada

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // <-- Deklarasi variabel id harus ada

    @Column(nullable = false, unique = true)
    private String namaPenulis; // <-- Deklarasi variabel namaPenulis harus ada

    @OneToMany(mappedBy = "penulis", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Buku> bukuList = new HashSet<>(); // <-- Deklarasi variabel bukuList harus ada


    public Penulis() // <-- Konstruktor default (tanpa argumen)
    {

    }

    public Penulis (String namaPenulis){ // <-- Konstruktor dengan argumen
        this.namaPenulis = namaPenulis;
    }

    // --- Getters and Setters ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNamaPenulis() {
        return namaPenulis;
    }

    public void setNamaPenulis(String namaPenulis) {
        this.namaPenulis = namaPenulis;
    }

    public Set<Buku> getBukuList() {
        return bukuList;
    }

    public void setBukuList(Set<Buku> bukuList) {
        this.bukuList = bukuList;
    }

    // --- Helper methods for bidirectional relationship management ---
    public void addBuku(Buku buku) {
        this.bukuList.add(buku);
        buku.setPenulis(this); // Set penulis di sisi Buku
    }

    public void removeBuku(Buku buku) {
        this.bukuList.remove(buku);
        buku.setPenulis(null); // Hapus referensi penulis di sisi Buku
    }

    @Override
    public String toString() {
        return "Penulis{" +
                "id=" + id +
                ", namaPenulis='" + namaPenulis + '\'' +
                '}';
    }
}