package com.example.Pet_Clinic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Pet_Clinic.entity.KhachHang;

public interface KhachHangRepository extends JpaRepository<KhachHang, Long> {
	List<KhachHang> findByTenKhachHangContaining(String keyword);
	KhachHang findByEmail(String email);
    boolean existsBySoDienThoai(String soDienThoai);
    boolean existsByEmail(String email);
}
