package com.example.Pet_Clinic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.Pet_Clinic.entity.LichKham;

@Repository
public interface LichKhamRepository extends JpaRepository<LichKham, Long>, JpaSpecificationExecutor<LichKham> {
	@Query("SELECT lk FROM LichKham lk JOIN FETCH lk.khachHang kh JOIN FETCH lk.thuCung tc")
    List<LichKham> findAllWithDetails();
	List<LichKham> findByMaLichKham(Long maLichKham);
}
