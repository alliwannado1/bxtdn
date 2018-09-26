package vn.toancauxanh.gg.model;

import java.io.IOException;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.zul.Window;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.model.Model;

@Entity
@Table(name = "donvihanhchinh")
public class DonViHanhChinh extends Model<DonViHanhChinh> {

	private String maTTCP = "";
	private String ma = "";
	private String ten = "";
	private String tenSearch = "";
	private String moTa = "";
	private String moTaSearch = "";
	private DonViHanhChinh cha;
	private CapDonViHanhChinh capDonViHanhChinh;

	public String getMaTTCP() {
		return maTTCP;
	}

	public void setMaTTCP(String maTTCP) {
		this.maTTCP = maTTCP;
	}

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

	@ManyToOne
	public DonViHanhChinh getCha() {
		return cha;
	}

	public void setCha(DonViHanhChinh cha) {
		this.cha = cha;
	}

	@ManyToOne
	public CapDonViHanhChinh getCapDonViHanhChinh() {
		return capDonViHanhChinh;
	}

	public void setCapDonViHanhChinh(CapDonViHanhChinh capDonViHanhChinh) {
		this.capDonViHanhChinh = capDonViHanhChinh;
	}

	@Transient
	public Long getDonViHanhChinhId() {
		return getId();
	}

	@Transient
	public DonViHanhChinh getDonViHanhChinhCha() {
		return getCha();
	}

	@Transient
	public CapDonViHanhChinh getCapDonViHanhChinhDVHC() {
		return getCapDonViHanhChinh();
	}

	@Command
	public void saveDonViHanhChinh(@BindingParam("list") final Object listObject,
			@BindingParam("attr") final String attr, @BindingParam("wdn") final Window wdn) throws IOException {
		save();
		wdn.detach();
		BindUtils.postNotifyChange(null, null, listObject, attr);
	}

	@Transient
	public AbstractValidator getValidatorMa() {
		return new AbstractValidator() {

			@Override
			public void validate(final ValidationContext ctx) {
				String value = (String) ctx.getProperty().getValue();
				if (value == null || "".equals(value)) {
					addInvalidMessage(ctx, "error", "Không dược để trống trường này");
				} else {
					JPAQuery<DonViHanhChinh> q = find(DonViHanhChinh.class)
							.where(QDonViHanhChinh.donViHanhChinh.ma.eq(value));
					if (!DonViHanhChinh.this.noId()) {
						q.where(QDonViHanhChinh.donViHanhChinh.id.ne(getId()));
					}
					if (q.fetchCount() > 0) {
						addInvalidMessage(ctx, "error", "Mã này đã được sử dụng trong hệ thống");
					}
				}
			}
		};
	}
}