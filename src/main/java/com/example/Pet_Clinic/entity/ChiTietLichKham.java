package com.example.Pet_Clinic.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "chitietlichkham")
public class ChiTietLichKham {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "maLichKham", referencedColumnName = "maLichKham")
	private LichKham lichKham;

	@ManyToOne
	@JoinColumn(name = "maThuCung", referencedColumnName = "maThuCung")
	private ThuCung thuCung;

	@ManyToOne
	@JoinColumn(name = "maBacSi", referencedColumnName = "maBacSi")
	private BacSiThuY bacSiThuY;

	@ManyToOne
	@JoinColumn(name = "maKhachHang", referencedColumnName = "maKhachHang")
	private KhachHang khachHang;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "ngayKham")
	private Date ngayKham;

	@Column(name = "tinhTrangSucKhoe")
	private String tinhTrangSucKhoe;

	@Column(name = "chanDoanHuongDan")
	private String chanDoanHuongDan;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "ngayXoa")
	private Date ngayXoa;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LichKham getLichKham() {
		return lichKham;
	}

	public void setLichKham(LichKham lichKham) {
		this.lichKham = lichKham;
	}

	public ThuCung getThuCung() {
		return thuCung;
	}

	public void setThuCung(ThuCung thuCung) {
		this.thuCung = thuCung;
	}

	public BacSiThuY getBacSiThuY() {
		return bacSiThuY;
	}

	public void setBacSiThuY(BacSiThuY bacSiThuY) {
		this.bacSiThuY = bacSiThuY;
	}

	public KhachHang getKhachHang() {
		return khachHang;
	}

	public void setKhachHang(KhachHang khachHang) {
		this.khachHang = khachHang;
	}

	public Date getNgayKham() {
		return ngayKham;
	}

	public void setNgayKham(Date ngayKham) {
		this.ngayKham = ngayKham;
	}

	public String getTinhTrangSucKhoe() {
		return tinhTrangSucKhoe;
	}

	public void setTinhTrangSucKhoe(String tinhTrangSucKhoe) {
		this.tinhTrangSucKhoe = tinhTrangSucKhoe;
	}

	public String getChanDoanHuongDan() {
		return chanDoanHuongDan;
	}

	public void setChanDoanHuongDan(String chanDoanHuongDan) {
		this.chanDoanHuongDan = chanDoanHuongDan;
	}

	public Date getNgayXoa() {
		return ngayXoa;
	}

	public void setNgayXoa(Date ngayXoa) {
		this.ngayXoa = ngayXoa;
	}

}
