package com.demo.crud.controller;

import com.demo.crud.model.Penulis;
import com.demo.crud.service.PenulisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.PublicKey;


@Controller
@RequestMapping("/penulis")
public class PenulisController {

    @Autowired
    private PenulisService penulisService;

    // Menampilkan daftar semua penulis
    @GetMapping("/") // <--- URL ini akan menjadi /penulis/
    public String listPenulis(Model model) {
        model.addAttribute("listPenulis", penulisService.getAllPenulis()); // <--- Nama atribut camelCase
        return "penulis_list"; // <--- Mengarahkan ke template penulis_list.html
    }

    // Menampilkan form untuk menambah penulis baru
    @GetMapping("/form") // <--- URL ini akan menjadi /penulis/form
    public String showPenulisForm(Model model) {
        model.addAttribute("penulis", new Penulis());
        model.addAttribute("pageTitle", "Tambah Penulis Baru"); // <--- Nama atribut camelCase
        return "penulis_form"; // <--- Mengarahkan ke template penulis_form.html
    }

    // Menangani pengiriman form untuk menyimpan (tambah/edit) penulis
    @PostMapping("/save") // <--- URL ini akan menjadi /penulis/save
    public String savePenulis(@ModelAttribute("penulis") Penulis penulis, RedirectAttributes ra) {
        penulisService.savePenulis(penulis);
        ra.addFlashAttribute("message", "Penulis berhasil disimpan!");
        return "redirect:/penulis/"; // <--- PENTING: Redirect kembali ke halaman daftar penulis
    }

    // Menampilkan form untuk mengedit penulis yang sudah ada
    @GetMapping("/edit/{id}") // <--- URL ini akan menjadi /penulis/edit/{id}
    public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes ra) {
        try {
            Penulis penulis = penulisService.getIdPenulis(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid penulis Id:" + id));
            model.addAttribute("penulis", penulis);
            model.addAttribute("pageTitle", "Edit Penulis (ID: " + id + ")");
            return "penulis_form";
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/penulis/";
        }
    }

    // Menangani penghapusan penulis
    @GetMapping("/delete/{id}") // <--- URL ini akan menjadi /penulis/delete/{id}
    public String deletePenulis(@PathVariable("id") Long id, RedirectAttributes ra) {
        try {
            penulisService.deletePenulis(id);
            ra.addFlashAttribute("message", "Penulis berhasil dihapus!");
        } catch (Exception e) {
            ra.addFlashAttribute("message", "Error menghapus penulis: " + e.getMessage());
        }
        return "redirect:/penulis/";
    }

}
