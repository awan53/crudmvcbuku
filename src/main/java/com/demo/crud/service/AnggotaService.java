package com.demo.crud.service;

import com.demo.crud.model.Anggota;
import com.demo.crud.repository.AnggotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnggotaService {
    @Autowired
    private AnggotaRepository anggotaRepository;

    public List<Anggota> getAllAnggota() {
        return anggotaRepository.findAll();
    }

    public Page<Anggota> getPanginatedAnggota(Pageable pageable){
        return anggotaRepository.findAll(pageable);
    }

    public Optional<Anggota> getAnggotaById(Long id){
        return anggotaRepository.findById(id);
    }

    public Anggota saveAnggota(Anggota anggota){
        return anggotaRepository.save(anggota);
    }

    public void deleteAnggota(Long id){
        anggotaRepository.deleteById(id);
    }


}
