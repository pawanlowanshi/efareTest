package com.omniwyse.selenium.test.suites;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.omniwyse.selenium.data.DataManager;
import com.omniwyse.selenium.data.ExcelUtils;
import com.omniwyse.selenium.test.cases.TestCases;

public class TestSuite {
	private int id;
	private String product;
	private String name;
	private List<TestCases> testCases;
	private Boolean result;
	private String platform;

	public TestSuite(String repositoryPath, XSSFRow row) throws Exception {
		id=(int)row.getCell(row.getLastCellNum() - 1).getNumericCellValue();
		product = row.getCell(row.getLastCellNum() - 2).toString();
		platform = row.getCell(2).toString();
		new DataManager().executeMethods(repositoryPath + "Products/", getProduct(),getId());
		System.out.println(repositoryPath + "Products/" + getProduct());
		if ("TestSuite".equals(row.getCell(1).toString())) {
			name = row.getCell(0).toString();
			DataManager.bufferedWriter.write(getName());
			DataManager.bufferedWriter.newLine();
			testCases = executeTestSuite(repositoryPath);
		} else if ("TestCases".equals(row.getCell(1).toString())) {
			name = "Not Applicable";
			DataManager.bufferedWriter.write(getName());
			DataManager.bufferedWriter.newLine();
			System.out.println("directly test case execution..");
			TestCases testCase = null;
			testCases = new ArrayList<TestCases>();
			testCase = new TestCases(repositoryPath, row, getProduct());
			testCases.add(testCase);
		} else {
			throw new RuntimeException("Invalid Entry for Test Type in Test Plan file, Please check and re-enter");
		}
	}

	private List<TestCases> executeTestSuite(String repositoryPath) throws Exception {
		System.out.println("starts suite execution...");
		List<TestCases> testCaseslist = new ArrayList<TestCases>();
		Boolean localResult = true;
		@SuppressWarnings("resource")
		XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(new File(repositoryPath + "Products/" + getProduct() + "/TestSuites/" + getName() + ".xlsx")));
		System.out.println(repositoryPath + "Products/" + getProduct() + "/TestSuites/" + getName() + ".xlsx");
		for (int i = 0; i < wb.getNumberOfSheets(); i++) {
			XSSFSheet sheet = wb.getSheetAt(i);
			for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
				if (!"yes".equalsIgnoreCase(ExcelUtils.getCellValByIndex(sheet, rowIndex, 3))) {
					continue;
				}
				TestCases testCase = new TestCases(repositoryPath, sheet.getRow(rowIndex), getProduct());
				if (testCase.getResult() == false)
					localResult = false;
				testCaseslist.add(testCase);
			}
			result = localResult;
		}
		return testCaseslist;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPlatform() {
		return platform;
	}

	public List<TestCases> getTestCases() {
		return testCases;
	}

	public void setTestCases(List<TestCases> testCases) {
		this.testCases = testCases;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public Boolean getResult() {
		return result;
	}

	public void setResult(Boolean result) {
		this.result = result;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
