package vn.toancauxanh.gg.model;

import java.io.IOException;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zul.Window;

import com.fasterxml.jackson.annotation.JsonIgnore;

import vn.toancauxanh.model.Model;

@Entity
@Table(name = "capdonvihanhchinh")
public class CapDonViHanhChinh extends Model<CapDonViHanhChinh> {

	private String ma = "";
	private String ten = "";
	private String tenSearch = "";
	private String moTa = "";
	private String moTaSearch = "";

	public String getMa() {
		return ma;
	}

	public void setMa(String ma) {
		this.ma = ma;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	@JsonIgnore
	public String getTenSearch() {
		return tenSearch;
	}

	public void setTenSearch(String tenSearch) {
		this.tenSearch = tenSearch;
	}

	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

	@JsonIgnore
	public String getMoTaSearch() {
		return moTaSearch;
	}

	public void setMoTaSearch(String moTaSearch) {
		this.moTaSearch = moTaSearch;
	}

	@Transient
	public Long getCapDonViHanhChinhId() {
		return getId();
	}

	@Command
	public void saveCapDonViHanhChinh(@BindingParam("list") final Object listObject,
			@BindingParam("attr") final String attr, @BindingParam("wdn") final Window wdn) throws IOException {
		save();
		wdn.detach();
		BindUtils.postNotifyChange(null, null, listObject, attr);
	}
}