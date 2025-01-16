package com.example.Pet_Clinic.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "thucung")
public class ThuCung {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maThuCung")
    private Long maThuCung;

    @ManyToOne
    @JoinColumn(name = "maKhachHang", referencedColumnName = "maKhachHang")
    @JsonBackReference
    private KhachHang khachHang;

    @Column(name = "tenThuCung")
    private String tenThuCung;

    @Column(name = "giongLoai")
    private String giongLoai;

    @Column(name = "loai")
    private String loai;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "ngaySinh")
    private Date ngaySinh;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "ngayXoa")
    private Date ngayXoa;

    // Getters và Setters
    public Long getMaThuCung() {
        return maThuCung;
    }

    public void setMaThuCung(Long maThuCung) {
        this.maThuCung = maThuCung;
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }

    public String getTenThuCung() {
        return tenThuCung;
    }

    public void setTenThuCung(String tenThuCung) {
        this.tenThuCung = tenThuCung;
    }

    public String getGiongLoai() {
        return giongLoai;
    }

    public void setGiongLoai(String giongLoai) {
        this.giongLoai = giongLoai;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public Date getNgayXoa() {
        return ngayXoa;
    }

    public void setNgayXoa(Date ngayXoa) {
        this.ngayXoa = ngayXoa;
    }
}
