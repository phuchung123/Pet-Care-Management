package com.example.Pet_Clinic.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.Pet_Clinic.entity.BacSiThuY;
import com.example.Pet_Clinic.repository.BacSiThuYRepository;

import jakarta.persistence.criteria.Predicate;

@Service
public class BacSiThuYService {
	@Autowired
	private BacSiThuYRepository bacSiThuYRepository;

	public BacSiThuY addBacSi(BacSiThuY bacSiThuY) {
		return bacSiThuYRepository.save(bacSiThuY);
	}

	public List<BacSiThuY> getAllBacSi() {
		return bacSiThuYRepository.findByNgayXoaIsNull();
	}

	public BacSiThuY getBacSiById(Long id) {
		return bacSiThuYRepository.findById(id).orElse(null);
	}

	public void updateBacSi(BacSiThuY bacSi) {
		bacSiThuYRepository.save(bacSi);
	}

	public boolean isEmailExist(String email) {
		return bacSiThuYRepository.findByEmail(email).isPresent();
	}

	public boolean isSoDienThoaiExist(String soDienThoai) {
		return bacSiThuYRepository.findBySoDienThoai(soDienThoai).isPresent();
	}

	public void deleteBacSi(Long id) throws ParseException {
		Optional<BacSiThuY> optionalBacSi = bacSiThuYRepository.findById(id);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String formattedDate = sdf.format(new Date());
		Date date = sdf.parse(formattedDate);
		if (optionalBacSi.isPresent()) {
			BacSiThuY bacSi = optionalBacSi.get();
			bacSi.setNgayXoa(date);
			bacSiThuYRepository.save(bacSi);
		}
	}

	public List<BacSiThuY> searchBacSi(String tenBacSi, String gioiTinh, Date ngaySinhFrom, Date ngaySinhTo,
			Date ngayVaoLamFrom, Date ngayVaoLamTo) {
		Specification<BacSiThuY> spec = (root, query, cb) -> {
			Predicate p = cb.conjunction();

			// Tìm kiếm theo tên bác sĩ
			if (tenBacSi != null && !tenBacSi.isEmpty()) {
				p = cb.and(p, cb.like(root.get("tenBacSi"), "%" + tenBacSi + "%"));
			}

			// Tìm kiếm theo giới tính
			if (gioiTinh != null && !gioiTinh.isEmpty()) {
				p = cb.and(p, cb.equal(root.get("gioiTinh"), gioiTinh));
			}

			// Tìm kiếm theo ngày sinh trong khoảng
			if (ngaySinhFrom != null && ngaySinhTo != null) {
				p = cb.and(p, cb.between(root.get("ngaySinh"), ngaySinhFrom, ngaySinhTo));
			} else if (ngaySinhFrom != null) {
				p = cb.and(p, cb.greaterThanOrEqualTo(root.get("ngaySinh"), ngaySinhFrom));
			} else if (ngaySinhTo != null) {
				p = cb.and(p, cb.lessThanOrEqualTo(root.get("ngaySinh"), ngaySinhTo));
			}

			// Tìm kiếm theo ngày vào làm trong khoảng
			if (ngayVaoLamFrom != null && ngayVaoLamTo != null) {
				p = cb.and(p, cb.between(root.get("ngayVaoLam"), ngayVaoLamFrom, ngayVaoLamTo));
			} else if (ngayVaoLamFrom != null) {
				p = cb.and(p, cb.greaterThanOrEqualTo(root.get("ngayVaoLam"), ngayVaoLamFrom));
			} else if (ngayVaoLamTo != null) {
				p = cb.and(p, cb.lessThanOrEqualTo(root.get("ngayVaoLam"), ngayVaoLamTo));
			}

			// Lọc theo bác sĩ chưa bị xóa
			p = cb.and(p, cb.isNull(root.get("ngayXoa")));

			return p;
		};
		return bacSiThuYRepository.findAll(spec);
	}
}
