package com.demo.crud.controller;
import com.demo.crud.model.Anggota; // Import model Anggota
import com.demo.crud.service.AnggotaService; // Import AnggotaService
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page; // Untuk paginasi
import org.springframework.data.domain.PageRequest; // Untuk membuat Pageable
import org.springframework.data.domain.Pageable; // Untuk paginasi
import org.springframework.data.domain.Sort; // Untuk sorting
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // Untuk mengirim data ke Thymeleaf
import org.springframework.web.bind.annotation.*; // Anotasi web
import org.springframework.web.servlet.mvc.support.RedirectAttributes; // Untuk pesan redirect
import org.springframework.dao.DataIntegrityViolationException;

import jakarta.servlet.http.HttpServletRequest; // Untuk mendapatkan URI saat ini
import java.time.LocalDate; // Untuk tanggal daftar otomatis
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class AnggotaController {

    private static final Logger logger = LoggerFactory.getLogger(AnggotaController.class);

    @Autowired
    private AnggotaService anggotaService;


    @GetMapping("/anggota")
    public String listAnggota(Model model,
                              @RequestParam(defaultValue = "1") int pageNum,
                              @RequestParam(defaultValue = "5") int pageSize,
                              @RequestParam(defaultValue = "id") String sortField,
                              @RequestParam(defaultValue = "asc") String sortDir,
                              HttpServletRequest request){

        int actualPageNum = pageNum - 1; // <-- Perbaiki 'actualpageNum' jadi 'actualPageNum'

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending():Sort.by(sortField).descending();

        // Perbaikan 2: Deklarasi dan inisialisasi objek Pageable
        org.springframework.data.domain.Pageable pageable = PageRequest.of(actualPageNum, pageSize, sort); // <-- TAMBAHKAN BARIS INI

        // Perbaikan 3: Perbaiki typo 'getPanginatedAnggota' dan panggil service dengan objek pageable
        Page<Anggota> pageAnggota = anggotaService.getPanginatedAnggota(pageable); // <-- Perbaikan typo dan gunakan 'pageable'

        // Perbaikan 4: Ambil listAnggota dari pageAnggota sebelum menambahkannya ke model
        List<Anggota> listAnggota = pageAnggota.getContent(); // <-- TAMBAHKAN BARIS INI

        model.addAttribute("listAnggota", listAnggota);
        model.addAttribute("currentPage", pageNum); // Halaman saat ini (1-based)
        model.addAttribute("totalPages", pageAnggota.getTotalPages()); // Total jumlah halaman
        model.addAttribute("totalItems", pageAnggota.getTotalElements()); // Total jumlah item
        model.addAttribute("pageSize", pageSize);

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("currentPageUri", request.getRequestURI()); // URI saat ini untuk link paginasi
        model.addAttribute("pageTitle", "Daftar Anggota"); // Judul halaman

        return "anggota";
    }

    @GetMapping("/anggota/new")
    public String showAnggotaForm(Model model, HttpServletRequest request){
        model.addAttribute("anggota", new Anggota()); // Membuat objek Anggota baru untuk form
        model.addAttribute("pageTitle", "Tambah Anggota Baru"); // Judul halaman form
        model.addAttribute("currentPageUri", request.getRequestURI());
        return "anggota_form";
    }

    @PostMapping("/anggota/save")
    public String saveAnggota(@ModelAttribute("anggota") Anggota anggota, RedirectAttributes ra)
    {
        if(anggota.getId() == null || anggota.getTanggalDaftar()== null)
        {
            anggota.setTanggalDaftar(LocalDate.now());
        }
        anggotaService.saveAnggota(anggota);
        ra.addFlashAttribute("message", "anggota berhasil disimpan");
        return "redirect:/anggota";
    }

    @GetMapping("/anggota/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes ra, HttpServletRequest request) {
        try {
            Anggota anggota = anggotaService.getAnggotaById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid anggota Id:" + id)); // Mencari anggota berdasarkan ID
            model.addAttribute("anggota", anggota); // Menambahkan objek anggota ke model
            model.addAttribute("pageTitle", "Edit Anggota"); // Judul halaman edit
            model.addAttribute("currentPageUri", request.getRequestURI());
            return "anggota_form"; // Mengarahkan kembali ke form yang sama untuk edit
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("message", e.getMessage()); // Pesan error jika tidak ditemukan
            return "redirect:/anggota"; // Redirect kembali ke daftar anggota
        }
    }

    @GetMapping("/anggota/delete/{id}")
    public String deleteAnggota(@PathVariable("id") Long id, RedirectAttributes ra) {

        logger.info("Menerima permintaan HAPUS untuk Anggota dengan ID: {}", id);
        try {
            anggotaService.deleteAnggota(id);
            ra.addFlashAttribute("message", "Anggota Berhasil Dihapus");
            logger.info("anggota Dengan Id {} berhasil dihapus. ", id);
        }catch (DataIntegrityViolationException e)
        {
            logger.error("gagal Menghapus Anggota dengan ID {} karena data terkait (Foreign Key Constraint): {}", id, e.getMessage());
        }catch (Exception e)
        {
            logger.error("Gagal Menghapus dengan ID {} ", id, e.getMessage(), e);
            ra.addFlashAttribute("message", "Gagal Menghapus anggota: " + e.getLocalizedMessage());
        }
        return "redirect:/anggota";
    }



}
