package com.demo.crud.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;




@Entity //ini adalah menandakan bahwa ini kelas entites JPA (Model)
public class Buku {

    @Id //menandakan ini adalah Primery Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto-Increment ID
    private Long id;

    @Column(nullable = false)
    private String Nama;
    private String Judul;
    private String Penerbit;
    private Integer tahunTerbit;

    public Buku(){

    }

    public Buku(String Nama, String Judul, String Penerbit, Integer tahunTerbit){
        this.Nama = Nama;
        this.Judul = Judul;
        this.Penerbit = Penerbit;
        this.tahunTerbit = tahunTerbit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public String getJudul() {
        return Judul;
    }

    public void setJudul(String judul) {
        Judul = judul;
    }

    public String getPenerbit() {
        return Penerbit;
    }

    public void setPenerbit(String penerbit) {
        Penerbit = penerbit;
    }

    public Integer getTahunTerbit() {
        return tahunTerbit;
    }

    public void setTahunTerbit(Integer tahunTerbit) {
        this.tahunTerbit = tahunTerbit;
    }

    @Override
    public String toString() {
        return "Buku {"+
                "id="+ id +
                ", Judul ="+ Judul +'\''+
                ", Nama ="+ Nama +'\''+
                ",Penerbit"+ Penerbit +'\''+
                ", Tahun Terbit"+ tahunTerbit+
                '}';
    }
}
