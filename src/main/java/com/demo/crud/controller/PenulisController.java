package com.demo.crud.controller;

import com.demo.crud.model.Penulis;
import com.demo.crud.service.PenulisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
@Controller

public class PenulisController {

    @Autowired
    private PenulisService penulisService;

    @GetMapping("/penulis") // Ubah mapping jika perlu
    public String listPenulis(Model model,
                              @RequestParam(defaultValue = "1") int pageNum,
                              @RequestParam(defaultValue = "5") int pageSize,
                              @RequestParam(defaultValue = "id") String sortField,
                              @RequestParam(defaultValue = "asc") String sortDir) {

        Page<Penulis> pagePenulis = penulisService.getPaginatedPenulis(pageNum, pageSize, sortField, sortDir);
        List<Penulis> listPenulis = pagePenulis.getContent();

        model.addAttribute("listPenulis", listPenulis);
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", pagePenulis.getTotalPages());
        model.addAttribute("totalItems", pagePenulis.getTotalElements());
        model.addAttribute("pageSize", pageSize);

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        return "penulis"; // Pastikan ini sesuai dengan nama template Thymeleaf Anda
    }

    @GetMapping("/penulis/new")
    public String showPenulisForm(Model model) {
        model.addAttribute("penulis", new Penulis());
        model.addAttribute("pageTitle", "Tambah Penulis Baru");
        return "penulis_form"; // Ini merujuk ke penulis_form.html
    }

    @PostMapping("/penulis/save")
    public String savePenulis(@ModelAttribute("penulis") Penulis penulis, RedirectAttributes ra) {
        penulisService.savePenulis(penulis);
        ra.addFlashAttribute("message", "Penulis berhasil disimpan!");
        return "redirect:/penulis"; // Redirect ke http://localhost:8080/penulis
    }

    @GetMapping("/penulis/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes ra) {
        try {
            Penulis penulis = penulisService.getIdPenulis(id)
                    .orElseThrow(() -> {
                        ra.addFlashAttribute("error-message", "Penulis tidak ditemukan!");
                        return new IllegalArgumentException("Invalid penulis Id:" + id);
                    });
            model.addAttribute("penulis", penulis);
            model.addAttribute("pageTitle", "Edit Penulis (ID: " + id + ")");
            return "penulis_form";
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("error-message", e.getMessage());
            return "redirect:/penulis";
        }
    }

    @GetMapping("/penulis/delete/{id}")
    public String deletePenulis(@PathVariable("id") Long id, RedirectAttributes ra) {
        try {
            penulisService.deletePenulis(id);
            ra.addFlashAttribute("message", "Penulis berhasil dihapus!");
        } catch (Exception e) {
            ra.addFlashAttribute("error-message", "Error menghapus penulis: " + e.getMessage());
        }
        return "redirect:/penulis";
    }
}
