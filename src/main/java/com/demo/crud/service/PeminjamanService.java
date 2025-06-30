package com.demo.crud.service;

import com.demo.crud.model.Peminjaman;
import com.demo.crud.model.Anggota;
import com.demo.crud.model.Buku;
import com.demo.crud.repository.AnggotaRepository;
import com.demo.crud.repository.PeminjamanRepository;
import com.demo.crud.repository.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PeminjamanService {

    @Autowired
    private PeminjamanRepository peminjamanRepository;

    @Autowired
    private AnggotaRepository anggotaRepository;

    @Autowired
    private BookRepository bookRepository;

    public List<Peminjaman> getAllPeminjaman()
    {
        return peminjamanRepository.findAll();
    }

    public Page<Peminjaman> getPaginatedPeminjaman(Pageable pageable)
    {
        return peminjamanRepository.findAll(pageable);
    }

    public Optional<Peminjaman> getPeminjamanById(Long id)
    {
        return peminjamanRepository.findById(id);
    }

    @Transactional
    public Peminjaman savePeminjaman(Peminjaman peminjaman)
    {
        if (peminjaman.getAnggota() != null && peminjaman.getAnggota().getId() != null)
        {
            Anggota anggota = anggotaRepository.findById(peminjaman.getAnggota().getId()).orElseThrow(() -> new IllegalArgumentException("Anggota tidak ditemukan dengan ID : "+peminjaman.getAnggota().getId()));
            peminjaman.setAnggota(anggota);
        } else {
            throw new IllegalArgumentException("Id Anggota tidak boleh kosong. ");
        }

        if(peminjaman.getBuku() != null && peminjaman.getBuku().getId() != null)
        {
            Buku buku = bookRepository.findById(peminjaman.getBuku().getId()).orElseThrow(()-> new IllegalArgumentException("Buku tidak ditemukan dengan Id : "+ peminjaman.getBuku().getId()));
            peminjaman.setBuku(buku);
        } else
        {
            throw new IllegalArgumentException("Id buku tidak boleh kosong");
        }

        if (peminjaman.getTanggalPeminjaman()== null)
        {
            peminjaman.setTanggalPeminjaman(LocalDate.now());
        }

        return peminjamanRepository.save(peminjaman);
    }

    @Transactional
    public Peminjaman updatePeminjaman(Long id,Peminjaman peminjamanDetails)
    {
        Peminjaman peminjaman = peminjamanRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Peminjaman tidak ditemukan dengan ID "+id));

        if (peminjamanDetails.getAnggota() != null && peminjamanDetails.getAnggota().getId() != null)
        {
            Anggota anggota = anggotaRepository.findById(peminjamanDetails.getAnggota().getId()).orElseThrow(() ->new IllegalArgumentException("Anggota Tidak ditemukan dengan Id : " +peminjamanDetails.getAnggota().getId()));
            peminjaman.setAnggota(anggota);
        }
        if (peminjamanDetails.getBuku() != null && peminjamanDetails.getBuku().getId()!=null)
        {
            Buku buku = bookRepository.findById(peminjamanDetails.getBuku().getId()).orElseThrow(()-> new IllegalArgumentException("Buku Tidak ditemukan dengan Id : "+peminjamanDetails.getBuku().getId()));
            peminjaman.setBuku(buku);
        }
        peminjaman.setTanggalPeminjaman(peminjamanDetails.getTanggalPeminjaman());
        peminjaman.setTanggalPengembalian(peminjamanDetails.getTanggalPengembalian());

        return peminjamanRepository.save(peminjaman);
    }

    @Transactional
    public void  deletePeminjaman(Long id)
    {
        if(!peminjamanRepository.existsById(id))
        {
            throw  new RuntimeException("Peminjaman tidak ditemukan dengan ID :"+id);
        }

        peminjamanRepository.deleteById(id);
    }

    @Transactional
    public Peminjaman returnPeminjaman(Long peminjamanId)
    {
        Peminjaman peminjaman = peminjamanRepository.findById(peminjamanId).orElseThrow(() -> new RuntimeException("Peminjaman tidak ditemukan dengan Id : "+peminjamanId));

        if (peminjaman.getTanggalPeminjaman() != null)
        {
            throw new IllegalArgumentException("Buku sudah dikembalikan ");
        }

        peminjaman.setTanggalPengembalian(LocalDate.now());
        return peminjamanRepository.save(peminjaman);
    }




}
