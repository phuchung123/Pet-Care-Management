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
@Table(name = "lichkham")
public class LichKham {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "maLichKham")
	private Long maLichKham;

	@ManyToOne
	@JoinColumn(name = "maThuCung", referencedColumnName = "maThuCung")
	private ThuCung thuCung;

	@ManyToOne
	@JoinColumn(name = "maKhachHang", referencedColumnName = "maKhachHang")
	private KhachHang khachHang;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "ngayTaoLich")
	private Date ngayTaoLich;

	@Column(name = "trangThaiDatLich")
	private String trangThaiDatLich;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "ngayXoa")
	private Date ngayXoa;

	public Long getMaLichKham() {
		return maLichKham;
	}

	public void setMaLichKham(Long maLichKham) {
		this.maLichKham = maLichKham;
	}

	public ThuCung getThuCung() {
		return thuCung;
	}

	public void setThuCung(ThuCung thuCung) {
		this.thuCung = thuCung;
	}

	public KhachHang getKhachHang() {
		return khachHang;
	}

	public void setKhachHang(KhachHang khachHang) {
		this.khachHang = khachHang;
	}

	public Date getNgayTaoLich() {
		return ngayTaoLich;
	}

	public void setNgayTaoLich(Date ngayTaoLich) {
		this.ngayTaoLich = ngayTaoLich;
	}

	public String getTrangThaiDatLich() {
		return trangThaiDatLich;
	}

	public void setTrangThaiDatLich(String trangThaiDatLich) {
		this.trangThaiDatLich = trangThaiDatLich;
	}

	public Date getNgayXoa() {
		return ngayXoa;
	}

	public void setNgayXoa(Date ngayXoa) {
		this.ngayXoa = ngayXoa;
	}

}
