package com.example.Pet_Clinic.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Pet_Clinic.entity.BacSiThuY;
import com.example.Pet_Clinic.service.BacSiThuYService;

@Controller
@RequestMapping("/bacsi")
public class BacSiThuYController {

	@Autowired
	private BacSiThuYService bacSiThuYService;

	@GetMapping("/add")
	public String showAddBacSiForm(Model model) {
		model.addAttribute("bacSi", new BacSiThuY());
		return "BacSiThuY/ThemBacSiThuY";
	}

	@PostMapping("/add")
	public String addBacSi(@ModelAttribute("bacSi") BacSiThuY bacSi, Model model) throws ParseException {
		if (bacSiThuYService.isSoDienThoaiExist(bacSi.getSoDienThoai())) {
			model.addAttribute("errorMessage", "Số điện thoại đã tồn tại.");
			return "BacSiThuY/ThemBacSiThuY";
		}
		
		if (bacSiThuYService.isEmailExist(bacSi.getEmail())) {
			model.addAttribute("errorMessage", "Email đã tồn tại.");
			return "BacSiThuY/ThemBacSiThuY";
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String formattedDate = sdf.format(new Date());
		Date date = sdf.parse(formattedDate);
		bacSi.setNgayVaoLam(date);
		bacSiThuYService.addBacSi(bacSi);
		return "redirect:/bacsi/list";
	}

	@GetMapping("/list")
	public String getAllBacSi(Model model) {
		List<BacSiThuY> danhSachBacSi = bacSiThuYService.getAllBacSi();
		model.addAttribute("danhSachBacSi", danhSachBacSi);
		return "BacSiThuY/DanhSachBacSiThuY";
	}

	@GetMapping("/edit/{id}")
	public String showEditForm(@PathVariable("id") Long id, Model model) {
		BacSiThuY bacSi = bacSiThuYService.getBacSiById(id);
		model.addAttribute("bacSi", bacSi);
		return "BacSiThuY/ChinhSuaThongTinBacSi";
	}

	@PostMapping("/edit/{id}")
	public String updateBacSi(@PathVariable("id") Long id, @ModelAttribute BacSiThuY bacSi) {
		bacSi.setMaBacSi(id);
		bacSiThuYService.updateBacSi(bacSi);
		return "redirect:/bacsi/list";
	}

	@GetMapping("/delete/{id}")
	public String softDeleteBacSi(@PathVariable("id") Long id) throws ParseException {
		bacSiThuYService.deleteBacSi(id);
		return "redirect:/bacsi/list";
	}

	@GetMapping("/search")
	public String searchBacSi(@RequestParam(required = false) String tenBacSi,
			@RequestParam(required = false) String gioiTinh,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date ngaySinhFrom,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date ngaySinhTo,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date ngayVaoLamFrom,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date ngayVaoLamTo, Model model) {

		List<BacSiThuY> danhSachBacSi = bacSiThuYService.searchBacSi(tenBacSi, gioiTinh, ngaySinhFrom, ngaySinhTo,
				ngayVaoLamFrom, ngayVaoLamTo);
		model.addAttribute("danhSachBacSi", danhSachBacSi);
		return "BacSiThuY/DanhSachBacSiThuY";
	}
}
