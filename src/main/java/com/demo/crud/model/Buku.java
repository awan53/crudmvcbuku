package com.demo.crud.model;

import jakarta.persistence.*;


@Entity //ini adalah menandakan bahwa ini kelas entites JPA (Model)

public class Buku {

    @Id //menandakan ini adalah Primery Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto-Increment ID
    private Long id;

    @Column(nullable = false)
    private String judulBuku;
    private String penerbitBuku;
    private Integer tahunTerbit;
    private  String isbn;

    @ManyToOne(fetch = FetchType.LAZY) // Many Buku to One Penulis
    @JoinColumn(name = "penulis_id", nullable = true) // Ini akan membuat kolom 'penulis_id' sebagai foreign key di tabel 'buku'
    private Penulis penulis;

    public Buku(){

    }

    public Buku(String judulBuku, String penerbitBuku, Integer tahunTerbit, String isbn){
        this.judulBuku = judulBuku;
        this.penerbitBuku = penerbitBuku;
        this.tahunTerbit = tahunTerbit;
        this.isbn = isbn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJudulBuku() {
        return judulBuku;
    }

    public void setJudulBuku(String judulBuku) {
        this.judulBuku = judulBuku;
    }

    public String getPenerbitBuku() {
        return penerbitBuku;
    }

    public void setPenerbitBuku(String penerbitBuku) {
        this.penerbitBuku = penerbitBuku;
    }

    public Integer getTahunTerbit() {
        return tahunTerbit;
    }

    public void setTahunTerbit(Integer tahunTerbit) {
        this.tahunTerbit = tahunTerbit;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Penulis getPenulis() {
        return penulis;
    }

    public void setPenulis(Penulis penulis) {
        this.penulis = penulis;
    }




    @Override
    public String toString() {
        return "Buku {"+
                "id="+ id +
                ", Judul ="+ judulBuku +'\''+
                ", nama_penulis ="+ (penulis != null ? penulis.getNamaPenulis() : "N/A")  +'\''+
                ",Penerbit"+ penerbitBuku +'\''+
                "isbn" + isbn + '\''+
                ", Tahun Terbit"+ tahunTerbit+
                '}';
    }
}
