package com.example.Pet_Clinic.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "bacsithuy")
public class BacSiThuY {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "maBacSi")
	private Long maBacSi;

	@Column(name = "tenBacSi")
	private String tenBacSi;

	@Column(name = "gioiTinh")
	private String gioiTinh;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "ngaySinh")
	private Date ngaySinh;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "ngayVaoLam")
	private Date ngayVaoLam;

	@Column(name = "soDienThoai")
	private String soDienThoai;

	@Column(name = "email")
	private String email;

	@Column(name = "diaChi")
	private String diaChi;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "ngayXoa")
	private Date ngayXoa;

	public Long getMaBacSi() {
		return maBacSi;
	}

	public void setMaBacSi(Long maBacSi) {
		this.maBacSi = maBacSi;
	}

	public String getTenBacSi() {
		return tenBacSi;
	}

	public void setTenBacSi(String tenBacSi) {
		this.tenBacSi = tenBacSi;
	}

	public String getGioiTinh() {
		return gioiTinh;
	}

	public void setGioiTinh(String gioiTinh) {
		this.gioiTinh = gioiTinh;
	}

	public Date getNgaySinh() {
		return ngaySinh;
	}

	public void setNgaySinh(Date ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	public Date getNgayVaoLam() {
		return ngayVaoLam;
	}

	public void setNgayVaoLam(Date ngayVaoLam) {
		this.ngayVaoLam = ngayVaoLam;
	}

	public String getSoDienThoai() {
		return soDienThoai;
	}

	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public Date getNgayXoa() {
		return ngayXoa;
	}

	public void setNgayXoa(Date ngayXoa) {
		this.ngayXoa = ngayXoa;
	}

}
