package com.example.Pet_Clinic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Pet_Clinic.entity.TaiKhoan;
import com.example.Pet_Clinic.repository.TaiKhoanRepository;

@Service
public class TaiKhoanService {
    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    public List<TaiKhoan> getAllTaiKhoans() {
        return taiKhoanRepository.findAll();
    }

    public TaiKhoan getTaiKhoanById(Long maTaiKhoan) {
        return taiKhoanRepository.findById(maTaiKhoan).orElse(null);
    }

    public TaiKhoan saveTaiKhoan(TaiKhoan taiKhoan) {
        return taiKhoanRepository.save(taiKhoan);
    }

    public void deleteTaiKhoan(Long maTaiKhoan) {
        taiKhoanRepository.deleteById(maTaiKhoan);
    }
}
