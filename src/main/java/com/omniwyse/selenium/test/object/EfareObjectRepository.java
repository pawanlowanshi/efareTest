package com.omniwyse.selenium.test.object;

import org.apache.poi.xssf.usermodel.XSSFRow;

import com.omniwyse.selenium.data.DataManager;

public class EfareObjectRepository implements ObjectRepository {
	private String id;
	private String name;
	private String rule;

	private String byCss;
	private String byXpath;

	public EfareObjectRepository(XSSFRow row) {
		if (row.getCell(0) != null)
			id = row.getCell(0).toString();
		if (row.getCell(1) != null)
			name = row.getCell(1).toString();
		if (row.getCell(2) != null)
			rule = row.getCell(2).toString();
		if (row.getCell(3) != null)
			byCss = row.getCell(3).toString();
		if (row.getCell(4) != null)
			byXpath = row.getCell(4).toString();
	}

	public String buildXpath() throws NoSuchFieldException, SecurityException {
		String xpath = null;
		ObjectRepository name = DataManager.objRepositories.get(getName());

		System.out.println("css  " + ((EfareObjectRepository) name).getByCss());
		System.out.println("xpath  " + ((EfareObjectRepository) name).getByXpath());

		System.out.println(((EfareObjectRepository) DataManager.objRepositories.get("SelectACard_Element")).getByCss());
		System.out.println(((EfareObjectRepository) DataManager.objRepositories.get("SelectACard_CardName")).getByXpath());

		if ("xpath".equalsIgnoreCase(rule))
			xpath = ((EfareObjectRepository) name).getByXpath();
		else if ("css".equalsIgnoreCase(rule))
			xpath = ((EfareObjectRepository) name).getByCss();
		System.out.println("xpath based on rule::: " + xpath);
		return xpath;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public String getByCss() {
		return byCss;
	}

	public void setByCss(String byCss) {
		this.byCss = byCss;
	}

	public String getByXpath() {
		return byXpath;
	}

	public void setByXpath(String byXpath) {
		this.byXpath = byXpath;
	}

}
