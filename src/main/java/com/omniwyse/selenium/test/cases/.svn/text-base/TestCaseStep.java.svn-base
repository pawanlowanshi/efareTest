package com.omniwyse.selenium.test.cases;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;

import com.omniwyse.selenium.data.DataManager;
import com.omniwyse.selenium.test.functions.AppFunction;
import com.omniwyse.selenium.test.functions.AppFunctionStep;
import com.omniwyse.selenium.test.param.Param;

public class TestCaseStep {
	// final static Logger logger =
	// Logger.getLogger(TestCasesStep.class.getClass());
	private int sno;
	private String keyword;
	private Boolean expectedResult;
	private Boolean actualResult = false;
	private String description;
	private List<Param> params;

	public TestCaseStep(String repositoryPath, XSSFRow row, String testCaseName, String product) throws Exception {
		sno = (int) row.getCell(0).getNumericCellValue();
		keyword = row.getCell(1).toString();
		description = row.getCell(2).toString();
		DataManager.bufferedWriter.write(", ," + getSno() + "," + getDescription() + "," + getKeyword() + ", , ,");
		expectedResult = DataManager.getBooleanValue(row);
		params = executeTestCaseStep(row, testCaseName, product);
	}

	private List<Param> executeTestCaseStep(XSSFRow row, String testCaseName, String product) throws Exception {
		System.out.println("test case step is executing...");
		List<Param> params = new ArrayList<Param>();
		int colIndex = 5;
		while (row.getCell(colIndex) != null && !"".equals(row.getCell(colIndex).toString())) {
			Param param = new Param(row.getCell(colIndex));
			params.add(param);
			DataManager.bufferedWriter.write(param.getValue());
			if ("".equals(param.getValue()) || " ".equals(param.getValue()) || param.getValue() == null)
				break;
			else
				DataManager.bufferedWriter.write(" | ");
			colIndex++;
		}
		DataManager.bufferedWriter.newLine();
		execute(params, testCaseName, product);
		return params;
	}

	private void execute(List<Param> parentParams, String testCaseName, String product) throws Exception {
		System.out.println("In Test Case Step Execute method with Params..");
		System.out.println("Keyword is : " + keyword);
		AppFunction appFunction = DataManager.appFunctions.get(keyword);

		if ("Live".equalsIgnoreCase(DataManager.configInfo.get(product).get("ExecutionEnvironment"))) {
			for (String envAppFunction : DataManager.environmentInfo.get(DataManager.configInfo.get(product).get("ExecutionEnvironment"))) {
				if (envAppFunction.equalsIgnoreCase(appFunction.getName())) {
					keyword = "APPFUNCTION SKIPPED:" + DataManager.configInfo.get(product).get("ExecutionEnvironment") + "Environment:" + envAppFunction;
					setActualResult(true);
					return;
				}
			}
		} else if ("Production".equalsIgnoreCase(DataManager.configInfo.get(product).get("ExecutionEnvironment"))) {
			for (String envAppFunction : DataManager.environmentInfo.get(DataManager.configInfo.get(product).get("ExecutionEnvironment"))) {
				if (envAppFunction.equalsIgnoreCase(appFunction.getName())) {
					keyword = "APPFUNCTION SKIPPED:" + DataManager.configInfo.get(product).get("ExecutionEnvironment") + "Environment:" + envAppFunction;
					setActualResult(true);
					return;
				}
			}
		} else if (DataManager.configInfo.get(product).get("ExecutionEnvironment") == null)
			return;

		System.out.println("Functions size " + appFunction.getFunctions().size());
		Files.createDirectories(Paths.get(DataManager.resultPath +"/"+ getSno()+"_"+getKeyword()));
		Iterator<AppFunctionStep> itr = appFunction.getFunctions().iterator();
		while (itr.hasNext()) {
			System.out.println("Inside Iterator App Step Execute..");
			AppFunctionStep appStep = itr.next();
			setActualResult(appStep.execute(parentParams, product, getSno(), getKeyword()));
			boolean flag = false;
			if (getActualResult().equals(getExpectedResult()))
				flag = true;
			String result = flag ? "PASS" : "FAIL";
			// logger.info(" : "+testCaseName+" : "+appFunction.getName()+" : "+appStep.getAction()+" : "+result+"  **********************************************");
			System.out.println("******************************" + new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss,SSS").format(Calendar.getInstance().getTime()) + " : "
					+ testCaseName + " : " + appFunction.getName() + " : " + getSno() + " : " + appStep.getAction() + " : " + appStep.getSno() + " : " + result
					+ "  **********************************************");
		}
	}

	public Boolean getExpectedResult() {
		return expectedResult;
	}

	public void setExpectedResult(Boolean expectedResult) {
		this.expectedResult = expectedResult;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public List<Param> getParams() {
		return params;
	}

	public void setParams(List<Param> params) {
		this.params = params;
	}

	public Boolean getActualResult() {
		return actualResult;
	}

	public void setActualResult(Boolean actualResult) {
		this.actualResult = actualResult;
	}

	public int getSno() {
		return sno;
	}

	public void setSno(int sno) {
		this.sno = sno;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
