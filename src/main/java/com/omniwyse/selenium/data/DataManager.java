package com.omniwyse.selenium.data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.xml.XmlTest;

import com.omniwyse.selenium.driver.SeleniumFramework;
import com.omniwyse.selenium.report.Reporter;
import com.omniwyse.selenium.test.functions.AppFunction;
import com.omniwyse.selenium.test.functions.AppFunctionStep;
import com.omniwyse.selenium.test.object.ObjectRepository;
import com.omniwyse.selenium.test.param.Param;
import com.omniwyse.selenium.test.suites.TestSuite;
import com.omniwyse.selenium.utils.Driver;

public class DataManager {
	static final String FILE_SEPERATOR = "/";
	public static HashMap<String, AppFunction> appFunctions = new HashMap<String, AppFunction>();
	public static HashMap<String, ObjectRepository> objRepositories = new HashMap<String, ObjectRepository>();
	public static HashMap<String, HashMap<String, String>> appElements = new HashMap<String, HashMap<String, String>>();
	public static HashMap<String, HashMap<String, String>> configInfo = new HashMap<String, HashMap<String, String>>();
	public static HashMap<String, ArrayList<String>> environmentInfo = new HashMap<String, ArrayList<String>>();
	public static String resultPath;
	public static String currentDate;
	public static String timestamp;
	public static BufferedWriter bufferedWriter;
	public static XmlTest config;

	public void executeTestPlan(String repositoryPath, String currentDate) throws Exception {
		System.out.println("repositoryPath Path is: " + repositoryPath);
		System.out.println("starts reading input(TestPlan) file..");
		List<TestSuite> testSuites = new ArrayList<TestSuite>();
		@SuppressWarnings("resource")
		XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(new File(repositoryPath + "TestPlan" + FILE_SEPERATOR + "Input_EfareTest.xlsx")));

		for (int i = 0; i < wb.getNumberOfSheets(); i++) {
			XSSFSheet sheet = wb.getSheetAt(i);
			for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
				if (!"yes".equalsIgnoreCase(ExcelUtils.getCellValByIndex(sheet, rowIndex, 3))) {
					continue;
				}
				
				try {
					TestSuite testSuite = new TestSuite(repositoryPath, sheet.getRow(rowIndex));
					testSuites.add(testSuite);
					bufferedWriter.close();
					objRepositories.clear();
					SeleniumFramework.driver.quit();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		Reporter.writeReport(repositoryPath,testSuites, currentDate);
		System.out.println("*****************************************************");
	}

	public void executeMethods(String repositoryPath, String product, int id, String testCaseName) throws Exception {
		resultPath = repositoryPath + product + "/Results/" + currentDate + FILE_SEPERATOR + timestamp;
		System.out.println(DataManager.resultPath + "/");
		Files.createDirectories(Paths.get(resultPath));
		bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(resultPath + "/Logs_" + testCaseName + ".csv"), "UTF-16"));
		bufferedWriter.write("TSName,TCName,TCStepNo,Description,AppFuncName,AppStepNo,AppStepName,Params,Status\n");
		extractAppFunctions(repositoryPath + product + FILE_SEPERATOR);
		extractConfig(repositoryPath, product);
		extractEnvironmentDetails(repositoryPath, product);
		driverInitializer(product);
		extractObjRepository(repositoryPath + product + FILE_SEPERATOR, product);
		if ("quixey".equals(product))
			extractAppSectionElements(repositoryPath + product + FILE_SEPERATOR);
		System.out.println("resultPath::::" + resultPath);
	}

	private void extractEnvironmentDetails(String repositoryPath, String product) throws FileNotFoundException, IOException {
		for (String name : new File(repositoryPath + FILE_SEPERATOR + product + FILE_SEPERATOR + "Environment/").list()) {
			System.out.println("Current environment detail elements name is " + name);
			if (!name.contains("~$")) {
				@SuppressWarnings("resource")
				XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(new File(repositoryPath + FILE_SEPERATOR + product + FILE_SEPERATOR + "Environment" + FILE_SEPERATOR
						+ name)));
				ArrayList<String> environmentDetail = new ArrayList<String>();
				for (int i = 0; i < wb.getNumberOfSheets(); i++) {
					XSSFSheet sheet = wb.getSheetAt(i);

					for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
						if ("".equals(ExcelUtils.getCellValByIndex(sheet, rowIndex, 0)) || ExcelUtils.getCellByIndex(sheet, rowIndex, 0) == null)
							continue;
						environmentDetail.add(ExcelUtils.getCellValByIndex(sheet, rowIndex, 0));
					}
				}
				environmentInfo.put(name.split("\\.")[0], environmentDetail);
			}
		}
		System.out.println("environment detail are ::::=-=-=--" + environmentInfo);

	}

	private void extractAppSectionElements(String repositoryPath) throws FileNotFoundException, IOException {
		for (String name : new File(repositoryPath + "quixeyAppElementsMap/").list()) {
			System.out.println("Current app section elements name is " + name);
			if (!name.contains("~$")) {
				@SuppressWarnings("resource")
				XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(new File(repositoryPath + "quixeyAppElementsMap" + FILE_SEPERATOR + name)));
				for (int i = 0; i < wb.getNumberOfSheets(); i++) {
					XSSFSheet sheet = wb.getSheetAt(i);

					for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
						HashMap<String, String> map = new HashMap<String, String>();
						String key = ExcelUtils.getCellValByIndex(sheet, rowIndex, 0) + "|" + ExcelUtils.getCellValByIndex(sheet, rowIndex, 1) + "|"
								+ ExcelUtils.getCellValByIndex(sheet, rowIndex, 2);
						map.put(ExcelUtils.getCellValByIndex(sheet, 0, 3), ExcelUtils.getCellValByIndex(sheet, rowIndex, 3));
						map.put(ExcelUtils.getCellValByIndex(sheet, 0, 4), ExcelUtils.getCellValByIndex(sheet, rowIndex, 4));
						appElements.put(key, map);
					}
				}
			}
		}
		System.out.println("All objects " + appElements);

	}

	private void extractObjRepository(String repositoryPath, String product) throws FileNotFoundException, IOException, InstantiationException, IllegalAccessException,
			ClassNotFoundException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		for (String name : new File(repositoryPath + "ObjectRepository/").list()) {
			System.out.println("Current object repository name is " + name);
			if (!name.contains("~$")) {
				@SuppressWarnings("resource")
				XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(new File(repositoryPath + "ObjectRepository" + FILE_SEPERATOR + name)));
				for (int i = 0; i < wb.getNumberOfSheets(); i++) {
					XSSFSheet sheet = wb.getSheetAt(i);

					for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {

						ObjectRepository objRep = (ObjectRepository) Class
								.forName("com.omniwyse.selenium.test.object." + product.substring(0, 1).toUpperCase() + product.substring(1) + "ObjectRepository")
								.getConstructor(XSSFRow.class).newInstance(sheet.getRow(rowIndex));
						objRepositories.put(Class.forName("com.omniwyse.selenium.test.object." + product.substring(0, 1).toUpperCase() + product.substring(1) + "ObjectRepository")
								.getMethod("getName", null).invoke(objRep, null).toString(), objRep);
					}
				}
			}
		}
		System.out.println("all objects " + objRepositories);

	}

	private void extractConfig(String repositoryPath, String product) throws FileNotFoundException, IOException {

		@SuppressWarnings("resource")
		XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(new File(repositoryPath + product + FILE_SEPERATOR + "Config.xlsx")));
		XSSFSheet sheet = wb.getSheetAt(0);
		HashMap<String, String> map = new HashMap<String, String>();
		configInfo.put(product, map);
		for (int rowIndex = 0; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
			System.out.println("Cell value at 0 index : " + ExcelUtils.getCellValByIndex(sheet, rowIndex, 0));
			System.out.println("Cell value at 1 index : " + ExcelUtils.getCellValByIndex(sheet, rowIndex, 1));
			map.put(ExcelUtils.getCellValByIndex(sheet, rowIndex, 0), ExcelUtils.getCellValByIndex(sheet, rowIndex, 1));
		}
		System.out.println("configInfo map:::>>>>>>>>>>>> " + configInfo);
	}

	public static Boolean getBooleanValue(XSSFRow row) {
		if (row.getCell(3) != null && "pass".equalsIgnoreCase(row.getCell(3).toString()) || "true".equalsIgnoreCase(row.getCell(3).toString()))
			return true;
		else
			return false;
	}

	private HashMap<String, AppFunction> extractAppFunctions(String repositoryPath) throws Exception {
		for (String name : new File(repositoryPath + "TestAppFunctions/").list()) {
			System.out.println("Current app function name is " + name);
			if (!name.contains("~$")) {
				AppFunction appFun = new AppFunction();
				List<AppFunctionStep> functions = new ArrayList<AppFunctionStep>();
				@SuppressWarnings("resource")
				XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(new File(repositoryPath + "TestAppFunctions" + FILE_SEPERATOR + name)));
				for (int i = 0; i < wb.getNumberOfSheets(); i++) {
					XSSFSheet sheet = wb.getSheetAt(i);

					for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
						System.out.println(sheet.getSheetName());
						if (!"yes".equalsIgnoreCase(ExcelUtils.getCellValByIndex(sheet, rowIndex, 4))) {
							continue;
						}
						AppFunctionStep appFunData = new AppFunctionStep();
						appFunData.setSno((int) ExcelUtils.getCellByIndex(sheet, rowIndex, 0).getNumericCellValue());
						appFunData.setAction(ExcelUtils.getCellValByIndex(sheet, rowIndex, 1));
						appFunData.setObject(ExcelUtils.getCellValByIndex(sheet, rowIndex, 2));
						appFunData.setExpectedResult(ExcelUtils.getCellBooleanByIndex(sheet, rowIndex, 3));
						List<Param> params = new ArrayList<Param>();
						int colIndex = 5;
						while (ExcelUtils.getCellByIndex(sheet, rowIndex, colIndex) != null) {
							Param param = new Param(ExcelUtils.getCellByIndex(sheet, rowIndex, colIndex));
							params.add(param);
							colIndex++;
						}
						System.out.println("Parameter values based on app function data " + params);
						appFunData.setParams(params);
						functions.add(appFunData);
						System.out.println("App function data based on file of " + sheet.getSheetName() + " is " + functions);
					}
				}
				appFun.setName(name.toString().split("\\.")[0]);
				appFun.setFunctions(functions);
				appFunctions.put(name.split("\\.")[0], appFun);
			}
		}
		System.out.println("All app functions " + appFunctions);
		return appFunctions;
	}

	public void driverInitializer(String product) throws MalformedURLException, InterruptedException {
		if ("Selenium".equals(DataManager.configInfo.get(product).get("Framework"))) {
			if (DataManager.configInfo.get(product).get("Platform").equalsIgnoreCase("grid")|| DataManager.configInfo.get(product).get("Platform").equalsIgnoreCase("remote"))
				SeleniumFramework.driver = Driver.getDriver(config, product);
			else {
				if (DataManager.configInfo.get(product).get("Browser").equalsIgnoreCase("chrome")) {
					System.setProperty("webdriver.chrome.driver", DataManager.configInfo.get(product).get("ChromeDriverPath"));
					SeleniumFramework.driver = new ChromeDriver();
				} else if (DataManager.configInfo.get(product).get("Browser").equalsIgnoreCase("firefox")
						|| DataManager.configInfo.get(product).get("Browser").equalsIgnoreCase("ff"))
					SeleniumFramework.driver = new FirefoxDriver();
				else {
					if (DataManager.configInfo.get(product).get("Browser").equalsIgnoreCase("ie") || DataManager.configInfo.get(product).get("Browser").contains("internet")) {
						System.setProperty("webdriver.ie.driver", DataManager.configInfo.get(product).get("IEDriverPath"));
						SeleniumFramework.driver = new InternetExplorerDriver();
					}
				}
				SeleniumFramework.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				SeleniumFramework.driver.manage().window().maximize();
			}
		}

	}
}
