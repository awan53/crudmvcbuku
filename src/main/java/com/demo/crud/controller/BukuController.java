package com.demo.crud.controller;

import com.demo.crud.model.Buku;
import com.demo.crud.model.Penulis;
import com.demo.crud.service.BukuService; // Import BukuService
import com.demo.crud.service.PenulisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;


@Controller

public class BukuController {
    @Autowired
    private BukuService bukuService;

    @Autowired
    private PenulisService penulisService;

    @GetMapping("/")
    public String listBuku(Model model){ // Nama method: listBuku (huruf kecil 'l')
        model.addAttribute("listBuku", bukuService.getAllBuku()); // Nama atribut: listBuku (huruf kecil 'l')
        return "buku";
    }

    @GetMapping("/buku_form")
    public String tambahBukuForm(Model model)
    {
        model.addAttribute("buku", new Buku());
        model.addAttribute("pageTitle", "Tambah Buku baru");

        List<Penulis> penulisList = penulisService.getAllPenulis();
        model.addAttribute("listPenulis", penulisList);
        return "buku_form";
    }

    @PostMapping("/save")
    public String saveBuku(@ModelAttribute("buku") Buku buku, // Perbaikan casing Savebuku -> saveBuku
                           @RequestParam(value = "penulisId", required = false) Long penulisId, RedirectAttributes ra)
    {
        // Penanganan Penulis: Pastikan objek Penulis di-set sebelum menyimpan Buku
        if (penulisId != null) {
            Optional<Penulis> optionalPenulis = penulisService.getIdPenulis(penulisId);
            if (optionalPenulis.isPresent()) {
                buku.setPenulis(optionalPenulis.get());
            } else {
                // Jika penulis tidak ditemukan, tambahkan pesan error dan kembali ke form
                ra.addFlashAttribute("message", "Penulis dengan ID " + penulisId + " tidak ditemukan.");
                return "redirect:/buku_form"; // Kembali ke form agar pengguna bisa koreksi
            }
        } else {
            // Jika penulis tidak dipilih (sesuai kebutuhan Anda, bisa jadi buku tanpa penulis)
            buku.setPenulis(null); // Atur penulis menjadi null
        }

        // --- PENTING: Simpan objek Buku di sini, setelah relasi Penulis diatur ---
        bukuService.saveBuku(buku);
        // -----------------------------------------------------------------------

        ra.addFlashAttribute("message", "Buku berhasil ditambahkan/diperbarui!"); // Perbaikan pesan dan typo
        return "redirect:/"; // <-- Mengarahkan ke root URL, yang ditangani oleh listBuku
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes ra) {
        try {
            Buku buku = bukuService.getIdBuku(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid buku Id :" + id));
            model.addAttribute("buku", buku);
            model.addAttribute("pageTitle", "Edit buku (ID : " + id + ")");

            List<Penulis> penulisList = penulisService.getAllPenulis();
            model.addAttribute("listPenulis", penulisList);

            return "buku_form"; // INI SUDAH BENAR: mengarahkan ke form edit
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("message", e.getMessage()); // Perbaiki spasi di "message "
            return "redirect:/";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteBuku(@PathVariable("id") Long id, RedirectAttributes ra) {
        try {
            bukuService.getIdBuku(id) // Panggil melalui instance service
                    .orElseThrow(() -> new IllegalArgumentException("Invalid buku Id:" + id));
            bukuService.deleteBuku(id); // Panggil melalui instance service
            ra.addFlashAttribute("message", "Buku berhasil dihapus!");
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/";
    }
}
