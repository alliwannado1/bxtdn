package vn.toancauxanh.gg.model;

import org.zkoss.zul.Window;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.model.Model;
import vn.toancauxanh.model.NhanVien;
import vn.toancauxanh.model.QNhanVien;

@Entity
@Table(name = "phongban")
public class PhongBan extends Model<PhongBan>{
	
	public PhongBan() {
		
	}
	
	public PhongBan(String ten) {
		this.ten = ten;
	}
	
	private String ten = "";
	private String moTa = "";
	private boolean macDinh;
	public String getTen() {
		return ten;
	}
	public void setTen(String ten) {
		this.ten = ten;
	}
	
	public boolean isMacDinh() {
		return macDinh;
	}

	public void setMacDinh(boolean macDinh) {
		this.macDinh = macDinh;
	}

	public String getMoTa() {
		return moTa;
	}
	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}
	
	@Transient
	public List<NhanVien> getListNhanVien() {
		List<NhanVien> list = new ArrayList<>();
		list = find(NhanVien.class)
				.where(QNhanVien.nhanVien.trangThai.eq(core().TT_AP_DUNG))
				.where(QNhanVien.nhanVien.phongBan.eq(this))
				.fetch();
		return list;
	}
	
	@Command
	public void savePhongBan(@BindingParam("list") final Object listObject, @BindingParam("attr") final String attr,
			@BindingParam("wdn") final Window wdn) throws IOException {	
		JPAQuery<PhongBan> q = find(PhongBan.class)
				.where(QPhongBan.phongBan.ten.eq(ten))
				.where(QPhongBan.phongBan.trangThai.ne(core().TT_DA_XOA));
		if(!this.noId()) {
			q.where(QPhongBan.phongBan.id.ne(this.getId()));
		}
		
		if(q.fetchCount() == 0) {
			save();
			wdn.detach();
			BindUtils.postNotifyChange(null, null, listObject, attr);	
		} else {
			showNotification("Tên đơn vị này đã tồn tại", "", "warning");
		}	
	}
}
