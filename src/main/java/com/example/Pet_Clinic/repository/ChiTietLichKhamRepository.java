package com.example.Pet_Clinic.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Pet_Clinic.entity.ChiTietLichKham;

@Repository
public interface ChiTietLichKhamRepository extends JpaRepository<ChiTietLichKham, Long>{
	List<ChiTietLichKham> findByLichKhamMaLichKham(Long maLichKham);
	Optional<ChiTietLichKham> findById(Long id);
}
