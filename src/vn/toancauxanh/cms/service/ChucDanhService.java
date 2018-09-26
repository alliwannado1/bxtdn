package vn.toancauxanh.cms.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.MapUtils;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.ChucDanh;
import vn.toancauxanh.gg.model.QChucDanh;
import vn.toancauxanh.service.BasicService;

public class ChucDanhService extends BasicService<ChucDanh> {

	public JPAQuery<ChucDanh> getTargetQuery() {
		String keyword = MapUtils.getString(argDeco(), "tukhoa", "").trim();
		String trangThai = MapUtils.getString(argDeco(), "trangthai", "");
		JPAQuery<ChucDanh> q = find(ChucDanh.class);

		if (keyword != null && !keyword.isEmpty()) {
			String tukhoa = "%" + keyword + "%";
			q.where(QChucDanh.chucDanh.ten.like(tukhoa).or(QChucDanh.chucDanh.moTa.like(tukhoa)));
		}
		if (!trangThai.isEmpty()) {
			q.where(QChucDanh.chucDanh.trangThai.eq(trangThai));
		}

		q.orderBy(QChucDanh.chucDanh.ngaySua.desc());
		return q;
	}

	public List<ChucDanh> getListChucDanhs() {
		List<ChucDanh> list = new ArrayList<ChucDanh>();
		list = find(ChucDanh.class).fetch();
		return list;
	}

	public List<ChucDanh> getListChucDanhsAndNull() {
		List<ChucDanh> list = new ArrayList<ChucDanh>();
		list.add(null);
		list.addAll(getListChucDanhs());
		return list;
	}
}
