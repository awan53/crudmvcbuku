package com.demo.crud.controller;

import com.demo.crud.model.Buku;
import com.demo.crud.service.BukuService; // Import BukuService
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller

public class BukuController {
    @Autowired
    private BukuService bukuService;

    @GetMapping("/")
    public String listBuku(Model model){ // Nama method: listBuku (huruf kecil 'l')
        model.addAttribute("listBuku", bukuService.getAllBuku()); // Nama atribut: listBuku (huruf kecil 'l')
        return "buku";
    }

    @GetMapping("/buku_form")
    public String tambahBukuForm(Model model)
    {
        model.addAttribute("buku", new Buku());
        model.addAttribute("PageTitle", "Tambah Buku Baru");
        return "buku_form";
    }

    @PostMapping("/save")
    public String Savebuku(@ModelAttribute("buku") Buku buku, RedirectAttributes ra)
    {
        bukuService.saveBuku(buku);
        ra.addFlashAttribute("Messagge", "Buku berhasil di tambahkan");
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes ra) {
        try {
            Buku buku = bukuService.getIdBuku(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid buku Id :" + id));
            model.addAttribute("buku", buku);
            model.addAttribute("pageTitle", "Edit buku (ID : " + id + ")");
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
