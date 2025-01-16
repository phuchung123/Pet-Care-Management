package com.example.Pet_Clinic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.Pet_Clinic.entity.ThuCung;

public interface ThuCungRepository extends JpaRepository<ThuCung, Long> {
	@Query("SELECT tc FROM ThuCung tc WHERE tc.khachHang.maKhachHang = :maKhachHang")
    List<ThuCung> findByKhachHangId(@Param("maKhachHang") Long maKhachHang);
	List<ThuCung> findByNgayXoaIsNull();
}
