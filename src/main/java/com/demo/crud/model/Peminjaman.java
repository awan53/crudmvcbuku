package com.demo.crud.model;


import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "peminjaman")
public class Peminjaman {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "anggota_id", nullable = false)
    private Anggota anggota;

    @ManyToOne
    @JoinColumn(name = "buku_id", nullable = false)
    private Buku buku;

    @Column(name = "tanggal_peminjaman", nullable = false)
    private LocalDate tanggalPeminjaman;

    @Column(name = "tanggal_pengembalian", nullable = true)
    private LocalDate tanggalPengembalian;

    public Peminjaman()
    {
        this.tanggalPeminjaman =LocalDate.now();
    }

    public Peminjaman(Anggota anggota, Buku buku){
        this.anggota = anggota;
        this.buku =buku;
        this.tanggalPeminjaman = LocalDate.now();
        this.tanggalPengembalian= LocalDate.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Anggota getAnggota() {
        return anggota;
    }

    public void setAnggota(Anggota anggota) {
        this.anggota = anggota;
    }

    public Buku getBuku() {
        return buku;
    }

    public void setBuku(Buku buku) {
        this.buku = buku;
    }

    public LocalDate getTanggalPeminjaman() {
        return tanggalPeminjaman;
    }

    public void setTanggalPeminjaman(LocalDate tanggalPeminjaman) {
        this.tanggalPeminjaman = tanggalPeminjaman;
    }

    public LocalDate getTanggalPengembalian() {
        return tanggalPengembalian;
    }

    public void setTanggalPengembalian(LocalDate tanggalPengembalian) {
        this.tanggalPengembalian = tanggalPengembalian;
    }

    @Override
    public String toString()
    {
        return "Peminjaman {" +
                "id= "+id+
                ", anggota "+(anggota !=null ? anggota.getNamaAnggota(): "null")+
                ", buku ="+(buku != null ? buku.getJudulBuku(): "null")+
                ", tanggalPeminjaman = "+tanggalPeminjaman+
                ", tanggalPengembalian "+tanggalPengembalian+
                '}';

    }
}
