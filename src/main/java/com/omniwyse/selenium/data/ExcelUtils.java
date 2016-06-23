package com.omniwyse.selenium.data;

import java.util.Date;

import org.apache.poi.hssf.util.AreaReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

	public static String getCellValByIndex(XSSFSheet sheet, int rowIndex, int colIndex) {
		return sheet.getRow(rowIndex).getCell(colIndex).toString();
	}

	public static String getFormulaCellValByIndex(XSSFSheet sheet, int rowIndex, int colIndex) {
		return sheet.getRow(rowIndex).getCell(colIndex).getNumericCellValue() + "";
	}

	public static Cell getCellByIndex(XSSFSheet sheet, int rowIndex, int colIndex) {
		return sheet.getRow(rowIndex).getCell(colIndex);
	}

	public static Boolean getCellBooleanByIndex(XSSFSheet sheet, int rowIndex, int colIndex) {
		if ("pass".equalsIgnoreCase(sheet.getRow(rowIndex).getCell(colIndex).toString()) || "true".equalsIgnoreCase(sheet.getRow(rowIndex).getCell(colIndex).toString()))

			return true;
		else
			return false;
	}

	public static Boolean getBooleanValue(XSSFRow row) {
		if (row.getCell(1) != null && "pass".equalsIgnoreCase(row.getCell(1).toString()) || "true".equalsIgnoreCase(row.getCell(1).toString()))
			return true;
		else
			return false;
	}

	public static Date getCellDateByIndex(XSSFSheet sheet, int rowIndex, int colIndex) {
		return sheet.getRow(rowIndex).getCell(colIndex).getDateCellValue();
	}

	public static void setExcelCellDate(XSSFSheet sheet, int rowIndex, int colIndex, Date value) {
		sheet.getRow(rowIndex).getCell(colIndex).setCellValue(value);
	}

	public static void setExcelCellValue(XSSFSheet sheet, int rowIndex, int colIndex, String value) {
		XSSFRow row = sheet.getRow(rowIndex);
		Cell cell;
		if (row != null) {
			cell = row.getCell(colIndex);
		} else {
			cell = sheet.createRow(rowIndex).getCell(colIndex);
		}
		if (cell != null) {
			cell.setCellValue(value);
		} else {
			sheet.getRow(rowIndex).createCell(colIndex).setCellValue(value);
		}
	}

	public static String getCellValByName(XSSFWorkbook wb, XSSFSheet sheet, int rowIndex, String name) {
		return sheet.getRow(rowIndex).getCell(new AreaReference(wb.getNameAt(wb.getNameIndex(name)).getRefersToFormula()).getFirstCell().getCol()).toString();
	}

	public static Cell getCellByName(XSSFWorkbook wb, XSSFSheet sheet, int rowIndex, String name) {
		return sheet.getRow(rowIndex).getCell(new AreaReference(wb.getNameAt(wb.getNameIndex(name)).getRefersToFormula()).getFirstCell().getCol());
	}
}
