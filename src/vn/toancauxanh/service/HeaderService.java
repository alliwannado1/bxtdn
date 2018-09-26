package vn.toancauxanh.service;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Init;

import vn.toancauxanh.model.NhanVien;

public class HeaderService extends BasicService<NhanVien> {
	private NhanVien user;
	private NhanVien currentUser;
	
	@Init
	public void bootstrap() {
		user = getNhanVien();
	}
	
	public NhanVien getUser() {
		return user;
	}
	
	public NhanVien getCurrentUser() {
		currentUser = getNhanVien();
		user = currentUser;
		BindUtils.postNotifyChange(null, null, currentUser, "user");
		return currentUser;
	}
	
	public void setCurrentUser(NhanVien currentUser) {
		this.currentUser = currentUser;
	}
}
