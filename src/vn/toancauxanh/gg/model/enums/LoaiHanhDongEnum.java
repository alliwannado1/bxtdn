package vn.toancauxanh.gg.model.enums;

public enum LoaiHanhDongEnum {
	
	LOGIN("Login"),
	LOGOUT("Logout");
	
	LoaiHanhDongEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
