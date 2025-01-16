package com.example.Pet_Clinic.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Pet_Clinic.entity.ThuCung;
import com.example.Pet_Clinic.service.ThuCungService;

@RestController
@RequestMapping("/lichkham")
public class LichKhamJsonController {

    @Autowired
    private ThuCungService thuCungService;

    // API trả về danh sách thú cưng theo mã khách hàng
    @GetMapping("/thucung/{maKhachHang}")
    public List<ThuCung> getThuCungByKhachHang(@PathVariable Long maKhachHang) {
        return thuCungService.getThuCungByKhachHang(maKhachHang);
    }
}

