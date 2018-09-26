package vn.toancauxanh.cms.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.MapUtils;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.PhongBan;
import vn.toancauxanh.gg.model.QPhongBan;
import vn.toancauxanh.service.BasicService;

public class PhongBanService extends BasicService<PhongBan> {
	private PhongBan selectedPhongBan;

	public PhongBan getSelectedPhongBan() {
		return selectedPhongBan;
	}

	public void setSelectedPhongBan(PhongBan selectedPhongBan) {
		this.selectedPhongBan = selectedPhongBan;
	}

	public List<PhongBan> getListAllPhongBan() {

		List<PhongBan> list = new ArrayList<PhongBan>();
		list.addAll(find(PhongBan.class).where(QPhongBan.phongBan.trangThai.eq(core().TT_AP_DUNG))
				.orderBy(QPhongBan.phongBan.ten.asc()).fetch());
		return list;
	}

	public List<PhongBan> getListPhongBanAndNull() {
		List<PhongBan> list = new ArrayList<PhongBan>();
		list.add(null);
		list.addAll(getListAllPhongBan());
		return list;
	}

	public JPAQuery<PhongBan> getTargetQuery() {
		String tuKhoa = MapUtils.getString(argDeco(), "tukhoa", "").trim();
		String trangThai = MapUtils.getString(argDeco(), "trangthai", "");

		JPAQuery<PhongBan> query = find(PhongBan.class);

		if (tuKhoa != null && !tuKhoa.isEmpty()) {
			String tukhoa = "%" + tuKhoa + "%";
			query.where(QPhongBan.phongBan.ten.like(tukhoa));
		}
		if (!trangThai.isEmpty()) {
			query.where(QPhongBan.phongBan.trangThai.eq(trangThai));
		}
		query.orderBy(QPhongBan.phongBan.ngaySua.desc());
		return query;
	}

}
