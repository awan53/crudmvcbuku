package com.demo.crud.service;

import com.demo.crud.model.Buku;
import com.demo.crud.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class BukuService {
    @Autowired
    private BookRepository bookRepository; // Perhatikan nama variabelnya 'bookRepository'

    public List<Buku> getAllBuku()
    {
        return bookRepository.findAll();
    }

    // Ini adalah satu-satunya method getPaginatedBuku yang benar
    public Page<Buku> getPaginatedBuku(Pageable pageable) { // Menerima objek Pageable
        return bookRepository.findAll(pageable); // Memanggil method findAll dari JpaRepository
    }

    public Optional<Buku> getIdBuku(Long id)
    {
        return bookRepository.findById(id);
    }

    public Buku saveBuku(Buku buku)
    {
        return bookRepository.save(buku);
    }

    public void deleteBuku(Long id)
    {
        bookRepository.deleteById(id);
    }
}
