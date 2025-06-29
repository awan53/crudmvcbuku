package com.demo.crud.repository;

import com.demo.crud.model.Anggota;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnggotaRepository extends JpaRepository<Anggota, Long> {
}
