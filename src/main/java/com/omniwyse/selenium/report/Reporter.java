package com.omniwyse.selenium.report;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;

import com.omniwyse.selenium.data.DataManager;
import com.omniwyse.selenium.driver.SeleniumFramework;
import com.omniwyse.selenium.test.cases.TestCaseStep;
import com.omniwyse.selenium.test.cases.TestCases;
import com.omniwyse.selenium.test.suites.TestSuite;

public class Reporter {

	private static final String resultPlaceholder = "<!-- INSERT_RESULTS -->";
	private static final String currentHeading = "Test results";
	private static final String currentProduct = "ProductName";
	private static final String currentDateandTime = "Date&Time";
	private static String templatePath /*
																			 * =
																			 * "D:/oWyseTestBase/Products/efare/Results/"
																			 * + "TestPlan_Report.html"
																			 */;
	static String reportIn;

	public static void writeReport(String repositoryPath, List<TestSuite> testSuites, String currentDate) throws Exception {
		for (int i = 0; i < testSuites.size(); i++) {
			templatePath = repositoryPath + "Products/" + testSuites.get(i).getProduct() + "/Results/" + "TestPlan_Report.html";
			reportIn = new String(Files.readAllBytes(Paths.get(templatePath)));
			reportIn = reportIn.replaceFirst(currentHeading, "Report of TestPlan");
			reportIn = reportIn.replaceFirst(currentDateandTime, currentDate);
			reportIn = reportIn.replaceFirst(currentProduct, testSuites.get(i).getProduct());
			if (testSuites.get(i).getName().equals("Not Applicable")) {
				reportIn = reportIn.replaceFirst(resultPlaceholder,
						"<tr><td bgcolor='#0066ff'>" + testSuites.get(i).getName()
								+ "</td><td bgcolor='#ffffff'></td><td bgcolor='#ffffff'></td><td bgcolor='#ffffff'></td><td bgcolor='#ffff99'><b>Not Applicable</b></font></td></tr>"
								+ resultPlaceholder);
			} else {
				if (testSuites.get(i).getResult() == true)
					reportIn = reportIn.replaceFirst(resultPlaceholder,
							"<tr><td bgcolor='#0066ff'>" + testSuites.get(i).getName()
									+ "</td><td bgcolor='#ffffff'></td><td bgcolor='#ffffff'></td><td bgcolor='#ffffff'></td><td><font color='#00cc00'><b>PASSED</b></font></td></tr>"
									+ resultPlaceholder);
				else
					reportIn = reportIn.replaceFirst(resultPlaceholder,
							"<tr><td bgcolor='#0066ff'>" + testSuites.get(i).getName()
									+ "</td><td bgcolor='#ffffff'></td><td bgcolor='#ffffff'></td><td bgcolor='#ffffff'></td><td><font color='#cc3300'><b>FAILED</b></font></td></tr>"
									+ resultPlaceholder);
			}

			for (int j = 0; j < testSuites.get(i).getTestCases().size(); j++) {
				TestCases testCase = testSuites.get(i).getTestCases().get(j);
				if (testCase.getResult() == true)
					reportIn = reportIn.replaceFirst(resultPlaceholder, "<tr><td bgcolor='#ffffff'></td><td bgcolor='#ff9900'>" + testCase.getId()
							+ "</td><td bgcolor='#ffffff'></td><td bgcolor='#ffffff'></td><td><font color='#00cc00'><b>PASSED</b></font></td></tr>" + resultPlaceholder);
				else
					reportIn = reportIn.replaceFirst(resultPlaceholder, "<tr><td bgcolor='#ffffff'></td><td bgcolor='#ff9900'>" + testCase.getId()
							+ "</td><td bgcolor='#ffffff'></td><td bgcolor='#ffffff'></td><td><font color='#cc3300'><b>FAILED</b></font></td></tr>" + resultPlaceholder);

				for (int k = 0; k < testCase.getTestCaseSteps().size(); k++) {
					TestCaseStep testStep = testCase.getTestCaseSteps().get(k);
					System.out.println("Actual TS result :: " + testStep.getActualResult());
					System.out.println("Expected TS result ::  " + testStep.getExpectedResult());
					if (testStep.getActualResult().equals(testStep.getExpectedResult()))
						reportIn = reportIn.replaceFirst(resultPlaceholder,
								"<tr><td bgcolor='#ffffff'></td><td bgcolor='#ffffff'></td><td bgcolor='#cc99ff'>" + testStep.getDescription() + "</td><td bgcolor='#ccccff'><a href="
										+ "./" + testStep.getSno() + "_" + testStep.getKeyword() + ">" + testStep.getKeyword()
										+ "</a></td><td><font color='#00cc00'><b>PASSED</b></font></td></tr>" + resultPlaceholder);
					else
						reportIn = reportIn.replaceFirst(resultPlaceholder,
								"<tr><td bgcolor='#ffffff'></td><td bgcolor='#ffffff'></td><td bgcolor='#cc99ff'>" + testStep.getDescription() + "</td><td bgcolor='ccccff'><a href="
										+ "./" + testStep.getSno() + "_" + testStep.getKeyword() + ">" + testStep.getKeyword()
										+ "</a></td><td><font color='#cc3300'><b>FAILED</b></font></td></tr>" + resultPlaceholder);

				}
			}

		}
		String reportPath = DataManager.resultPath + "/Report.html";
		Files.write(Paths.get(reportPath), reportIn.getBytes(), StandardOpenOption.CREATE);
	}

	public static void captureScreenShot(String directoryName) throws IOException {
		String fileNameFormat;
		String fileName;
		fileNameFormat = new SimpleDateFormat("HHmmss.SS").format(new Date());
		fileName = directoryName + "/" + fileNameFormat + "_" + UUID.randomUUID().toString() + ".png";
		System.out.println("FileName:" + fileName);
		WebDriver augmentedDriver = new Augmenter().augment(SeleniumFramework.driver);
		File scrFile = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File(fileName), true);
	}
}
