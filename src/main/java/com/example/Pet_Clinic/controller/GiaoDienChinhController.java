package com.example.Pet_Clinic.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class GiaoDienChinhController {
	
    @GetMapping("/")
    public String home() {
        return "GiaoDienChinh/GiaoDienChinh";
    }
    
    @GetMapping("/login")
    public String showLoginForm() {
        return "GiaoDienChinh/DangNhap";
    }
    
    @GetMapping("/logout")
	public String Logout(HttpServletRequest request, HttpServletResponse response) {
		SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
	    logoutHandler.logout(request, response, SecurityContextHolder.getContext().getAuthentication());
	    return "redirect:/GiaoDienChinh/DangNhap";
	}
}