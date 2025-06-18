package com.demo.crud.repository;

import com.demo.crud.model.Penulis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PenulisRepository extends JpaRepository<Penulis, Long> {
}
