package vn.toancauxanh.cms.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.MapUtils;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.CapDonViHanhChinh;
import vn.toancauxanh.gg.model.QCapDonViHanhChinh;
import vn.toancauxanh.service.BasicService;

public class CapDonViHanhChinhService extends BasicService<CapDonViHanhChinh> {

	public JPAQuery<CapDonViHanhChinh> getTargetQuery() {
		String paramImage = MapUtils.getString(argDeco(), "tukhoa", "").trim();
		String trangThai = MapUtils.getString(argDeco(), "trangthai", "");
		JPAQuery<CapDonViHanhChinh> q = find(CapDonViHanhChinh.class);

		if (paramImage != null && !paramImage.isEmpty()) {
			String tukhoa = "%" + paramImage + "%";
			q.where(QCapDonViHanhChinh.capDonViHanhChinh.ten.like(tukhoa)
					.or(QCapDonViHanhChinh.capDonViHanhChinh.moTa.like(tukhoa)));
		}
		if (!trangThai.isEmpty()) {
			q.where(QCapDonViHanhChinh.capDonViHanhChinh.trangThai.eq(trangThai));
		}

		q.orderBy(QCapDonViHanhChinh.capDonViHanhChinh.ngaySua.desc());
		return q;
	}

	public List<CapDonViHanhChinh> getListCapDonViHanhChinhs() {
		List<CapDonViHanhChinh> list = new ArrayList<CapDonViHanhChinh>();
		list = find(CapDonViHanhChinh.class).fetch();
		return list;
	}

	public List<CapDonViHanhChinh> getListCapDonViHanhChinhsAndNull() {
		List<CapDonViHanhChinh> list = new ArrayList<CapDonViHanhChinh>();
		list.add(null);
		list.addAll(getListCapDonViHanhChinhs());
		return list;
	}
}
