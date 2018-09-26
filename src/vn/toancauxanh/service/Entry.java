
package vn.toancauxanh.service;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.zkoss.util.resource.Labels;
import org.zkoss.zhtml.Object;

import vn.toancauxanh.cms.service.CapDonViHanhChinhService;
import vn.toancauxanh.cms.service.ChucDanhService;
import vn.toancauxanh.cms.service.DonViHanhChinhService;
import vn.toancauxanh.cms.service.DonViService;
import vn.toancauxanh.cms.service.ImageService;
import vn.toancauxanh.cms.service.LanguageService;
import vn.toancauxanh.cms.service.LichSuNguoiDungService;
import vn.toancauxanh.cms.service.PhongBanService;
import vn.toancauxanh.model.GopYPhanMem;
import vn.toancauxanh.model.VaiTro;

@SuppressWarnings("deprecation")
@Configuration
@Controller
public class Entry extends BaseObject<Object> {
	static Entry instance;

	@Value("${trangthai.apdung}")
	public String TT_AP_DUNG = "";
	@Value("${trangthai.daxoa}")
	public String TT_DA_XOA = "";
	@Value("${trangthai.khongapdung}")
	public String TT_KHONG_AP_DUNG = "";

	// No image url
	public String URL_M_NOIMAGE = "/assetsfe/images/lg_noimage.png";
	public String URL_S_NOIMAGE = "/assetsfe/images/sm_noimage.png";

	@Value("${action.xem}")
	public String XEM = ""; // duoc xem bat ky
	@Value("${action.list}")
	public String LIST = ""; // duoc vao trang list search url
	@Value("${action.sua}")
	public String SUA = ""; // duoc sua bat ky
	@Value("${action.xoa}")
	public String XOA = ""; // duoc xoa bat ky
	@Value("${action.them}")
	public String THEM = ""; // duoc them

	@Value("${url.nguoidung}")
	public String NGUOIDUNG = "";
	@Value("${url.phongban}")
	public String PHONGBAN = "";

	@Value("${url.chucdanh}")
	public String CHUCDANH = "";
	@Value("${url.capdonvihanhchinh}")
	public String CAPDONVIHANHCHINH = "";
	@Value("${url.donvihanhchinh}")
	public String DONVIHANHCHINH = "";
	@Value("${url.vaitro}")
	public String VAITRO = "";

	@Value("${url.thongbaochung}")
	public String THONGBAOCHUNG = "";

	@Value("${url.quantrihethong}")
	public String QUANTRIHETHONG = "";

	// uend
	public char CHAR_CACH = ':';
	public String CACH = CHAR_CACH + "";

	@Value("${url.vaitro}" + ":" + "${action.xem}")
	public String VAITROXEM;
	@Value("${url.vaitro}" + ":" + "${action.them}")
	public String VAITROTHEM = "";
	@Value("${url.vaitro}" + ":" + "${action.list}")
	public String VAITROLIST = "";
	@Value("${url.vaitro}" + ":" + "${action.xoa}")
	public String VAITROXOA = "";
	@Value("${url.vaitro}" + ":" + "${action.sua}")
	public String VAITROSUA = "";

	@Value("${url.nguoidung}" + ":" + "${action.xem}")
	public String NGUOIDUNGXEM = "";
	@Value("${url.nguoidung}" + ":" + "${action.them}")
	public String NGUOIDUNGTHEM = "";
	@Value("${url.nguoidung}" + ":" + "${action.list}")
	public String NGUOIDUNGLIST = "";
	@Value("${url.nguoidung}" + ":" + "${action.xoa}")
	public String NGUOIDUNGXOA = "";
	@Value("${url.nguoidung}" + ":" + "${action.sua}")
	public String NGUOIDUNGSUA = "";

	@Value("${url.donvi}" + ":" + "${action.xem}")
	public String DONVIXEM = "";
	@Value("${url.donvi}" + ":" + "${action.them}")
	public String DONVITHEM = "";
	@Value("${url.donvi}" + ":" + "${action.list}")
	public String DONVILIST = "";
	@Value("${url.donvi}" + ":" + "${action.xoa}")
	public String DONVIXOA = "";
	@Value("${url.donvi}" + ":" + "${action.sua}")
	public String DONVISUA = "";

	@Value("${url.donvihanhchinh}" + ":" + "${action.xem}")
	public String DONVIHANHCHINHXEM = "";
	@Value("${url.donvihanhchinh}" + ":" + "${action.them}")
	public String DONVIHANHCHINHTHEM = "";
	@Value("${url.donvihanhchinh}" + ":" + "${action.list}")
	public String DONVIHANHCHINHLIST = "";
	@Value("${url.donvihanhchinh}" + ":" + "${action.xoa}")
	public String DONVIHANHCHINHXOA = "";
	@Value("${url.donvihanhchinh}" + ":" + "${action.sua}")
	public String DONVIHANHCHINHSUA = "";

	@Value("${url.capdonvihanhchinh}" + ":" + "${action.xem}")
	public String CAPDONVIHANHCHINHXEM = "";
	@Value("${url.capdonvihanhchinh}" + ":" + "${action.them}")
	public String CAPDONVIHANHCHINHTHEM = "";
	@Value("${url.capdonvihanhchinh}" + ":" + "${action.list}")
	public String CAPDONVIHANHCHINHLIST = "";
	@Value("${url.capdonvihanhchinh}" + ":" + "${action.xoa}")
	public String CAPDONVIHANHCHINHXOA = "";
	@Value("${url.capdonvihanhchinh}" + ":" + "${action.sua}")
	public String CAPDONVIHANHCHINHSUA = "";

	@Value("${url.cauhinhhethong}")
	public String CAUHINHHETHONG = "";

	@Value("${url.chucdanh}" + ":" + "${action.xem}")
	public String CHUCDANHXEM = "";
	@Value("${url.chucdanh}" + ":" + "${action.them}")
	public String CHUCDANHTHEM = "";
	@Value("${url.chucdanh}" + ":" + "${action.list}")
	public String CHUCDANHLIST = "";
	@Value("${url.chucdanh}" + ":" + "${action.xoa}")
	public String CHUCDANHXOA = "";
	@Value("${url.chucdanh}" + ":" + "${action.sua}")
	public String CHUCDANHSUA = "";

	@Value("${url.phongban}" + ":" + "${action.xem}")
	public String PHONGBANXEM = "";
	@Value("${url.phongban}" + ":" + "${action.them}")
	public String PHONGBANTHEM = "";
	@Value("${url.phongban}" + ":" + "${action.list}")
	public String PHONGBANLIST = "";
	@Value("${url.phongban}" + ":" + "${action.xoa}")
	public String PHONGBANXOA = "";
	@Value("${url.phongban}" + ":" + "${action.sua}")
	public String PHONGBANSUA = "";

	@Value("${url.thongbaochung}" + ":" + "${action.xem}")
	public String THONGBAOCHUNGXEM = "";
	@Value("${url.thongbaochung}" + ":" + "${action.list}")
	public String THONGBAOCHUNGLIST = "";

	@Value("${url.cauhinhhethong}" + ":" + "${action.list}")
	public String CAUHINHHETHONGLIST = "";
	@Value("${url.cauhinhhethong}" + ":" + "${action.sua}")
	public String CAUHINHHETHONGSUA = "";

	@Value("${url.quantrihethong}" + ":" + "${action.list}")
	public String QUANTRIHETHONGLIST = "";

	// aend
	public String[] getRESOURCES() {
		return new String[] { NGUOIDUNG, PHONGBAN, VAITRO, CHUCDANH, CAPDONVIHANHCHINH, DONVIHANHCHINH, VAITRO,
				THONGBAOCHUNG, CAUHINHHETHONG };
	}

	public String[] getACTIONS() {
		return new String[] { LIST, XEM, THEM, SUA, XOA };
	}

	static {
		File file = new File(Labels.getLabel("filestore.root") + File.separator + Labels.getLabel("filestore.folder"));
		if (!file.exists()) {
			if (file.mkdir()) {
				System.out.println("Directory mis is created!");
			} else {
				System.out.println("Failed to create directory!");
			}
		}
	}
	
	@Autowired
	public Environment env;

	@Autowired
	DataSource dataSource;

	public Entry() {
		super();
		setCore();
		instance = this;
	}

	@Bean
	public FilterRegistrationBean cacheFilter() {
		FilterRegistrationBean rs = new FilterRegistrationBean(new CacheFilter());
		rs.addUrlPatterns("*.css");
		rs.addUrlPatterns("*.js");
		rs.addUrlPatterns("*.wpd");
		rs.addUrlPatterns("*.wcs");
		rs.addUrlPatterns("*.jpg");
		rs.addUrlPatterns("*.jpeg");
		rs.addUrlPatterns("*.png");
		rs.addUrlPatterns("*.svg");
		rs.addUrlPatterns("*.gif");
		return rs;
	}

	// BE
	@RequestMapping(value = "/")
	public String home() {
		return "forward:/WEB-INF/zul/home.zhtml?resource=nguoidung&action=lietke&file=/WEB-INF/zul/nguoidung/list.zul";
	}
	
	@RequestMapping(value = "/cp")
	public String cp() {
		return "forward:/WEB-INF/zul/home.zhtml?resource=nguoidung&action=lietke&file=/WEB-INF/zul/nguoidung/list.zul";
	}

	@RequestMapping(value = "/cpo/{path:.+$}")
	public String cp2(@PathVariable String path) {
		return "forward:/WEB-INF/zul/home.zhtml?resource=" + path + "&action=lietke&file=/WEB-INF/zul/" + path
				+ "/list.zul";
	}

	@RequestMapping(value = "/{path:.+$}")
	public String cp(@PathVariable String path) {
		System.out.println("đã vào 33333");
		return "forward:/WEB-INF/zul/home.zhtml?resource=" + path + "&action=lietke&file=/WEB-INF/zul/" + path
				+ "/list.zul";
	}

	@RequestMapping(value = "/sso-error")
	public String sso() {
		return "forward:/WEB-INF/zul/error-sso.zul";
	}

	@RequestMapping(value = "/dang-nhap-sso")
	public String loginSSO(HttpServletRequest request, HttpServletResponse response) {
		return "forward:/WEB-INF/zul/dang-nhap-sso.zul";
	}

	@RequestMapping(value = "/cp/{path:.+$}")
	public String cpAdmin(@PathVariable String path) {
		System.out.println("đẫ vào 22222");
		return "forward:/WEB-INF/zul/home.zhtml?resource=" + path + "&action=lietke&file=/WEB-INF/zul/" + path
				+ "/list.zul";
	}

	@RequestMapping(value = "/{path:.+$}/add")
	public String cpAdd(@PathVariable String path) {
		return "forward:/WEB-INF/zul/home.zhtml?resource=" + path + "&action=them&file=/WEB-INF/zul/" + path
				+ "/add-view.zhtml";
	}

	@RequestMapping(value = "/{path:.+$}/them-moi")
	public String cpAdd2(@PathVariable String path) {
		return "forward:/WEB-INF/zul/home.zhtml?resource=" + path + "&action=them&file=/WEB-INF/zul/" + path
				+ "/add.zul";
	}

	@RequestMapping(value = "/{path:.+$}/chinh-sua/{id:\\d+}")
	public String cpEdit(@PathVariable String path, @PathVariable long id) {
		return "forward:/WEB-INF/zul/home.zhtml?resource=" + path + "&action=sua&file=/WEB-INF/zul/" + path
				+ "/edit.zul&id=" + id;
	}

	@RequestMapping(value = "/{path:.+$}/chi-tiet/{id:\\d+}")
	public String cpDetail(@PathVariable String path, @PathVariable long id) {
		return "forward:/WEB-INF/zul/home.zhtml?resource=" + path + "&action=xem&file=/WEB-INF/zul/" + path
				+ "/detail.zul&id=" + id;
	}

	@RequestMapping(value = "/login")
	public String dangNhapBackend() {
		return "forward:/WEB-INF/zul/login.zul";
	}

	@RequestMapping(value = "/auth/logout")
	public void dangXuatBackend(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null) {
			response.sendRedirect(request.getContextPath() + "/cas/login");
		} else {
			new NhanVienService().logoutNotRedirect(request, response);
		}
	}

	public final DonViService getDonVis() {
		return new DonViService();
	}

	public final Quyen getQuyen() {
		return getNhanVien().getTatCaQuyen();
	}

	public final NhanVienService getNhanVienService() {
		return new NhanVienService();
	}

	public final VaiTroService getVaiTros() {
		return new VaiTroService();
	}

	public final DonViHanhChinhService getDonViHanhChinhs() {
		return new DonViHanhChinhService();
	}

	public final CapDonViHanhChinhService getCapDonViHanhChinhs() {
		return new CapDonViHanhChinhService();
	}

	public final ChucDanhService getChucDanhs() {
		return new ChucDanhService();
	}

	public final PhongBanService getPhongBans() {
		return new PhongBanService();
	}

	public final LichSuNguoiDungService getLichSuNguoiDungs() {
		return new LichSuNguoiDungService();
	}

	public GopYPhanMem getGopYPhanMem() {
		return new GopYPhanMem();
	}

	public final ImageService getImages() {
		return new ImageService();
	}

	public final LanguageService getLanguages() {
		return new LanguageService();
	}

	public boolean checkVaiTro(String vaiTro) {
		if (vaiTro == null || vaiTro.isEmpty()) {
			return false;
		}
		boolean rs = false;
		for (VaiTro vt : getNhanVien().getVaiTros()) {
			if (vaiTro.equals(vt.getAlias())) {
				rs = true;
				break;
			}
		}
		return rs;// || getQuyen().get(vaiTro);
	}

	@Configuration
	@EnableWebMvc
	public static class MvcConfig extends WebMvcConfigurerAdapter {
		@Override
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
			registry.addResourceHandler("/files/**").addResourceLocations("file:/home/vhttdata/hdndfiles/");
			registry.addResourceHandler("/assetsfe/**").addResourceLocations("/assetsfe/");
			registry.addResourceHandler("/backend/**").addResourceLocations("/backend/");
			registry.addResourceHandler("/img/**").addResourceLocations("/img/");
			registry.addResourceHandler("/login/**").addResourceLocations("/login/");
		}

		@Override
		public void configureViewResolvers(final ViewResolverRegistry registry) {
			registry.jsp("/WEB-INF/", "*");
		}

		@ExceptionHandler(ResourceNotFoundException.class)
		@ResponseStatus(HttpStatus.NOT_FOUND)
		public String handleResourceNotFoundException() {
			return "forward:/WEB-INF/zul/notfound.zul";
		}
	}

}