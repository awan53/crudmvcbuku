package com.demo.crud.repository;

import com.demo.crud.model.Buku;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Buku, Long> {

}
