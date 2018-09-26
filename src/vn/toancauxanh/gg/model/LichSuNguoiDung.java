package vn.toancauxanh.gg.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import vn.toancauxanh.gg.model.enums.LoaiHanhDongEnum;
import vn.toancauxanh.model.Model;
import vn.toancauxanh.model.NhanVien;

@Entity
@Table(name = "lichsunguoidung")
public class LichSuNguoiDung extends Model<LichSuNguoiDung> {

	private Date ngayGio;
	private NhanVien nhanVien;
	private LoaiHanhDongEnum loaiHanhDong;

	public Date getNgayGio() {
		return ngayGio;
	}

	public void setNgayGio(Date ngayGio) {
		this.ngayGio = ngayGio;
	}

	@ManyToOne
	public NhanVien getNhanVien() {
		return nhanVien;
	}

	public void setNhanVien(NhanVien nhanVien) {
		this.nhanVien = nhanVien;
	}

	@Enumerated(EnumType.STRING)
	public LoaiHanhDongEnum getLoaiHanhDong() {
		return loaiHanhDong;
	}

	public void setLoaiHanhDong(LoaiHanhDongEnum loaiHanhDong) {
		this.loaiHanhDong = loaiHanhDong;
	}

}
