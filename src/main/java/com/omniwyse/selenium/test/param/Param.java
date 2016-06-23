package com.omniwyse.selenium.test.param;

import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;

public class Param {
	private String value;

	public Param(Cell cell) throws IOException {
		value = String.valueOf(cell).trim();
	}

	public Param() {
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
