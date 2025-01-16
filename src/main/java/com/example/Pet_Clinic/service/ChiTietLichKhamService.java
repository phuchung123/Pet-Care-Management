package com.example.Pet_Clinic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Pet_Clinic.entity.ChiTietLichKham;
import com.example.Pet_Clinic.repository.ChiTietLichKhamRepository;

@Service
public class ChiTietLichKhamService {

    @Autowired
    private ChiTietLichKhamRepository chiTietLichKhamRepository;

    public List<ChiTietLichKham> getByMaLichKham(Long maLichKham) {
        return chiTietLichKhamRepository.findByLichKhamMaLichKham(maLichKham);
    }

    public ChiTietLichKham getById(Long id) {
        return chiTietLichKhamRepository.findById(id).orElse(null);
    }

    public void save(ChiTietLichKham chiTietLichKham) {
        chiTietLichKhamRepository.save(chiTietLichKham);
    }
}

