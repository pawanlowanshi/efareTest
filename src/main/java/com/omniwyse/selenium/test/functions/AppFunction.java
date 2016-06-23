package com.omniwyse.selenium.test.functions;

import java.util.List;

public class AppFunction {
	private List<AppFunctionStep> functions;
	private String name;

	public List<AppFunctionStep> getFunctions() {
		return functions;
	}

	public void setFunctions(List<AppFunctionStep> functions) {
		this.functions = functions;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
