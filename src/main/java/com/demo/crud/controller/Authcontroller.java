// src/main/java/com/demo/crud/controller/AuthController.java
package com.demo.crud.controller;

import com.demo.crud.model.Role;
import com.demo.crud.model.User;
import com.demo.crud.repository.RoleRepository;
import com.demo.crud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections; // Untuk Set<Role>

@Controller
public class Authcontroller {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository; // Jika menggunakan role

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, RedirectAttributes ra) {
        // Cek apakah username sudah ada
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            ra.addFlashAttribute("message", "Username sudah terdaftar!");
            return "redirect:/register";
        }

        // Enkripsi password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Berikan role default (misal: ROLE_USER)
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> { // Jika ROLE_USER belum ada, buat baru
                    Role newUserRole = new Role("ROLE_USER");
                    return roleRepository.save(newUserRole);
                });
        user.setRoles(Collections.singleton(userRole)); // Set satu role saja

        userRepository.save(user);
        ra.addFlashAttribute("message", "Registrasi berhasil! Silakan login.");
        return "redirect:/login";
    }
}