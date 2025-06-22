package com.demo.crud.service;
import com.demo.crud.model.Buku;
import com.demo.crud.model.Penulis;
import com.demo.crud.repository.PenulisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PenulisService {

    @Autowired
    private PenulisRepository penulisRepository;

    public List<Penulis> getAllPenulis()
    {
        return penulisRepository.findAll();
    }

    public Page<Penulis> getPaginatedPenulis(int pageNum, int pageSize, String sortField, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
        return penulisRepository.findAll(pageable);
    }

    public Optional<Penulis> getIdPenulis(Long id)
    {
        return penulisRepository.findById(id);
    }

    public Penulis savePenulis(Penulis penulis)
    {
        return penulisRepository.save(penulis);
    }

    public void deletePenulis(Long id)
    {
        penulisRepository.deleteById(id);
    }
}
