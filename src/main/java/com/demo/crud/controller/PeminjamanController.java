package com.demo.crud.controller;

import com.demo.crud.model.Peminjaman;
import com.demo.crud.model.Anggota; // Import Anggota
import com.demo.crud.model.Buku;     // Import Buku
import com.demo.crud.service.PeminjamanService;
import com.demo.crud.service.AnggotaService; // Import AnggotaService
import com.demo.crud.service.BukuService;     // Import BukuService
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/peminjaman")
public class PeminjamanController {

    private static final Logger logger =LoggerFactory.getLogger(PeminjamanController.class);

    @Autowired
    private PeminjamanService peminjamanService;

    @Autowired
    private AnggotaService anggotaService;

    @Autowired
    private BukuService bukuService;

    @GetMapping
    public String listPeminjaman(Model model,
                                 @RequestParam(defaultValue = "1") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(defaultValue = "id,asc") String[] sort)
    {
        try {
            String sortBy = sort[0];
            Sort.Direction direction = Sort.Direction.fromString(sort[1]);
            Pageable pageable = PageRequest.of(page - 1, size, Sort.by(direction, sortBy));

            Page<Peminjaman> peminjamanPage = peminjamanService.getPaginatedPeminjaman(pageable);

            model.addAttribute("peminjamanPage", peminjamanPage);
            model.addAttribute("currentPage", page);
            model.addAttribute("pageSize", size);
            model.addAttribute("sortField", sortBy);
            model.addAttribute("sortDir", direction.name().toLowerCase());

            int totalPages = peminjamanPage.getTotalPages();
            if (totalPages > 0) {
                List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
                model.addAttribute("pageNumber", pageNumbers);
            }
        }catch (Exception e)
        {
            logger.error("Error saat pengambilan daftar peminjaman : {} ", e.getMessage(), e);
            model.addAttribute("message", "terjadi kesalahan saat memuat data peminjaman ");
        }
        return "peminjaman/list";
        }

    @GetMapping("/new")
    public String showPeminjamanForm(Model model) {
        model.addAttribute("peminjaman", new Peminjaman());
        model.addAttribute("anggotaList", anggotaService.getAllAnggota());
        model.addAttribute("bukuList", bukuService.getAllBuku());
        return "peminjaman/form";
    }
    @PostMapping("/save")
    public String savePeminjaman(@ModelAttribute("peminjaman") Peminjaman peminjaman, RedirectAttributes ra) {
        logger.info("Menerima permintaan SIMPAN untuk Peminjaman: {}", peminjaman);
        try {
            peminjamanService.savePeminjaman(peminjaman);
            ra.addFlashAttribute("message", "Peminjaman berhasil disimpan!");
            logger.info("Peminjaman berhasil disimpan: {}", peminjaman.getId());
        } catch (IllegalArgumentException e) {
            logger.error("Gagal menyimpan peminjaman: {}", e.getMessage());
            ra.addFlashAttribute("message", "Gagal menyimpan peminjaman: " + e.getMessage());

        } catch (Exception e) {
            logger.error("Gagal menyimpan peminjaman: {}", e.getMessage(), e);
            ra.addFlashAttribute("message", "Terjadi kesalahan saat menyimpan peminjaman: " + e.getLocalizedMessage());
        }
        return "redirect:/peminjaman";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes ra)
    {
        logger.info("Menerima Permintaan Edit untuk peminjaman dengan Id : {}", id);

        try {
            Optional<Peminjaman> optionalPeminjaman = peminjamanService.getPeminjamanById(id);
            if (optionalPeminjaman.isPresent())
            {
               Peminjaman peminjamanActual = optionalPeminjaman.get();

               model.addAttribute("peminjaman", peminjamanActual);
               model.addAttribute("anggotaList", anggotaService.getAllAnggota());
               model.addAttribute("bukuList", bukuService.getAllBuku());

                logger.info("data peminjaman ID {}:", id);
               logger.info("tanggal peminjam: {}", peminjamanActual.getTanggalPeminjaman());
               logger.info("tanggal pengembalian: {}", peminjamanActual.getTanggalPengembalian());
                return "peminjaman/form";
            } else
            {
                ra.addFlashAttribute("message ", "Peminjam tidak ditemukan");
                return "redirect:/peminjaman";
            }
        } catch (Exception e) {
            logger.error("Error saat menampilkan form edit peminjaman ID {}: {} ", id, e.getMessage(), e);
            ra.addFlashAttribute("message", "Terjadi kesalahan memuat data peminjaman untuk edit ");
            return "redirect:/peminjaman";
        }
    }

    @GetMapping("/return/{id}")
    public String returnBuku(@PathVariable("id") Long id, RedirectAttributes ra) {
        logger.info("Menerima permintaan PENGEMBALIAN untuk Peminjaman dengan ID: {}", id);
        try {
            peminjamanService.returnPeminjaman(id);
            ra.addFlashAttribute("message", "Buku berhasil dikembalikan."); // Spasi setelah message tidak diperlukan
            logger.info("Buku berhasil dikembalikan untuk peminjaman id: {}", id);
        } catch (IllegalArgumentException e) { // Tangani khusus untuk validasi logika bisnis
            logger.error("Gagal mengembalikan buku untuk Peminjaman ID {}: {}", id, e.getMessage());
            ra.addFlashAttribute("message", "Gagal mengembalikan buku: " + e.getMessage());
        } catch (RuntimeException e) { // Untuk RuntimeException lainnya (misal: not found)
            logger.error("Gagal mengembalikan buku (runtime error) untuk Peminjaman ID {}: {}", id, e.getMessage());
            ra.addFlashAttribute("message", "Gagal mengembalikan buku: " + e.getMessage());
        } catch (Exception e) { // Untuk semua Exception lainnya
            logger.error("Terjadi kesalahan tak terduga saat mengembalikan buku untuk Peminjaman ID {}: {}", id, e.getMessage(), e);
            ra.addFlashAttribute("message", "Terjadi kesalahan saat mengembalikan buku.");
        }
        return "redirect:/peminjaman";
    }

    @GetMapping("/delete/{id}")
    public String deletePeminjaman(@PathVariable("id") Long id, RedirectAttributes ra) {
        logger.info("Menerima permintaan HAPUS untuk Peminjaman dengan ID: {}", id);
        try {
            peminjamanService.deletePeminjaman(id);
            ra.addFlashAttribute("message", "Peminjaman berhasil dihapus!");
            logger.info("Peminjaman dengan ID {} berhasil dihapus.", id);
        } catch (RuntimeException e) {
            logger.error("Gagal menghapus Peminjaman dengan ID {}: {}", id, e.getMessage());
            ra.addFlashAttribute("message", "Gagal menghapus peminjaman: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Terjadi kesalahan saat menghapus Peminjaman dengan ID {}: {}", id, e.getMessage(), e);
            ra.addFlashAttribute("message", "Terjadi kesalahan saat menghapus peminjaman.");
        }
        return "redirect:/peminjaman";
    }





}

