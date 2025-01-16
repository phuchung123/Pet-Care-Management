package com.example.Pet_Clinic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Pet_Clinic.entity.ThuCung;
import com.example.Pet_Clinic.repository.ThuCungRepository;

@Service
public class ThuCungService {
	@Autowired
	private ThuCungRepository thuCungRepository;

	public List<ThuCung> getAllThuCung() {
		return thuCungRepository.findAll();
	}

	public List<ThuCung> getThuCungByKhachHang(Long maKhachHang) {
		return thuCungRepository.findByKhachHangId(maKhachHang);
	}

	public void addThuCung(ThuCung thuCung) {
		thuCungRepository.save(thuCung);
	}

	public List<ThuCung> getThuCungChuaXoa() {
		return thuCungRepository.findByNgayXoaIsNull();
	}

	// Lấy thú cưng theo ID
	public ThuCung getThuCungById(Long maThuCung) {
		return thuCungRepository.findById(maThuCung).orElse(null);
	}

	// Cập nhật thông tin thú cưng
	public void updateThuCung(ThuCung thuCung) {
		thuCungRepository.save(thuCung); 
	}
}
