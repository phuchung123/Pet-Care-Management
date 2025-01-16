package com.example.Pet_Clinic.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.Pet_Clinic.entity.KhachHang;
import com.example.Pet_Clinic.entity.ThuCung;
import com.example.Pet_Clinic.service.KhachHangService;
import com.example.Pet_Clinic.service.ThuCungService;

@Controller
@RequestMapping("/thucung")
public class ThuCungController {
    @Autowired
    private ThuCungService thuCungService;

    @Autowired
    private KhachHangService khachHangService;

    @GetMapping("/add")
    public String showAddThuCungForm(Model model) {
        List<KhachHang> khachHangs = khachHangService.getAllKhachHang();
        model.addAttribute("thuCung", new ThuCung());
        model.addAttribute("khachHangs", khachHangs); // Thêm danh sách khách hàng
        return "ThuCung/ThemThuCung"; // Form thêm thú cưng
    }

    @PostMapping("/add")
    public String addThuCung(@ModelAttribute("thuCung") ThuCung thuCung, Model model) throws ParseException {
        if (thuCung.getKhachHang() == null || thuCung.getKhachHang().getMaKhachHang() == null) {
            model.addAttribute("errorMessage", "Khách hàng không hợp lệ.");
            return "ThuCung/ThemThuCung";
        }

        thuCungService.addThuCung(thuCung);
        return "redirect:/thucung/list"; // Chuyển hướng đến danh sách thú cưng
    }
    
    @GetMapping("/list")
    public String listThuCung(Model model) {
        List<ThuCung> thuCungs = thuCungService.getThuCungChuaXoa();
        model.addAttribute("thuCungs", thuCungs);
        return "ThuCung/DanhSachThuCung"; // Trả về view danh sách thú cưng
    }
    
    @GetMapping("/edit/{maThuCung}")
    public String showEditForm(@PathVariable("maThuCung") Long maThuCung, Model model) {
        ThuCung thuCung = thuCungService.getThuCungById(maThuCung);
        model.addAttribute("thuCung", thuCung);
        return "ThuCung/CapNhatThuCung";
    }

    @PostMapping("/update")
    public String updateThuCung(@ModelAttribute("thuCung") ThuCung thuCung) {
        if (thuCung.getKhachHang() == null || thuCung.getKhachHang().getMaKhachHang() == null) {
            return "redirect:/thucung/list";
        }
        KhachHang khachHang = khachHangService.getKhachHangById(thuCung.getKhachHang().getMaKhachHang());
        thuCung.setKhachHang(khachHang);
        thuCungService.updateThuCung(thuCung);
        return "redirect:/thucung/list";
    }
    
    @GetMapping("/delete/{maThuCung}")
    public String deleteThuCung(@PathVariable("maThuCung") Long maThuCung) throws ParseException {
        ThuCung thuCung = thuCungService.getThuCungById(maThuCung);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String formattedDate = sdf.format(new Date());
		Date date = sdf.parse(formattedDate);
        if (thuCung != null) {
            thuCung.setNgayXoa(date);
            thuCungService.updateThuCung(thuCung); 
        }
        return "redirect:/thucung/list";
    }

}
