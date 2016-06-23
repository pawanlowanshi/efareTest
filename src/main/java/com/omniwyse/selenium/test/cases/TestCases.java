package com.omniwyse.selenium.test.cases;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.omniwyse.selenium.data.DataManager;
import com.omniwyse.selenium.data.ExcelUtils;

public class TestCases {
	private List<TestCaseStep> testCaseStep;
	private String path;
	private Boolean expectedResult;
	private Boolean result = true;
	private String id;

	public TestCases(String repositoryPath, XSSFRow row, String product) throws Exception {
		id = row.getCell(0).toString();
		DataManager.bufferedWriter.write("," + getId());
		DataManager.bufferedWriter.newLine();
		path = row.getCell(1).toString();
		expectedResult = DataManager.getBooleanValue(row);
		testCaseStep = executeTestCase(repositoryPath, product);
	}

	private List<TestCaseStep> executeTestCase(String repositoryPath, String product) throws Exception {
		System.out.println("current test case is " + getId());
		List<TestCaseStep> testCaseStepList = new ArrayList<TestCaseStep>();
		Boolean localResult = true;
		@SuppressWarnings("resource")
		XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(new File(repositoryPath + "Products/" + product + "/TestCases/" + getId() + ".xlsx")));
		System.out.println(repositoryPath + "Products/" + product + "/TestCases/" + getId() + ".xlsx");
		for (int i = 0; i < wb.getNumberOfSheets(); i++) {
			XSSFSheet sheet = wb.getSheetAt(i);
			for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
				if (!"yes".equalsIgnoreCase(ExcelUtils.getCellValByIndex(sheet, rowIndex, 4))) {
					continue;
				}
				TestCaseStep testCaseStep = new TestCaseStep(repositoryPath, sheet.getRow(rowIndex), getId(), product);
				if (!testCaseStep.getActualResult().equals(testCaseStep.getExpectedResult()))
					localResult = false;
				testCaseStepList.add(testCaseStep);
				if (localResult == false)
					break;
			}
			setResult(localResult);
		}
		return testCaseStepList;
	}

	public List<TestCaseStep> getTestCaseSteps() {
		return testCaseStep;
	}

	public void setTestCaseData(List<TestCaseStep> testCaseStep) {
		this.testCaseStep = testCaseStep;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Boolean getExpectedResult() {
		return expectedResult;
	}

	public void setExpectedResult(Boolean expectedResult) {
		this.expectedResult = expectedResult;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Boolean getResult() {
		return result;
	}

	public void setResult(Boolean result) {
		this.result = result;
	}

}
