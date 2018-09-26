package vn.toancauxanh.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.zkoss.zul.Filedownload;

import vn.toancauxanh.gg.model.LichSuNguoiDung;

public class ExcelUtil {

	private static ExcelUtil instance;

	public static ExcelUtil getInStance() {
		if (instance == null) {
			instance = new ExcelUtil();
		}
		return instance;
	}

	public static String formatDouble(double number) {
		// #.###,##
		final DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
		decimalFormatSymbols.setDecimalSeparator(',');
		decimalFormatSymbols.setGroupingSeparator('.');
		final DecimalFormat decimalFormat = new DecimalFormat("#.###", decimalFormatSymbols);
		return decimalFormat.format(number);
	}

	private static void setBorderMore(Workbook wb, Row row, Cell c, int begin, int end, int fontSize) {
		final CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setWrapText(true);
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyle.setBorderLeft((short) 1);
		Font font = wb.createFont();
		font.setFontName("Times New Roman");

	}

	private static void setBorderMore(int flag, Workbook wb, Row row, Cell c, int begin, int end, int fontSize) {
		final CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setWrapText(true);
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyle.setBorderLeft((short) 1);

		Font font = wb.createFont();
		font.setFontName("Times New Roman");
		for (int i = begin; i < end; i++) {
			Cell c1 = row.createCell(i);
			if (flag == 1) {
				cellStyle.setBorderTop((short) 2);
				font.setFontHeightInPoints((short) fontSize);
				cellStyle.setFont(font);
			} else {
				cellStyle.setBorderTop((short) 1);
			}
			if (flag == 2) {
				cellStyle.setBorderBottom((short) 2);
				font.setFontHeightInPoints((short) fontSize);
				cellStyle.setFont(font);
			} else {
				cellStyle.setBorderBottom((short) 1);
			}

			if (i == (end - 1)) {
				cellStyle.setBorderRight((short) 2);
			} else {
				cellStyle.setBorderRight((short) 1);
			}
			if (flag == 3) {
				cellStyle.setBorderTop((short) 1);
				cellStyle.setBorderBottom((short) 1);
				font.setFontHeightInPoints((short) fontSize);
				cellStyle.setFont(font);
				cellStyle.setBorderRight((short) 1);
				font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			}
			c1.setCellStyle(cellStyle);
		}
	}

	public static CellStyle setBorderAndFont(final Workbook workbook, final int borderSize, final boolean isTitle,
			final int fontSize, final String fontColor, final String textAlign) {
		final CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setWrapText(true);

		cellStyle.setBorderTop((short) borderSize); // single line border
		cellStyle.setBorderBottom((short) borderSize); // single line border
		cellStyle.setBorderLeft((short) borderSize); // single line border
		cellStyle.setBorderRight((short) borderSize); // single line border
		cellStyle.setAlignment(CellStyle.VERTICAL_CENTER);

		if (textAlign.equals("RIGHT")) {
			cellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
		} else if (textAlign.equals("CENTER")) {
			cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		} else if (textAlign.equals("LEFT")) {
			cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
		}
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

		final Font font = workbook.createFont();
		font.setFontName("Times New Roman");
		if (isTitle) {

			font.setBoldweight(Font.BOLDWEIGHT_BOLD);

		}
		if (fontColor.equals("RED")) {
			font.setColor(Font.COLOR_RED);
		} else if (fontColor.equals("BLUE")) {
			font.setColor(HSSFColor.BLUE.index);
		} else if (fontColor.equals("ORANGE")) {
			font.setColor(HSSFColor.ORANGE.index);
		} else {

		}
		font.setFontHeightInPoints((short) fontSize);
		cellStyle.setFont(font);

		return cellStyle;
	}

	// Danh sách Thống Kê lịch sử người dùng
	public static void exportDanhSachLichSuNguoiDung(String fileName, String sheetName, List<LichSuNguoiDung> list,
			String title) throws IOException {
		// New Workbook
		Workbook wb = new XSSFWorkbook();
		try {
			Cell c = null;
			// New Sheet
			Sheet sheet1 = null;
			sheet1 = wb.createSheet(sheetName);
			sheet1.getPrintSetup().setLandscape(true);
			sheet1.getPrintSetup().setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
			sheet1.createFreezePane(0, 3);

			CellStyle styleLeft = setBorderAndFont(wb, 1, false, 8, "", "LEFT");
			CellStyle styleCenter = setBorderAndFont(wb, 1, false, 8, "", "CENTER");
			CellStyle styleLeftHeader = setBorderAndFont(wb, 1, true, 9, "", "LEFT");
			CellStyle styleCenterHeader = setBorderAndFont(wb, 1, true, 9, "", "CENTER");
			// Row and column indexes
			int idx = 0;
			// Generate column headings
			Row row;
			row = sheet1.createRow(idx);
			c = row.createCell(0);
			c.setCellValue("DANH SÁCH " + title.toUpperCase());
			c.setCellStyle(setBorderAndFont(wb, 0, true, 14, "BLUE", "CENTER"));
			sheet1.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));

			// set column width

			sheet1.setColumnWidth(0, 10 * 256);
			sheet1.setColumnWidth(1, 30 * 256);
			sheet1.setColumnWidth(2, 30 * 256);
			sheet1.setColumnWidth(3, 30 * 256);

			// Generate rows header of grid
			idx++;
			idx++;
			row = sheet1.createRow(idx);
			idx++;

			int sttHeader = 0;

			c = row.createCell(sttHeader);
			sttHeader++;

			c.setCellValue("STT");
			c.setCellStyle(styleCenterHeader);
			c = row.createCell(sttHeader);
			sttHeader++;

			c.setCellValue("Họ và tên");
			c.setCellStyle(styleCenterHeader);
			c = row.createCell(sttHeader);
			sttHeader++;

			c.setCellValue("Loại hành động");
			c.setCellStyle(styleCenterHeader);
			c = row.createCell(sttHeader);
			sttHeader++;

			c.setCellValue("Ngày giờ");
			c.setCellStyle(styleCenterHeader);
			c = row.createCell(sttHeader);
			sttHeader++;

			int i = 1;
			DateFormat df = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
			for (LichSuNguoiDung lichSu : list) {
				row = sheet1.createRow(idx);
				int sttData = 0;

				c = row.createCell(sttData);
				sttData++;
				c.setCellValue(i);
				c.setCellStyle(styleCenter);
				c = row.createCell(sttData);
				sttData++;

				c.setCellValue(lichSu.getNhanVien().getTenDangNhap().toUpperCase());
				c.setCellStyle(styleLeft);
				c = row.createCell(sttData);
				sttData++;

				c.setCellValue(lichSu.getLoaiHanhDong().getText());
				c.setCellStyle(styleCenter);
				c = row.createCell(sttData);
				sttData++;

				c.setCellValue(df.format(lichSu.getNgayGio()));
				c.setCellStyle(styleCenter);
				c = row.createCell(sttData);
				sttData++;

				i++;
				idx++;
			}

			idx++;
			// createNoteRow(wb, sheet1, idx);
			idx++;

			ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
			wb.write(fileOut);
			Filedownload.save(new ByteArrayInputStream(fileOut.toByteArray()), "application/octet-stream",
					fileName + ".xlsx");
		} finally {
			wb.close();
		}
	}

	@SuppressWarnings("deprecation")
	private static void setBorderMerge(Workbook mWb, Sheet sheet, CellRangeAddress rangeAdd) {
		RegionUtil.setBorderBottom(CellStyle.BORDER_THIN, rangeAdd, sheet, mWb);
		RegionUtil.setBorderLeft(CellStyle.BORDER_THIN, rangeAdd, sheet, mWb);
		RegionUtil.setBorderRight(CellStyle.BORDER_THIN, rangeAdd, sheet, mWb);
		RegionUtil.setBorderTop(CellStyle.BORDER_THIN, rangeAdd, sheet, mWb);
	}
}