package vn.toancauxanh.cms.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.MapUtils;
import org.zkoss.bind.BindUtils;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.CapDonViHanhChinh;
import vn.toancauxanh.gg.model.DonViHanhChinh;
import vn.toancauxanh.gg.model.QDonViHanhChinh;
import vn.toancauxanh.service.BasicService;

public class DonViHanhChinhService extends BasicService<DonViHanhChinh> {

	private CapDonViHanhChinh selectedCapDonViHanhChinh;
	private DonViHanhChinh selectedDVHCQuanHuyen;
	private DonViHanhChinh selectedDVHCTinhThanh;
	private DonViHanhChinh selectedDVHCPhuongXa;

	public CapDonViHanhChinh getSelectedCapDonViHanhChinh() {
		return selectedCapDonViHanhChinh;
	}

	public void setSelectedCapDonViHanhChinh(CapDonViHanhChinh selectedCapDonViHanhChinh) {
		this.selectedCapDonViHanhChinh = selectedCapDonViHanhChinh;
	}

	public DonViHanhChinh getSelectedDVHCQuanHuyen() {
		return selectedDVHCQuanHuyen;
	}

	public void setSelectedDVHCQuanHuyen(DonViHanhChinh selectedDVHCQuanHuyen) {
		this.selectedDVHCQuanHuyen = selectedDVHCQuanHuyen;
		BindUtils.postNotifyChange(null, null, this, "listPhuongXaDaNang");
		BindUtils.postNotifyChange(null, null, this, "listPhuongXaDaNangAndNull");
		BindUtils.postNotifyChange(null, null, this, "listPhuongXaDaNangAndNull2");
	}

	public DonViHanhChinh getSelectedDVHCTinhThanh() {
		return selectedDVHCTinhThanh;
	}

	public void setSelectedDVHCTinhThanh(DonViHanhChinh selectedDVHCTinhThanh) {
		this.selectedDVHCTinhThanh = selectedDVHCTinhThanh;
		BindUtils.postNotifyChange(null, null, this, "listQuanHuyen");
		BindUtils.postNotifyChange(null, null, this, "listQuanHuyenAndNull");
		BindUtils.postNotifyChange(null, null, this, "listQuanHuyenAndNull2");
	}

	public DonViHanhChinh getSelectedDVHCPhuongXa() {
		return selectedDVHCPhuongXa;
	}

	public void setSelectedDVHCPhuongXa(DonViHanhChinh selectedDVHCPhuongXa) {
		this.selectedDVHCPhuongXa = selectedDVHCPhuongXa;
		BindUtils.postNotifyChange(null, null, this, "listToDanPho");
	}

	public List<DonViHanhChinh> getListTinhThanh() {
		List<DonViHanhChinh> list = new ArrayList<DonViHanhChinh>();
		list = find(DonViHanhChinh.class).where(QDonViHanhChinh.donViHanhChinh.cha.isNull())
				.orderBy(QDonViHanhChinh.donViHanhChinh.ten.asc()).fetch();
		return list;
	}

	public List<DonViHanhChinh> getListTinhThanhAndNull() {
		List<DonViHanhChinh> list = new ArrayList<DonViHanhChinh>();
		list.add(null);
		list.addAll(getListTinhThanh());
		return list;
	}

	public List<DonViHanhChinh> getListTinhThanhAndNull2() {
		List<DonViHanhChinh> list = new ArrayList<DonViHanhChinh>();
		list.add(null);
		DonViHanhChinh donViHanhChinh = new DonViHanhChinh();
		donViHanhChinh.setTen("Chưa nhập thông tin");
		donViHanhChinh.setId(0L);
		list.add(donViHanhChinh);
		DonViHanhChinh ngoaiTinh = new DonViHanhChinh();
		ngoaiTinh.setTen("Ngoại tỉnh");
		ngoaiTinh.setId(1000L);
		list.add(ngoaiTinh);
		list.addAll(getListTinhThanh());
		return list;
	}

	public List<DonViHanhChinh> getListQuanHuyen() {
		List<DonViHanhChinh> list = new ArrayList<DonViHanhChinh>();
		if (selectedDVHCTinhThanh != null && !selectedDVHCTinhThanh.getId().equals(0L)) {
			list = find(DonViHanhChinh.class).where(QDonViHanhChinh.donViHanhChinh.cha.eq(selectedDVHCTinhThanh))
					.orderBy(QDonViHanhChinh.donViHanhChinh.ten.asc()).fetch();
		}
		return list;
	}

	public List<DonViHanhChinh> getListQuanHuyenAndNull2() {
		List<DonViHanhChinh> list = new ArrayList<DonViHanhChinh>();
		list.add(null);
		DonViHanhChinh donViHanhChinh = new DonViHanhChinh();
		donViHanhChinh.setTen("Chưa nhập thông tin");
		donViHanhChinh.setId(0L);
		list.add(donViHanhChinh);
		list.addAll(getListQuanHuyen());
		return list;
	}

	public List<DonViHanhChinh> getListQuanHuyenAndNull() {
		List<DonViHanhChinh> list = new ArrayList<DonViHanhChinh>();
		list.add(null);
		list.addAll(getListQuanHuyen());
		return list;
	}

	public List<DonViHanhChinh> getListQuanHuyenDaNang() {
		List<DonViHanhChinh> list = new ArrayList<DonViHanhChinh>();
		list = find(DonViHanhChinh.class).where(QDonViHanhChinh.donViHanhChinh.cha.ma.eq("danang"))
				.orderBy(QDonViHanhChinh.donViHanhChinh.ma.asc()).fetch();
		return list;
	}

	public List<DonViHanhChinh> getListDonViCon(DonViHanhChinh cha) {
		List<DonViHanhChinh> list = new ArrayList<DonViHanhChinh>();
		list = find(DonViHanhChinh.class).where(QDonViHanhChinh.donViHanhChinh.cha.eq(cha))
				.orderBy(QDonViHanhChinh.donViHanhChinh.ten.asc()).fetch();
		return list;
	}

	public long countDonViCon(DonViHanhChinh cha) {
		JPAQuery<DonViHanhChinh> q = find(DonViHanhChinh.class).where(QDonViHanhChinh.donViHanhChinh.cha.eq(cha));
		return q.fetchCount();
	}

	public DonViHanhChinh getDonViDaNang() {
		DonViHanhChinh donVi = null;
		Long idDanang = new ThamSoService().getIdDonViHanhChinhDaNang();
		donVi = find(DonViHanhChinh.class).where(QDonViHanhChinh.donViHanhChinh.id.eq(idDanang)).fetchFirst();
		return donVi;
	}

	public List<DonViHanhChinh> getListDonViDaNang() {
		List<DonViHanhChinh> list = new ArrayList<DonViHanhChinh>();
		list.add(getDonViDaNang());
		return list;
	}

	public List<DonViHanhChinh> getListQuanHuyenDaNangAndNull() {
		List<DonViHanhChinh> list = new ArrayList<DonViHanhChinh>();
		list.add(null);
		list.addAll(getListQuanHuyenDaNang());
		return list;
	}

	public List<DonViHanhChinh> getListPhuongXaDaNang() {
		List<DonViHanhChinh> list = new ArrayList<DonViHanhChinh>();
		if (selectedDVHCQuanHuyen != null && !selectedDVHCQuanHuyen.getId().equals(0L)) {
			list = find(DonViHanhChinh.class).where(QDonViHanhChinh.donViHanhChinh.cha.eq(selectedDVHCQuanHuyen))
					.orderBy(QDonViHanhChinh.donViHanhChinh.ten.asc()).fetch();
		}
		return list;
	}

	public List<DonViHanhChinh> getListPhuongXaDaNangAndNull() {
		List<DonViHanhChinh> list = new ArrayList<DonViHanhChinh>();
		list.add(null);
		list.addAll(getListPhuongXaDaNang());
		return list;
	}

	public List<DonViHanhChinh> getListPhuongXaDaNangAndNull2() {
		List<DonViHanhChinh> list = new ArrayList<DonViHanhChinh>();
		list.add(null);
		DonViHanhChinh donViHanhChinh = new DonViHanhChinh();
		donViHanhChinh.setTen("Chưa nhập thông tin");
		donViHanhChinh.setId(0L);
		list.add(donViHanhChinh);
		list.addAll(getListPhuongXaDaNang());
		return list;
	}

	public JPAQuery<DonViHanhChinh> getTargetQuery() {
		String paramImage = MapUtils.getString(argDeco(), "tukhoa", "").trim();
		String trangThai = MapUtils.getString(argDeco(), "trangthai", "");
		JPAQuery<DonViHanhChinh> q = find(DonViHanhChinh.class);

		if (paramImage != null && !paramImage.isEmpty()) {
			String tukhoa = "%" + paramImage + "%";
			q.where(QDonViHanhChinh.donViHanhChinh.ten.like(tukhoa)
					.or(QDonViHanhChinh.donViHanhChinh.moTa.like(tukhoa)));
		}
		if (!trangThai.isEmpty()) {
			q.where(QDonViHanhChinh.donViHanhChinh.trangThai.eq(trangThai));
		}
		if (selectedCapDonViHanhChinh != null) {
			q.where(QDonViHanhChinh.donViHanhChinh.capDonViHanhChinh.eq(selectedCapDonViHanhChinh));
		}
		q.orderBy(QDonViHanhChinh.donViHanhChinh.ma.asc());
		q.orderBy(QDonViHanhChinh.donViHanhChinh.ngaySua.desc());
		return q;
	}
}