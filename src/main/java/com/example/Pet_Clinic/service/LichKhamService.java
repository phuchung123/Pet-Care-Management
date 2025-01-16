package com.example.Pet_Clinic.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.Pet_Clinic.entity.BacSiThuY;
import com.example.Pet_Clinic.entity.ChiTietLichKham;
import com.example.Pet_Clinic.entity.KhachHang;
import com.example.Pet_Clinic.entity.LichKham;
import com.example.Pet_Clinic.entity.ThuCung;
import com.example.Pet_Clinic.repository.BacSiThuYRepository;
import com.example.Pet_Clinic.repository.ChiTietLichKhamRepository;
import com.example.Pet_Clinic.repository.LichKhamRepository;

import jakarta.persistence.criteria.Predicate;

@Service
public class LichKhamService {

    @Autowired
    private LichKhamRepository lichKhamRepository;
    
    @Autowired
    private BacSiThuYRepository bacSiThuYRepository;
    
    @Autowired
    private ChiTietLichKhamRepository chiTietLichKhamRepository;

    public LichKham datLichKham(Long maThuCung, Long maKhachHang, Date ngayTaoLich) {
        LichKham lichKham = new LichKham();
        lichKham.setThuCung(new ThuCung());
        lichKham.getThuCung().setMaThuCung(maThuCung);

        lichKham.setKhachHang(new KhachHang());
        lichKham.getKhachHang().setMaKhachHang(maKhachHang);

        lichKham.setNgayTaoLich(ngayTaoLich);
        lichKham.setTrangThaiDatLich("Chờ duyệt");

        return lichKhamRepository.save(lichKham);
    }
    
    public List<LichKham> getAllLichKham() {
        return lichKhamRepository.findAllWithDetails();
    }
    
	public void updateBookStatus(Long maLichKham, String trangThaiMoi) {
		Optional<LichKham> lichKhamOptional = lichKhamRepository.findById(maLichKham);
		if (lichKhamOptional.isPresent()) {
			LichKham lichKham = lichKhamOptional.get();
			lichKham.setTrangThaiDatLich(trangThaiMoi);
			lichKhamRepository.save(lichKham);
		} else {
			throw new RuntimeException("Không tìm thấy lịch khám với mã: " + maLichKham);
		}
	}
	
	public LichKham getLichKhamById(Long maLichKham) {
	    return lichKhamRepository.findById(maLichKham)
	            .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch khám với mã: " + maLichKham));
	}

	public boolean checkChiTietLichKhamExists(Long maLichKham) {
	    List<ChiTietLichKham> chiTietList = chiTietLichKhamRepository.findByLichKhamMaLichKham(maLichKham);
	    return !chiTietList.isEmpty();
	}
	
	public void saveChiTietLichKham(Long maLichKham, Long maBacSi, String tinhTrangSucKhoe, String chanDoanHuongDan) {
		if (checkChiTietLichKhamExists(maLichKham)) {
	        throw new RuntimeException("Chi tiết lịch khám đã tồn tại cho lịch khám này.");
	    }
	    LichKham lichKham = getLichKhamById(maLichKham);
	    BacSiThuY bacSiThuY = bacSiThuYRepository.findById(maBacSi)
	            .orElseThrow(() -> new RuntimeException("Không tìm thấy bác sĩ với mã: " + maBacSi));

	    ChiTietLichKham chiTietLichKham = new ChiTietLichKham();
	    chiTietLichKham.setLichKham(lichKham);
	    chiTietLichKham.setThuCung(lichKham.getThuCung());
	    chiTietLichKham.setKhachHang(lichKham.getKhachHang());
	    chiTietLichKham.setBacSiThuY(bacSiThuY);
	    chiTietLichKham.setTinhTrangSucKhoe(tinhTrangSucKhoe);
	    chiTietLichKham.setChanDoanHuongDan(chanDoanHuongDan);
	    chiTietLichKham.setNgayKham(lichKham.getNgayTaoLich());

	    chiTietLichKhamRepository.save(chiTietLichKham);
	}

	public List<LichKham> searchLichKham(String tenKhachHang, String tenThuCung, Date ngayTaoLichFrom, Date ngayTaoLichTo, String trangThai) {
	    Specification<LichKham> spec = (root, query, cb) -> {
	        Predicate p = cb.conjunction();

	        if (tenKhachHang != null && !tenKhachHang.isEmpty()) {
	            p = cb.and(p, cb.like(root.get("khachHang").get("tenKhachHang"), "%" + tenKhachHang + "%"));
	        }

	        if (tenThuCung != null && !tenThuCung.isEmpty()) {
	            p = cb.and(p, cb.like(root.get("thuCung").get("tenThuCung"), "%" + tenThuCung + "%"));
	        }

	        if (ngayTaoLichFrom != null && ngayTaoLichTo != null) {
	            p = cb.and(p, cb.between(root.get("ngayTaoLich"), ngayTaoLichFrom, ngayTaoLichTo));
	        } else if (ngayTaoLichFrom != null) {
	            p = cb.and(p, cb.greaterThanOrEqualTo(root.get("ngayTaoLich"), ngayTaoLichFrom));
	        } else if (ngayTaoLichTo != null) {
	            p = cb.and(p, cb.lessThanOrEqualTo(root.get("ngayTaoLich"), ngayTaoLichTo));
	        }

	        if (trangThai != null && !trangThai.isEmpty()) {
	            p = cb.and(p, cb.equal(root.get("trangThaiDatLich"), trangThai));
	        }

	        p = cb.and(p, cb.isNull(root.get("ngayXoa")));
	        return p;
	    };

	    return lichKhamRepository.findAll(spec);
	}

	public List<LichKham> searchByMaLichKham(Long maLichKham) {
	    if (maLichKham != null) {
	        return lichKhamRepository.findByMaLichKham(maLichKham); // Dùng phương thức này để tìm kiếm theo mã lịch khám
	    } else {
	        return lichKhamRepository.findAll(); // Nếu không có mã, trả về tất cả lịch khám
	    }
	}

	
}
