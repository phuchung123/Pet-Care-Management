package com.example.Pet_Clinic.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.Pet_Clinic.entity.BacSiThuY;
import com.example.Pet_Clinic.entity.ChiTietLichKham;
import com.example.Pet_Clinic.entity.KhachHang;
import com.example.Pet_Clinic.entity.LichKham;
import com.example.Pet_Clinic.entity.ThuCung;
import com.example.Pet_Clinic.service.BacSiThuYService;
import com.example.Pet_Clinic.service.ChiTietLichKhamService;
import com.example.Pet_Clinic.service.KhachHangService;
import com.example.Pet_Clinic.service.LichKhamService;
import com.example.Pet_Clinic.service.ThuCungService;

@Controller
@RequestMapping("/lichkham")
public class LichKhamController {
	@Autowired
	private LichKhamService lichKhamService;

	@Autowired
	private ThuCungService thuCungService;

	@Autowired
	private KhachHangService khachHangService;

	@Autowired
	private BacSiThuYService bacSiThuYService;

	@Autowired
	private ChiTietLichKhamService chiTietLichKhamService;

	@GetMapping("/book")
	public String showBookForm(Model model) {
		List<ThuCung> thuCungList = thuCungService.getAllThuCung();
		List<KhachHang> khachHangList = khachHangService.getAllKhachHang();

		model.addAttribute("thuCungList", thuCungList);
		model.addAttribute("khachHangList", khachHangList);

		return "LichKham/DatLichKham";
	}

	@PostMapping("/book")
	public String book(@RequestParam Long maThuCung, @RequestParam Long maKhachHang,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Date ngayTaoLich, Model model) {

		try {
			LichKham lichKham = lichKhamService.datLichKham(maThuCung, maKhachHang, ngayTaoLich);
			model.addAttribute("message", "Đặt lịch thành công!");
			model.addAttribute("lichKham", lichKham);
		} catch (Exception e) {
			model.addAttribute("message", "Đặt lịch thất bại! Vui lòng kiểm tra lại thông tin.");
		}

		model.addAttribute("thuCungList", thuCungService.getAllThuCung());
		model.addAttribute("khachHangList", khachHangService.getAllKhachHang());

		return "LichKham/DatLichKham";
	}

	@GetMapping("/list")
	public String lichKhamList(Model model) {
		List<LichKham> lichKhams = lichKhamService.getAllLichKham();
		List<Boolean> chiTietExistFlags = lichKhams.stream()
				.map(lichKham -> lichKhamService.checkChiTietLichKhamExists(lichKham.getMaLichKham()))
				.collect(Collectors.toList());
		model.addAttribute("lichKhams", lichKhams);
		model.addAttribute("chiTietExistFlags", chiTietExistFlags);
		return "LichKham/DanhSachLichKham";
	}

	@PostMapping("/update-status")
	public String updateBookStatus(@RequestParam Long maLichKham, @RequestParam String trangThaiMoi,
			RedirectAttributes redirectAttributes) {
		try {
			lichKhamService.updateBookStatus(maLichKham, trangThaiMoi);
			redirectAttributes.addFlashAttribute("message", "Cập nhật trạng thái lịch khám thành công!");
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra khi cập nhật trạng thái lịch khám.");
		}
		return "redirect:/lichkham/list";
	}

	@GetMapping("/details")
	public String showDetailsForm(@RequestParam Long maLichKham, Model model) {
		// Lấy thông tin lịch khám từ database
		LichKham lichKham = lichKhamService.getLichKhamById(maLichKham);
		List<BacSiThuY> bacSiThuYList = bacSiThuYService.getAllBacSi(); // Service để lấy danh sách bác sĩ

		model.addAttribute("lichKham", lichKham);
		model.addAttribute("bacSiThuYList", bacSiThuYList);
		return "ChiTietLichKham/DatLichChiTiet";
	}

	@PostMapping("/details")
	public String submitDetailsForm(@RequestParam Long maLichKham, @RequestParam Long maBacSi,
			@RequestParam String tinhTrangSucKhoe, @RequestParam String chanDoanHuongDan,
			RedirectAttributes redirectAttributes) {
		try {
			lichKhamService.saveChiTietLichKham(maLichKham, maBacSi, tinhTrangSucKhoe, chanDoanHuongDan);
			redirectAttributes.addFlashAttribute("message", "Đặt lịch chi tiết thành công!");
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra khi đặt lịch chi tiết.");
		}
		return "redirect:/lichkham/list";
	}

	@GetMapping("/details/{maLichKham}")
	public String xemChiTietLichKham(@PathVariable("maLichKham") Long maLichKham, Model model) {
		List<ChiTietLichKham> chiTietLichKhamList = chiTietLichKhamService.getByMaLichKham(maLichKham);
		model.addAttribute("chiTietLichKhamList", chiTietLichKhamList);
		return "ChiTietLichKham/ChiTietLichKham";
	}

	// Hiển thị form sửa thông tin chi tiết lịch khám
	@GetMapping("/details/edit/{id}")
	public String editChiTietLichKham(@PathVariable("id") Long id, Model model) {
		ChiTietLichKham chiTiet = chiTietLichKhamService.getById(id);
		model.addAttribute("chiTiet", chiTiet);
		return "ChiTietLichKham/SuaThongTinChiTietLichKham";
	}

	// Xử lý việc cập nhật thông tin chi tiết lịch khám
	@PostMapping("/details/update")
	public String updateChiTietLichKham(@RequestParam("id") Long id,
			@RequestParam("ngayKham") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Date ngayKham,
			@RequestParam("tinhTrangSucKhoe") String tinhTrangSucKhoe,
			@RequestParam("chanDoanHuongDan") String chanDoanHuongDan, Model model) {
		ChiTietLichKham chiTiet = chiTietLichKhamService.getById(id);
		chiTiet.setNgayKham(ngayKham);
		chiTiet.setTinhTrangSucKhoe(tinhTrangSucKhoe);
		chiTiet.setChanDoanHuongDan(chanDoanHuongDan);
		chiTietLichKhamService.save(chiTiet);

		model.addAttribute("message", "Thông tin đã được cập nhật");
		return "redirect:/lichkham/list";
	}

	@GetMapping("/search")
	public String searchLichKham(@RequestParam(required = false) String tenKhachHang,
			@RequestParam(required = false) String tenThuCung,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date ngayTaoLichFrom,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date ngayTaoLichTo,
			@RequestParam(required = false) String trangThai, Model model) {
		List<LichKham> lichKhams = lichKhamService.searchLichKham(tenKhachHang, tenThuCung, ngayTaoLichFrom,
				ngayTaoLichTo, trangThai);
		List<Boolean> chiTietExistFlags = lichKhams.stream()
				.map(lichKham -> lichKhamService.checkChiTietLichKhamExists(lichKham.getMaLichKham()))
				.collect(Collectors.toList());
		model.addAttribute("chiTietExistFlags", chiTietExistFlags);
		model.addAttribute("lichKhams", lichKhams);
		return "LichKham/DanhSachLichKham";
	}

}
