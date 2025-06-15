package com.demo.crud.service;

import com.demo.crud.model.Buku;
import com.demo.crud.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BukuService {
    @Autowired
    private BookRepository bookRepository;

    public List<Buku> getAllBuku()
    {
        return bookRepository.findAll();
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
