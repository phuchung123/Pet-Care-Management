package com.example.Pet_Clinic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Pet_Clinic.entity.KhachHang;
import com.example.Pet_Clinic.entity.TaiKhoan;
import com.example.Pet_Clinic.repository.KhachHangRepository;
import com.example.Pet_Clinic.repository.TaiKhoanRepository;

import jakarta.transaction.Transactional;

@Service
public class KhachHangService {

    @Autowired
    private KhachHangRepository khachHangRepository;
    
    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<KhachHang> getAllKhachHang() {
        return khachHangRepository.findAll();
    }
    
    public List<KhachHang> searchNhanVienByName(String keyword) {
        return khachHangRepository.findByTenKhachHangContaining(keyword);
    }
    
    public void register(KhachHang khachHang) {
        // Save TaiKhoan first if it's new
        TaiKhoan taiKhoan = khachHang.getTaiKhoan();
        if (taiKhoan != null && taiKhoan.getMaTaiKhoan() == null) {
            taiKhoanRepository.save(taiKhoan);
        }
        khachHangRepository.save(khachHang);
    }
    
    public boolean existsByEmail(String email) {
        return khachHangRepository.existsByEmail(email);
    }
    
    public boolean existsBySoDienThoai(String soDienThoai) {
        return khachHangRepository.existsBySoDienThoai(soDienThoai);
    }
    
    public KhachHang getKhachHangById(Long id) {
        return khachHangRepository.findById(id).orElse(null);
    }
    
    public KhachHang findByEmail(String email) {
    	return khachHangRepository.findByEmail(email);
    }
    
    @Transactional
    public void deleteKhachHang(Long id) {
        KhachHang khachHang = khachHangRepository.findById(id).orElse(null);
        if (khachHang != null) {
            TaiKhoan taiKhoan = khachHang.getTaiKhoan();
            khachHangRepository.deleteById(id);
            if (taiKhoan != null) {
                taiKhoanRepository.delete(taiKhoan);
            }
        }
    }
}
