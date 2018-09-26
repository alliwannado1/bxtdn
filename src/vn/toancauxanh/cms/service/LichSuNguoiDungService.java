package vn.toancauxanh.cms.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.LichSuNguoiDung;
import vn.toancauxanh.gg.model.QLichSuNguoiDung;
import vn.toancauxanh.gg.model.enums.LoaiHanhDongEnum;
import vn.toancauxanh.model.NhanVien;
import vn.toancauxanh.service.BasicService;
import vn.toancauxanh.service.ExcelUtil;

public class LichSuNguoiDungService extends BasicService<LichSuNguoiDung>{
	
	private NhanVien selectedNhanVien;
	
	private LoaiHanhDongEnum selectHanhDong;
	
	public LoaiHanhDongEnum getSelectHanhDong() {
		return selectHanhDong;
	}

	public void setSelectHanhDong(LoaiHanhDongEnum selectHanhDong) {
		this.selectHanhDong = selectHanhDong;
	}

	public NhanVien getSelectedNhanVien() {
		return selectedNhanVien;
	}

	public void setSelectedNhanVien(NhanVien selectedNhanVien) {
		this.selectedNhanVien = selectedNhanVien;
	}

	public List<LichSuNguoiDung> getListAllLichSuNguoiDungs() {
		
		List<LichSuNguoiDung> list = new ArrayList<LichSuNguoiDung>();
		list.addAll(find(LichSuNguoiDung.class)
				.orderBy(QLichSuNguoiDung.lichSuNguoiDung.ngayGio.asc())
				.fetch());
		return list;
	}

	public List<LichSuNguoiDung> getListLichSuNguoiDungsAndNull() {
		List<LichSuNguoiDung> list = new ArrayList<LichSuNguoiDung>();
		list.add(null);
		list.addAll(getListAllLichSuNguoiDungs());
		return list;
	}
	
	public JPAQuery<LichSuNguoiDung> getTargetQuery() {
		JPAQuery<LichSuNguoiDung> query = find(LichSuNguoiDung.class);
		if (getFixTuNgay() != null && getFixDenNgay() == null) {
			query.where(QLichSuNguoiDung.lichSuNguoiDung.ngayGio.after(getFixTuNgay()));
			System.out.println(getFixTuNgay());
		} else if (getFixTuNgay() == null && getFixDenNgay() != null) {
			System.out.println(getFixTuNgay());
			query.where(QLichSuNguoiDung.lichSuNguoiDung.ngayGio.before(getFixDenNgay()));
		} else if (getFixTuNgay() != null && getFixDenNgay() != null) {
			System.out.println(getFixTuNgay());
			query.where(QLichSuNguoiDung.lichSuNguoiDung.ngayGio.between(getFixTuNgay(), getFixDenNgay()));
		}
		
		if (selectHanhDong != null) {
			query.where(QLichSuNguoiDung.lichSuNguoiDung.loaiHanhDong.eq(selectHanhDong));
		}
		
		if (selectedNhanVien != null) {
			query.where(QLichSuNguoiDung.lichSuNguoiDung.nhanVien.eq(selectedNhanVien));
		}
		query.orderBy(QLichSuNguoiDung.lichSuNguoiDung.ngaySua.desc());
		return query;
	}
	
	public List<LoaiHanhDongEnum> getListLoaiHanhDongsAndNull() {
		List<LoaiHanhDongEnum> list = new ArrayList<LoaiHanhDongEnum>();
		list.add(null);
		list.add(LoaiHanhDongEnum.LOGIN);
		list.add(LoaiHanhDongEnum.LOGOUT);
		return list;
	}
	
	@Command
	public void xuatExcel(@BindingParam("query") final JPAQuery<LichSuNguoiDung> query,
			@BindingParam("title") final String title) throws IOException {
		ExcelUtil.exportDanhSachLichSuNguoiDung("danhSachLichSuNguoiDung", "danhSach", query.orderBy(QLichSuNguoiDung.lichSuNguoiDung.nhanVien.tenDangNhap.asc()).fetch(),
				title);
	} 
	
}
