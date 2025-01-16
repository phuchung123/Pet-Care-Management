package com.example.Pet_Clinic.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.Pet_Clinic.entity.BacSiThuY;

@Repository
public interface BacSiThuYRepository extends JpaRepository<BacSiThuY, Long>, JpaSpecificationExecutor<BacSiThuY> {
    List<BacSiThuY> findByNgayXoaIsNull();
    Optional<BacSiThuY> findBySoDienThoai(String soDienThoai);
    Optional<BacSiThuY> findByEmail(String email);
}