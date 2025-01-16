package com.example.Pet_Clinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Pet_Clinic.entity.TaiKhoan;

@Repository
public interface TaiKhoanRepository extends JpaRepository<TaiKhoan, Long> {
	TaiKhoan findByTenTaiKhoan(String tenTaiKhoan);
}
