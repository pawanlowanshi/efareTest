package com.omniwyse.selenium.test.functions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.omniwyse.selenium.data.DataManager;
import com.omniwyse.selenium.driver.SeleniumFramework;
import com.omniwyse.selenium.report.Reporter;
import com.omniwyse.selenium.test.object.ObjectRepository;
import com.omniwyse.selenium.test.param.Param;

public class AppFunctionStep {
	private int sno;
	private String action;
	private Boolean expectedResult;
	private String object;
	private List<Param> params;

	public Boolean getExpectedResult() {
		return expectedResult;
	}

	public void setExpectedResult(Boolean expectedResult) {
		this.expectedResult = expectedResult;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public List<Param> getParams() {
		return params;
	}

	public void setParams(List<Param> params) {
		this.params = params;
	}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	public Boolean execute(List<Param> parentParams, String product, String testCaseName, int sno, String appFunctionName) throws Exception {
		DataManager.bufferedWriter.write(", , , , ," + getSno() + "," + getAction() + ",");
		System.out.println("App Step Execute");
		String xpath = null;
		if (object.equals("::Device") || object.equals("::Function")) {
			xpath = object;
		} else {
			ObjectRepository objRep;
			if (object.contains("$Param")) {
				int index = Integer.parseInt(object.split("m")[1]);
				System.out.println(index + "         " + parentParams.get(index - 1).getValue());
				objRep = DataManager.objRepositories.get(parentParams.get(index - 1).getValue());
			} else {
				objRep = DataManager.objRepositories.get(object);
			}
			System.out.println("current object is: " + object);
			xpath = objRep.buildXpath();
		}
		System.out.println("XPATH:::::" + xpath);
		java.lang.reflect.Method method = getFrameworkInstance(product).getClass().getMethod(getAction(), String.class, List.class);
		System.out.println("Invoking Framework Methods");
		Reporter.captureScreenShot(DataManager.resultPath+"/"+testCaseName+"/"+sno+"_"+appFunctionName,getAction());

		for (Param appStepParam : getParams()) {
			DataManager.bufferedWriter.write(appStepParam.getValue());
			if ("".equals(appStepParam.getValue()) || " ".equals(appStepParam.getValue()) || appStepParam.getValue() == null)
				break;
			else
				DataManager.bufferedWriter.write(" | ");
		}
		boolean flag = (Boolean) method.invoke(getFrameworkInstance(product), xpath, extractParamsFromParent(parentParams));
		if (flag == true)
			DataManager.bufferedWriter.write("\n, , , , , , , ,PASS");
		else
			DataManager.bufferedWriter.write("\n, , , , , , , ,Fail");
		DataManager.bufferedWriter.newLine();
		return flag;
	}

	private Object getFrameworkInstance(String product) {
		Object frameworkInstance = null;
		if ("Selenium".equals(DataManager.configInfo.get(product).get("Framework"))) {
			frameworkInstance = new SeleniumFramework();
		} else if ("UIAutomater".equals(DataManager.configInfo.get(product).get("Framework"))) {
			frameworkInstance = null;// new UIAutomaterFramework();
		}

		return frameworkInstance;
	}

	public List<Param> extractParamsFromParent(List<Param> parentParams) {
		List<Param> newParams = new ArrayList<Param>();
		Iterator<Param> itr = getParams().iterator();
		while (itr.hasNext()) {
			Param param = itr.next();
			if (!param.getValue().contains("$Param")) {
				newParams.add(param);
			} else {
				Param newParam = new Param();
				int index = Integer.parseInt(param.getValue().split("m")[1]);
				newParam.setValue(parentParams.get(index - 1).getValue());
				newParams.add(newParam);
			}
		}
		return newParams;
	}

	public int getSno() {
		return sno;
	}

	public void setSno(int sno) {
		this.sno = sno;
	}
}
