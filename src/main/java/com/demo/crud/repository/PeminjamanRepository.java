package com.demo.crud.repository;

import com.demo.crud.model.Peminjaman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface PeminjamanRepository extends JpaRepository<Peminjaman, Long>{

    List<Peminjaman> findByanggotaId(Long anggotaId);
    List<Peminjaman> findByBukuId(Long bukuId);
    List<Peminjaman> findBytanggalPengembalianIsNull();
}
