package com.example.Pet_Clinic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.Pet_Clinic.entity.KhachHang;
import com.example.Pet_Clinic.entity.TaiKhoan;
import com.example.Pet_Clinic.service.KhachHangService;
import com.example.Pet_Clinic.service.TaiKhoanService;

@Controller
@RequestMapping("/nguoidung")
public class NguoiDungController {

    @Autowired
    private KhachHangService khachHangService;
    
    @Autowired
    private TaiKhoanService taiKhoanService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("khachHang", new KhachHang());
        return "GiaoDienChinh/DangKy";
    }

    @PostMapping("/register")
    public String registerCustomer(@ModelAttribute("khachHang") KhachHang khachHang, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "GiaoDienChinh/DangKy";
        }

        boolean hasErrors = false;

        // Kiểm tra Email và số điện thoại
        if (khachHangService.existsBySoDienThoai(khachHang.getSoDienThoai())) {
            model.addAttribute("soDTError", "Số điện thoại đã tồn tại");
            hasErrors = true;
        }
        if (khachHangService.existsByEmail(khachHang.getEmail())) {
            model.addAttribute("emailError", "Email đã tồn tại");
            hasErrors = true;
        }

        if (hasErrors) {
            return "GiaoDienChinh/DangKy";
        }
        
        try {
            // Tạo tên tài khoản từ email
            String username = khachHang.getEmail();

            // Tạo mật khẩu từ soDT và mã hóa mật khẩu
            String password = khachHang.getSoDienThoai();
            String encodedPassword = passwordEncoder.encode(password); // Mã hóa mật khẩu

            // Tạo đối tượng TaiKhoan và lưu vào cơ sở dữ liệu
            TaiKhoan taiKhoan = new TaiKhoan();
            taiKhoan.setTenTaiKhoan(username);
            taiKhoan.setMatKhau(encodedPassword); // Sử dụng mật khẩu đã mã hóa
            taiKhoan.setVaiTro("USER");
            taiKhoanService.saveTaiKhoan(taiKhoan);

            // Gán tài khoản cho khách hàng
            khachHang.setTaiKhoan(taiKhoan);

            // Lưu khách hàng
            khachHangService.register(khachHang);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "GiaoDienChinh/DangKy";
        }
        return "redirect:/DangNhap";
    }

    @GetMapping("/profile")
    public String viewProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        KhachHang khachHang = khachHangService.findByEmail(email);
        if (khachHang == null) {
            model.addAttribute("errorMessage", "Khách hàng không tồn tại");
            return "GiaoDienChinh/XemThongTin"; // Hoặc chuyển hướng đến trang lỗi khác
        }
        model.addAttribute("khachHang", khachHang);
        return "GiaoDienChinh/XemThongTin";
    }

    @PostMapping("/updateProfile")
    public String updateProfile(@ModelAttribute("khachHang") KhachHang khachHang, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "GiaoDienChinh/XemThongTin";
        }

        try {
            // Kiểm tra sự tồn tại của khách hàng
            KhachHang existingKhachHang = khachHangService.getKhachHangById(khachHang.getMaKhachHang());
            if (existingKhachHang == null) {
                model.addAttribute("errorMessage", "Khách hàng không tồn tại");
                return "GiaoDienChinh/XemThongTin";
            }

            // Kiểm tra cập nhật số điện thoại và email
            boolean phoneChanged = !existingKhachHang.getSoDienThoai().equals(khachHang.getSoDienThoai());
            boolean emailChanged = !existingKhachHang.getEmail().equals(khachHang.getEmail());

            // Cập nhật thông tin khách hàng
            existingKhachHang.setTenKhachHang(khachHang.getTenKhachHang());
            existingKhachHang.setSoDienThoai(khachHang.getSoDienThoai());
            existingKhachHang.setEmail(khachHang.getEmail());
            existingKhachHang.setNgaySinh(khachHang.getNgaySinh());
            existingKhachHang.setDiaChi(khachHang.getDiaChi());

            // Cập nhật tên tài khoản và mật khẩu nếu số điện thoại hoặc email thay đổi
            if (phoneChanged || emailChanged) {
                String username = khachHang.getEmail();
                String password = khachHang.getSoDienThoai();
                String encodedPassword = passwordEncoder.encode(password); // Mã hóa mật khẩu

                TaiKhoan taiKhoan = existingKhachHang.getTaiKhoan();
                taiKhoan.setTenTaiKhoan(username);
                taiKhoan.setMatKhau(encodedPassword); // Sử dụng mật khẩu đã mã hóa
                taiKhoanService.saveTaiKhoan(taiKhoan);
            }

            // Lưu khách hàng đã cập nhật
            khachHangService.register(existingKhachHang);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "GiaoDienChinh/XemThongTin";
        }
        return "redirect:/";
    }
}