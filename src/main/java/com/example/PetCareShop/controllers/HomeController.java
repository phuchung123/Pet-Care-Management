package com.example.PetCareShop.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.PetCareShop.models.CustomUserDetails;

@Controller
public class HomeController {

    @GetMapping("/admin/home")
    public String home(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        
        model.addAttribute("user", userDetails.getUser());
        return "admin/home"; // Đường dẫn tới file admin/home.html
    }
    
    @RequestMapping("/error/forbidden")
    public String error403() {
        return "error/forbidden"; // Đường dẫn tới trang 403 tùy chỉnh
    }
}
