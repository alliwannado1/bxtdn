package vn.toancauxanh.cms.service;

import org.apache.commons.collections.MapUtils;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.model.Image;
import vn.toancauxanh.model.QImage;
import vn.toancauxanh.service.BasicService;

public class ImageService extends BasicService<Image> {
	public JPAQuery<Image> getTargetQuery() {
		String paramImage = MapUtils.getString(argDeco(), "tukhoa", "").trim();
		String trangThai = MapUtils.getString(argDeco(), "trangthai", "");

		JPAQuery<Image> q = find(Image.class).where(QImage.image.trangThai.ne(core().TT_DA_XOA));

		if (paramImage != null && !paramImage.isEmpty()) {
			String tukhoa = "%" + paramImage + "%";
			q.where(QImage.image.title.like(tukhoa));
		}
		if (!trangThai.isEmpty()) {
			q.where(QImage.image.trangThai.eq(trangThai));
		}
		q.orderBy(QImage.image.ngayTao.desc());
		return q;
	}
}
