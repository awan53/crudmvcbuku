package com.demo.crud.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "anggota")
public class Anggota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nama_anggota", nullable = false, length = 100)
    private String namaAnggota;

    @Column(name = "alamat", nullable = false, length = 255)
    private String alamat;

    @Column(name = "no_Telephone", nullable = false, length = 20)
    private String no_Telephone;

    @Column(name = "email", unique = true, length = 100)
    private String email;

    @Column(name = "tanggal_daftar", nullable = false)
    private LocalDate tanggalDaftar;


    public Anggota()
    {

    }

    public Anggota(String namaAnggota, String alamat, String no_Telephone, String email, LocalDate tanggalDaftar)
    {
        this.namaAnggota = namaAnggota;
        this.alamat = alamat;
        this.no_Telephone = no_Telephone;
        this.email = email;
        this.tanggalDaftar = tanggalDaftar;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNamaAnggota() {
        return namaAnggota;
    }

    public void setNamaAnggota(String namaAnggota) {
        this.namaAnggota = namaAnggota;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNo_Telephone() {
        return no_Telephone;
    }

    public void setNo_Telephone(String no_Telephone) {
        this.no_Telephone = no_Telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getTanggalDaftar() {
        return tanggalDaftar;
    }

    public void setTanggalDaftar(LocalDate tanggalDaftar) {
        this.tanggalDaftar = tanggalDaftar;
    }

    public String toString()
    {
        return "Anggota{"+
                "id="+id+
                ", namaAnggota='" + namaAnggota + '\'' +
                ", alamat='" + alamat + '\'' +
                ", no_Telepon='" + no_Telephone + '\'' +
                ", email='" + email + '\'' +
                ", tanggalDaftar=" + tanggalDaftar +
                '}';
    }
}
